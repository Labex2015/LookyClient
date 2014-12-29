package labex.feevale.br.looky.view.dialogs;

import android.app.Activity;
import android.widget.Toast;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.controller.ResponseHelpTask;
import labex.feevale.br.looky.model.ResponseHelp;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.fragment.ChatFragment;

/**
 * Created by PabloGilvan on 28/12/2014.
 */
public class RequestHelpDialogActions implements DialogActions {

    private User userTo;
    private Activity activity;
    private ResponseHelp responseHelp;

    public RequestHelpDialogActions(User userTo, Activity activity) {
        this.userTo = userTo;
        this.activity = activity;
    }

    @Override
    public void cancelAction() {
        responseHelp = new ResponseHelp();
        responseHelp.user = userTo;
        responseHelp.status = false;
        responseHelp.message = "O usuário "+userTo.getUserName()+" não pode lhe ajudar no momento.";
        new ResponseHelpTask("Notificando usuário.", activity, responseHelp).execute();
    }

    @Override
    public void confirmAction() {
         User me = new SharedPreferencesUtils().getUSer(activity);
        ((MainActivity)activity).changeFragment(new ChatFragment(activity, me.getId(), userTo.getId()));
    }
}
