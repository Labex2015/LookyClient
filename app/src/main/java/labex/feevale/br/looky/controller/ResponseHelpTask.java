package labex.feevale.br.looky.controller;

import android.app.Activity;
import android.widget.Toast;

import labex.feevale.br.looky.model.ResponseHelp;
import labex.feevale.br.looky.service.SendDeniedRequest;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 29/12/2014.
 */
public class ResponseHelpTask extends ModelBasicTask {

    private ResponseHelp responseHelp;

    public ResponseHelpTask(String messageDialog, Activity activity, ResponseHelp responseHelp){
        super(messageDialog, activity);
        this.responseHelp = responseHelp;
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), messageResponse.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getActivity(), messageResponse.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected MessageResponse doInBackground(Void... voids) {
        new SendDeniedRequest(getActivity(), responseHelp, messageResponse).execute();
        return messageResponse;
    }
}
