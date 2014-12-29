package labex.feevale.br.looky.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.ResponseHelp;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by 0118230 on 29/12/2014.
 */
public class SendDeniedRequest extends ServiceHandler {

    private Activity activity;
    private Long idUserToRequest;
    private ProgressDialog dialog;
    private ResponseHelp responseHelp;


    public SendDeniedRequest(Activity activity, Long idUserToRequest, ResponseHelp responseHelp) {
        this.activity = activity;
        this.idUserToRequest = idUserToRequest;
        this.responseHelp = responseHelp;
    }

    @Override
    public boolean validation() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getResources().getString(R.string.LOADING));
        dialog.setCancelable(false);
        dialog.show();
        return new AppHelp(activity).validateConnection();
    }

    @Override
    public void onFailValidation() {
        closeDialog();
    }

    @Override
    protected void postExecute(String response) {
        MessageResponse messageResponse = new JsonUtils().JsonToMessageResponse(response);
        closeDialog();
        if(messageResponse != null)
            Toast.makeText(activity, messageResponse.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void execute() {
        JsonUtils jsonUtils = new JsonUtils();
        makeServiceCall(AppVariables.URL+ AppVariables.REQUEST_USER_HELP.replace("#ID_USER", responseHelp.user.getId().toString()),GET,jsonUtils.ResponseHelptToJson(responseHelp) );
    }

    private void closeDialog(){
        if(dialog.isShowing())
            dialog.dismiss();
    }
}
