package labex.feevale.br.looky.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.PreferencesHelp;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by Jeferson on 11/12/2014.
 */
public class NotifyPositionService extends BaseServiceHandler {
    Context context;

    public NotifyPositionService(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean validation() {
        return new AppHelp(context).validateConnection();
    }

    @Override
    public void onFailValidation() {
        Log.w(context.getResources().getString(R.string.TAG_CONNECTION).toString(), context.getResources().getString(R.string.CONNECTION_ERROR).toString());
    }

    @Override
    protected void postExecute(String response) {
        MessageResponse messageResponse = null;

        JsonUtils jsonUtils = new JsonUtils();
        messageResponse = jsonUtils.JsonToMessageResponse(response);

        if(messageResponse.getStatus() != true){
            execute();
        }
    }

    public void execute(){
        User user           = new SharedPreferencesUtils().getUSer(context);
        if(user != null) {
            JsonUtils jsonUtils = new JsonUtils();
            super.makeServiceCall(AppVariables.URL + AppVariables.POSITION_VERB, POST, jsonUtils.UserToJson(user));
        }
    }
}
