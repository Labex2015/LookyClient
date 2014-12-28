package labex.feevale.br.looky.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by PabloGilvan on 25/12/2014.
 */
public abstract class ModelBasicTask extends AsyncTask<Void, MessageResponse, MessageResponse>{

    private Activity activity;
    private ProgressDialog progressDialog;
    private String messageDialog;

    protected MessageResponse messageResponse;

    protected ModelBasicTask(String messageDialog, Activity activity) {
        this.messageDialog = messageDialog;
        this.activity = activity;
        messageResponse = new MessageResponse();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(messageDialog);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(MessageResponse messageResponse) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            if(messageResponse.getStatus())
                onSuccess();
            else
                onFail();
    }

    public abstract void onFail();
    public abstract void onSuccess();

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
