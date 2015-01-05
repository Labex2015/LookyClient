package labex.feevale.br.looky.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.Toast;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.fragment.MainFragment;
import labex.feevale.br.looky.wrapper.RegisterLogin;

/**
 * Created by 0118230 on 30/12/2014.
 */
public class LoginService extends ServiceHandler {
    private Activity activity;
    private ProgressDialog dialog;
    private RegisterLogin registerLogin;


    public LoginService(Activity activity, RegisterLogin registerLogin, ProgressDialog dialog) {
        super(activity);
        this.dialog = dialog;
        this.activity = activity;
        this.registerLogin = registerLogin;
    }

    @Override
    public boolean validation() {
        return new AppHelp(activity).validateConnection();
    }

    @Override
    public void onFailValidation() {
        closeDialog();
    }

    @Override
    protected void postExecute(String response) {
        User userResponse = new JsonUtils().JsonToUser(response);
        closeDialog();

        if(userResponse.getId() != null){
            ((MainActivity)activity).changeFragment(new MainFragment());
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
            sharedPreferencesUtils.saveUser(activity,userResponse);
        }else{
            Toast.makeText(activity,activity.getResources().getString(R.string.OPERATION_FAIL),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void execute() {
        JsonUtils jsonUtils = new JsonUtils();
        makeServiceCall(AppVariables.URL+ AppVariables.LOGIN_USER,POST,jsonUtils.RegisterLoginToJson(registerLogin) );
    }

    private void closeDialog(){
        if(dialog.isShowing())
            dialog.dismiss();
    }
}

