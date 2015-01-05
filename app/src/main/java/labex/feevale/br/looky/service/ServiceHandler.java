package labex.feevale.br.looky.service;

import android.content.Context;
import android.widget.Toast;
import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.utils.MessageResponse;

public abstract class ServiceHandler extends BaseServiceHandler{


    public ServiceHandler(Context activity) {
        super(activity);
    }

    @Override
    public void onFailValidation(final MessageResponse messageResponse) {
        ((MainActivity)activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, messageResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }
}