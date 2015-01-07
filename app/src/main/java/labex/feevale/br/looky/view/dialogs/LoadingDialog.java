package labex.feevale.br.looky.view.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;

/**
 * Created by 0126128 on 07/01/2015.
 */
public class LoadingDialog extends ProgressDialog{


    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog_default);
        setCancelable(false);
        setIndeterminate(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
