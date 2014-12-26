package labex.feevale.br.looky.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import labex.feevale.br.looky.model.Message;
import labex.feevale.br.looky.service.SendMessageService;
import labex.feevale.br.looky.service.utils.ProcessChat;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 23/12/2014.
 */
public class SendMessageTask extends AsyncTask<Void, MessageResponse, MessageResponse> {

    private Message message;
    private ProcessChat processChat;
    private Activity activity;
    private String messageBody;
    private MessageResponse responseFromServer;

    public SendMessageTask(Message message, ProcessChat processChat, Activity activity) {
        this.message = message;
        this.processChat = processChat;
        this.activity = activity;
        this.responseFromServer = new MessageResponse();
    }

    @Override
    protected void onPreExecute() {
        messageBody = new JsonUtils().MessageToJson(message);
    }

    @Override
    protected MessageResponse doInBackground(Void... voids) {
        new SendMessageService(messageBody, responseFromServer, activity).execute();
        return responseFromServer;
    }

    @Override
    protected void onPostExecute(MessageResponse messageResponse) {

        //Log.e("GCM", messageResponse.getMsg());

        if(responseFromServer.getStatus())
            processChat.executeActionSuccess();
        else
            processChat.executeActionFail(messageResponse);
    }
}
