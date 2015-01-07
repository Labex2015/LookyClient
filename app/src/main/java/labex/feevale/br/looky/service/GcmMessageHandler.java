package labex.feevale.br.looky.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.ChatResponse;
import labex.feevale.br.looky.model.RequestHelp;
import labex.feevale.br.looky.service.utils.GCMVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GcmMessageHandler extends IntentService{

    private String message;
    private String messageTypeToRedirect;
    private Integer type;
    private Handler handler;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private ChatResponse chatResponse;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        message = extras.getString("body");
        messageTypeToRedirect = extras.getString("type");
        type = Integer.parseInt(messageTypeToRedirect);
        handler.post(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case GCMVariables.CHAT:
                        notifyMessage(message);
                        break;
                    case GCMVariables.TYPE_REQUEST_HELP:
                        notifyRequestUserHelp(message);
                        break;
                    case GCMVariables.TYPE_RESPONSE_HELP:

                        break;
                }
            }
        });
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    public void notifyMessage(String response) {
        chatResponse = new JsonUtils().JsonToChatResponse(message);
        Bundle argsBundle = new Bundle();
        argsBundle.putSerializable("CHAT", chatResponse);
        Intent chat = new Intent(this, MainActivity.class);
        chat.putExtra(GCMVariables.ITEM_TO_LOAD, GCMVariables.CHAT);
        chat.putExtra("TYPE_FRAG", argsBundle);

        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1000,chat, 0);
        showNotification(getApplicationContext(), "Nova mensagem!", chatResponse.getText(),
                getResources().getString(R.string.app_name), R.drawable.ic_launcher, contentIntent);
    }

    public void notifyRequestUserHelp(String response) {
        RequestHelp request = new JsonUtils().JsonToRequestHelp(message);
        Bundle argsBundle = new Bundle();
        argsBundle.putSerializable("REQUEST", request);
        argsBundle.putInt(GCMVariables.ITEM_TO_LOAD, GCMVariables.TYPE_REQUEST_HELP);
        Intent helpIntent = new Intent(this, MainActivity.class);
        helpIntent.putExtra("TYPE_FRAG", argsBundle);

        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, helpIntent, 0);
        showNotification(getApplicationContext(), "Solicitação de ajuda!", request.getText(),
                getResources().getString(R.string.app_name), R.drawable.ic_launcher, contentIntent);
    }

    public void notifyResponseUser(String response) {
        MessageResponse messageResponse = new JsonUtils().JsonToMessageResponse(response);
        Bundle argsBundle = new Bundle();
        argsBundle.putSerializable("RESPONSE", messageResponse);
        Intent helpIntent = new Intent(this, MainActivity.class);
        helpIntent.putExtra("TYPE_FRAG", argsBundle);

        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, helpIntent, 0);
        showNotification(getApplicationContext(), "Seu pedido de ajuda foi respondido!", messageResponse.getMsg(),
                getResources().getString(R.string.app_name), R.drawable.ic_launcher, contentIntent);
    }


    public void showNotification(Context context, String title, String message, String ticker, int icon, PendingIntent contentIntent){

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(message);

        if(contentIntent != null)
            builder.setContentIntent(contentIntent);

        builder.setSmallIcon(icon);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        builder.setAutoCancel(true);
        builder.setSound(alarmSound);

        Notification notification = builder.build();
        notification.vibrate = new long[]{150, 300, 150, 450};
        notificationManager.notify(1, notification);
    }
}
