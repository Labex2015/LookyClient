package labex.feevale.br.looky.service;

import android.app.Activity;

import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 23/12/2014.
 */
public class SendMessageService extends ServiceHandler {

    private String message;
    private MessageResponse messageResponse;
    private Activity activity;

    public SendMessageService(String message, MessageResponse messageResponse, Activity activity) {
        this.message = message;
        this.messageResponse = messageResponse;
        this.activity = activity;
    }

    @Override
    public boolean validation() {
        return new AppHelp(activity).validateConnection();
    }

    @Override
    public void onFailValidation() {
        messageResponse.setMsg("Sem conexão com a internet.");
        messageResponse.setStatus(false);
    }

    @Override
    protected void postExecute(String response) {
        MessageResponse responseServer = new JsonUtils().JsonToMessageResponse(response);
        messageResponse.setStatus(responseServer.getStatus());
        messageResponse.setMsg(responseServer.getMsg());
    }

    @Override
    public void execute() {
        makeServiceCall(AppVariables.URL+ AppVariables.CHAT, POST, message);
    }
}
