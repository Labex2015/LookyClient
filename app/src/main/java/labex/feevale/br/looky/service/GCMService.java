package labex.feevale.br.looky.service;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import labex.feevale.br.looky.service.utils.GCMVariables;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GCMService extends AsyncTask<Void, String,String>{

    private GoogleCloudMessaging gcm;
    private Activity activity;

    public GCMService(Activity activity) {
        gcm = GoogleCloudMessaging.getInstance(activity);
        this.activity = activity;
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
            msg = "Device registered, registration ID=" + regid;
            Log.i("GCM", msg);

        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();

        }
        return msg;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.e("GCM", s);
    }
}
