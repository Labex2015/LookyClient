package labex.feevale.br.looky.view.dialogs;

import android.app.Activity;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.view.fragment.ChatFragment;

/**
 * Created by 0126128 on 09/01/2015.
 */
public class ResponseRequestDialogAction implements DialogActions {
    private Activity activity;
    private Long idFrom, idTo;

    public ResponseRequestDialogAction(Activity activity, Long idFrom, Long idTo) {
        this.activity = activity;
        this.idFrom = idFrom;
        this.idTo = idTo;
    }

    @Override
    public void cancelAction() {}

    @Override
    public void confirmAction() {
        ((MainActivity)activity).changeFragment(new ChatFragment(activity, idFrom, idTo));
    }
}
