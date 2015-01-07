package labex.feevale.br.looky.view.dialogs;

import android.app.Activity;

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
       assemblyNotification("O usuário "+userTo.getUserName()+" não pode lhe ajudar no momento.", false);
    }

    @Override
    public void confirmAction() {
         User me = new SharedPreferencesUtils().getUSer(activity);
         assemblyNotification("O usuário "+userTo.getUserName()+" aceitou lhe ajudar!", true);
         ((MainActivity)activity).changeFragment(new ChatFragment(activity, me.getId(), userTo.getId()));
    }

    private void assemblyNotification(String message, Boolean status){
        responseHelp = new ResponseHelp();
        responseHelp.user = userTo;
        responseHelp.status = status;
        responseHelp.message = message;
        new ResponseHelpTask("Notificando usuário.", activity, responseHelp).execute();
    }
}
