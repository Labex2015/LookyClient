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
    private ProgressDialog dialog;
    public static final int LOGIN = 1;
    public static final int REGISTER = 2;

    public GCMService(Activity activity, RegisterLogin registerLogin, int operation) {
        gcm = GoogleCloudMessaging.getInstance(activity);
        this.activity = activity;
        this.registerLogin = registerLogin;
        this.operation = operation;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getResources().getString(R.string.LOADING));
        dialog.setCancelable(false);
        dialog.show();
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
                    new LoginService(activity,registerLogin, dialog).execute();
                    break;
                case REGISTER:
                    new RegisterService(activity,registerLogin, dialog).execute();
                    break;
            }
        }else{
            if(dialog.isShowing())
                dialog.dismiss();
            Toast.makeText(activity, "Problemas ao gerar GCM, " +
                    "verifique sua conexão com a internet!", Toast.LENGTH_LONG).show();
        }
    }
}
