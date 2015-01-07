package labex.feevale.br.looky.service;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.utils.BaseServiceAction;
import labex.feevale.br.looky.service.utils.GCMVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.view.dialogs.LoadingDialog;
import labex.feevale.br.looky.wrapper.RegisterLogin;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GCMService extends AsyncTask<Void, String,String> implements BaseServiceAction{

    private GoogleCloudMessaging gcm;
    private Activity activity;
    private RegisterLogin registerLogin;
    private int operation;
    private ProgressDialog dialog;
    public static final int LOGIN = 1;
    public static final int REGISTER = 2;

    private String params;
    private LoadingDialog loadingDialog;

    public GCMService(Activity activity, RegisterLogin registerLogin, int operation) {
        gcm = GoogleCloudMessaging.getInstance(activity);
        this.activity = activity;
        this.registerLogin = registerLogin;
        this.operation = operation;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        initAction();
        params = new JsonUtils().RegisterLoginToJson(registerLogin);
    }

    @Override
    protected String doInBackground(Void... voids) {
        String msg = "";
        String registerId = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(activity.getApplicationContext());
            }
            registerId = gcm.register(GCMVariables.PROJECT_NUMBER);
            msg = registerId;
            Log.i("GCM", msg);
        } catch (IOException ex) {
            Log.e("Error", ex.getMessage());
            registerId = "";
        }
        return registerId;
    }

    @Override
    protected void onPostExecute(String s) {
        if(2 == 2){ //TODO: s != null && s.length() != 0
            registerLogin.setUserKey(s);
            switch (operation){
                case LOGIN:
                    new LoginService(new User(), params, activity, BaseHandler.TASK, BaseHandler.POST, this).makeServiceCall();
                    break;
                case REGISTER:
                    new RegisterService(activity,registerLogin, dialog).execute();
                    break;
            }
        }
    }

    @Override
    public void initAction() {
        loadingDialog = new LoadingDialog(activity);
        loadingDialog.show();
    }

    @Override
    public void finalizeAction() {
        if(loadingDialog.isShowing())
            loadingDialog.dismiss();
    }
}
