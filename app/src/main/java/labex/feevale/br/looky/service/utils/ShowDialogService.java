package labex.feevale.br.looky.service.utils;

import android.app.Activity;

import labex.feevale.br.looky.view.dialogs.LoadingDialog;

/**
 * Created by 0126128 on 07/01/2015.
 */
public class ShowDialogService implements BaseServiceAction {

    private LoadingDialog loadingDialog;
    private Activity activity;

    public ShowDialogService(Activity activity) {
        this.activity = activity;
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
