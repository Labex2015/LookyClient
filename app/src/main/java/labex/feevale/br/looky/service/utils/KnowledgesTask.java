package labex.feevale.br.looky.service.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import labex.feevale.br.looky.service.ServiceHandler;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0139612 on 17/12/2014.
 */
public class KnowledgesTask extends AsyncTask<Void, MessageResponse, MessageResponse> {
    private ServiceHandler handler;
    private Activity activity;
    private ProgressDialog dialog;
    private ProcessMessage processMessage;

    public KnowledgesTask(ServiceHandler handler, Activity activity, ProcessMessage processMessage) {
        this.handler = handler;
        this.activity = activity;
        this.processMessage = processMessage;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Executando ação");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected MessageResponse doInBackground(Void... params) {

        handler.execute();
        MessageResponse response = processMessage.getResponse();
        return response;
    }

    @Override
    protected void onPostExecute(MessageResponse messageResponse) {
       if(dialog.isShowing())
           dialog.dismiss();

        Toast.makeText(activity, messageResponse.getMsg(), Toast.LENGTH_LONG).show();
    }
}
