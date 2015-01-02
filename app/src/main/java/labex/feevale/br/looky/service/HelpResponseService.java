package labex.feevale.br.looky.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.Toast;

import labex.feevale.br.looky.model.Message;
import labex.feevale.br.looky.model.ResponseHelp;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by PabloGilvan on 28/12/2014.
 */
public class HelpResponseService extends ServiceHandler {

    private Activity activity;
    private ResponseHelp responseHelp;
    private MessageResponse messageResponse;

    public HelpResponseService(Activity activity, ResponseHelp responseHelp, MessageResponse messageResponse) {
        super(activity);
        this.activity = activity;
        this.responseHelp = responseHelp;
        this.messageResponse = messageResponse;
    }

    @Override
    public boolean validation() {
        return new AppHelp(activity).validateConnection();
    }

    @Override
    public void onFailValidation() {
        Toast.makeText(activity, "Sem conexão com a internet!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void postExecute(String response) {
        MessageResponse responseServer = new JsonUtils().JsonToMessageResponse(response);
        if(responseServer != null){
            messageResponse.setStatus(responseServer.getStatus());
            messageResponse.setMsg(responseServer.getMsg());
        }
    }

    @Override
    public void execute() {
        makeServiceCall(AppVariables.URL+AppVariables.RESPONSE_USER_REQUEST.replace("#ID_USER", "1"),GET, null);
    }
}
