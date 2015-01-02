package labex.feevale.br.looky.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.ResponseHelp;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.fragment.MainFragment;
import labex.feevale.br.looky.wrapper.RegisterLogin;

/**
 * Created by 0118230 on 29/12/2014.
 */
public class RegisterService extends ServiceHandler {

    private Activity activity;
    private ProgressDialog dialog;
    private RegisterLogin registerLogin;

    public RegisterService(Activity activity, RegisterLogin registerLogin) {
        super(activity);
        this.activity = activity;
        this.registerLogin = registerLogin;
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
        User userResponse = new JsonUtils().JsonToUser(response);
        closeDialog();

        if(userResponse.getId() != null){
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
            sharedPreferencesUtils.saveUser(activity,userResponse);
            if(sharedPreferencesUtils.getUSer(activity).getId() > 0)
                ((MainActivity)activity).loadMainFragment();
            else
                Toast.makeText(activity, "Problemas ao efetuar seu registro!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(activity,activity.getResources().getString(R.string.OPERATION_FAIL),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void execute() {
        JsonUtils jsonUtils = new JsonUtils();
        makeServiceCall(AppVariables.URL+ AppVariables.REGISTER_USER,POST,jsonUtils.RegisterLoginToJson(registerLogin) );
    }

    private void closeDialog(){
        if(dialog.isShowing())
            dialog.dismiss();
    }
}
