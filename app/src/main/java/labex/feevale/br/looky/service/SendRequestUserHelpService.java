package labex.feevale.br.looky.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by PabloGilvan on 27/12/2014.
 */
public class SendRequestUserHelpService extends ServiceHandler {

    private Activity activity;
    private Long idUserToRequest;
    private ProgressDialog dialog;

    public SendRequestUserHelpService(Activity activity, Long idUserToRequest) {
        this.activity = activity;
        this.idUserToRequest = idUserToRequest;
    }

    @Override
    public boolean validation() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Solicitando ajuda......");
        dialog.setCancelable(false);
        dialog.show();
        return new AppHelp(activity).validateConnection();
    }

    @Override
    public void onFailValidation() {
        closeDialog();
        Toast.makeText(activity, "Ops! Sem conexão com a internet!", Toast.LENGTH_LONG).show();
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
        makeServiceCall(AppVariables.URL+
                AppVariables.REQUEST_USER_HELP.replace("#ID_USER", new SharedPreferencesUtils().getUSer(activity).getId()+"")
                .replace("#ID_HELPER",idUserToRequest+""),GET, null);
    }

    private void closeDialog(){
        if(dialog.isShowing())
            dialog.dismiss();
    }
}
