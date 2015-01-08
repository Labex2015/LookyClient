package labex.feevale.br.looky.view.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.controller.SendMessageTask;
import labex.feevale.br.looky.model.ChatResponse;
import labex.feevale.br.looky.model.Message;
import labex.feevale.br.looky.service.utils.ProcessChat;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by 0146596 on 11/12/2014.
 */
public class ChatFragment extends android.support.v4.app.Fragment implements ProcessChat {

    private EditText campoMensagem;
    private LinearLayout chatArea;
    private ImageButton sendMessage;

    private Message message;
    private ChatResponse chatResponse;
    private ProcessChat processChat;
    private Bundle bundle;

    private Activity activity;
    private Long idFrom;
    private Long idTo;
    private Boolean comeFromNotification = false;

    public ChatFragment(Activity activity, Long idFrom, Long idTo) {
        this.activity = activity;
        this.idFrom = idFrom;
        this.idTo = idTo;
    }

    public ChatFragment(Activity activity, ChatResponse chatResponse) {
        this.activity = activity;
        this.chatResponse = chatResponse;
        this.idTo = chatResponse.getIdFrom();
        this.idFrom = new SharedPreferencesUtils().getUSer(activity).getId();
        this.comeFromNotification = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onNotice, new IntentFilter("Msg"));
        new SharedPreferencesUtils().saveChatStatus(true, getActivity());
        if(savedInstanceState == null){
            setRetainInstance(true);
            this.processChat = this;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(comeFromNotification)
            setReponse(chatResponse);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewChat = inflater.inflate(R.layout.fragment_chat, container, false);
        chatArea = (LinearLayout) viewChat.findViewById(R.id.chatArea);
        campoMensagem = (EditText) viewChat.findViewById(R.id.campoMensagem);
        sendMessage = (ImageButton) viewChat.findViewById(R.id.enviarChat);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageAction();
            }
        });

        return viewChat;
    }

    public void setReponse(ChatResponse response){
        //Log.e("CHAT", response.getText());
        LinearLayout rowChat = new LinearLayout(getActivity());
        rowChat.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(getActivity());
        textView.setTextSize(20);
        textView.setText(response.getText());



        LinearLayout dataUser = new LinearLayout(getActivity());
        dataUser.setOrientation(LinearLayout.VERTICAL);

        TextView label = new TextView(getActivity());
        label.setTextSize(12);
        label.setText(response.getUserFrom());
        label.setGravity(Gravity.LEFT);
        textView.setBackgroundResource(R.drawable.bg_purple_message_chat);

        dataUser.addView(label);
        dataUser.addView(textView);

        rowChat.addView(dataUser);
        rowChat.setGravity(Gravity.LEFT);
        rowChat.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        chatArea.addView(rowChat);


    }


    @Override
    public void executeActionSuccess() {
        LinearLayout rowChat = new LinearLayout(getActivity());
        rowChat.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(getActivity());
        textView.setTextSize(20);
        textView.setText(message.getText());

        LinearLayout dataUser = new LinearLayout(getActivity());
        dataUser.setOrientation(LinearLayout.VERTICAL);

        TextView label = new TextView(getActivity());
        label.setTextSize(12);
        label.setText("Você disse");
        label.setGravity(Gravity.RIGHT);
        textView.setBackgroundResource(R.drawable.bg_orange_message_chat);

        dataUser.addView(label);
        dataUser.addView(textView);


        rowChat.addView(dataUser);
        rowChat.setGravity(Gravity.RIGHT);
        rowChat.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rowChat.setPadding(2, 2, 2, 2);
        chatArea.addView(rowChat);
        campoMensagem.setText("");
    }

    private void sendMessageAction(){
        String valor = campoMensagem.getText().toString().trim();
        message = new Message();
        message.setText(valor);
        message.setDate(new Date());
        message.setIdFrom(idFrom);
        message.setIdTo(idTo);

        new SendMessageTask(message,processChat, getActivity()).execute();
    }

    @Override
    public void executeActionFail(MessageResponse messageResponse) {
        Toast.makeText(getActivity(), messageResponse.getMsg(), Toast.LENGTH_LONG).show();
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            chatResponse = null;
            chatResponse = (ChatResponse) intent.getSerializableExtra("CHAT");
            if(chatResponse != null)
                setReponse(chatResponse);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        new SharedPreferencesUtils().saveChatStatus(false, getActivity());
    }

    public void onExit(){
        campoMensagem.setText("O usuário "+new SharedPreferencesUtils().getUSer(activity).getUserName()
                    +" saiu do chat.");
        sendMessageAction();
    }
}
