package labex.feevale.br.looky.service;

import android.content.Context;
import android.widget.Toast;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.PreferencesHelp;

/**
 * Created by Jeferson on 16/12/2014.
 */
public class CancelHelpService extends ServiceHandler {
    Context context;

    public CancelHelpService(Context context) {
        this.context = context;
    }

    @Override
    public boolean validation() {
        return new AppHelp(context).validateConnection();
    }

    @Override
    public void onFailValidation() {
        Toast.makeText(context,context.getResources().getText(R.string.OPERATION_FAIL),Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void postExecute(String response) {
        MessageResponse messageResponse = null;

        JsonUtils jsonUtils = new JsonUtils();
        messageResponse = jsonUtils.JsonToMessageResponse(response);

        if(messageResponse.getStatus() != true){
            Toast.makeText(context,context.getResources().getText(R.string.OPERATION_FAIL),Toast.LENGTH_SHORT).show();
        }
    }

    public void execute(){
        User user             = new PreferencesHelp(context).getUserToPreferences();
        JsonUtils jsonUtils   = new JsonUtils();

        super.makeServiceCall(AppVariables.URL + AppVariables.CANCEL_HELP, GET, jsonUtils.UserToJson(user));
    }
}
