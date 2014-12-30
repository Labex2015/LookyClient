package labex.feevale.br.looky.service;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.utils.GCMVariables;
import labex.feevale.br.looky.wrapper.RegisterLogin;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GCMService extends AsyncTask<Void, String,String>{

    private GoogleCloudMessaging gcm;
    private Activity activity;
    private RegisterLogin registerLogin;
    private int operation;
    public static final int LOGIN = 1;
    public static final int REGISTER = 2;

    public GCMService(Activity activity, RegisterLogin registerLogin, int operation) {
        gcm = GoogleCloudMessaging.getInstance(activity);
        this.activity = activity;
        this.registerLogin = registerLogin;
        this.operation = operation;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String msg = "";
        String regid = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(activity.getApplicationContext());
            }
            regid = gcm.register(GCMVariables.PROJECT_NUMBER);
            msg = regid;
            Log.i("GCM", msg);

        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();

        }
        return msg;
    }

    @Override
    protected void onPostExecute(String s) {
        registerLogin.setUserKey(s);
        switch (operation){
            case LOGIN:
                new LoginService(activity,registerLogin).execute();
                break;
            case REGISTER:
                new RegisterService(activity,registerLogin).execute();
                break;
        }
    }
}
