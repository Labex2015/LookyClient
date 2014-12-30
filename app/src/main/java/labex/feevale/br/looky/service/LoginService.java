package labex.feevale.br.looky.service;

import android.content.Context;

/**
 * Created by 0118230 on 30/12/2014.
 */
public class LoginService extends ServiceHandler {
    Context context;

    public LoginService(Context context) {
        this.context = context;
    }

    @Override
    public boolean validation() {
        return false;
    }

    @Override
    public void onFailValidation() {

    }

    @Override
    protected void postExecute(String response) {

    }

    @Override
    public void execute() {

    }
}
