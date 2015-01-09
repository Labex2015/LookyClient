package labex.feevale.br.looky.view.dialogs;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.model.ResponseHelp;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.BaseHandler;
import labex.feevale.br.looky.service.utils.BaseServiceAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.fragment.ChatFragment;

/**
 * Created by PabloGilvan on 28/12/2014.
 */
public class RequestHelpDialogActions implements DialogActions, BaseServiceAction<MessageResponse>{

    private String URL = AppVariables.URL+ AppVariables.RESPONSE_USER_REQUEST;

    private Boolean confirmHelp;
    private User userTo;
    private Activity activity;
    private ResponseHelp responseHelp;
    private BaseServiceAction serviceAction;
    private  User me;
    private LoadingDialog loadingDialog;

    public RequestHelpDialogActions(User userTo, Activity activity) {
        this.userTo = userTo;
        this.activity = activity;
        serviceAction = this;
        me = new SharedPreferencesUtils().getUSer(activity);
    }

    @Override
    public void cancelAction() {
       confirmHelp = false;
       assemblyNotification("O usuário "+me.getUserName()+" não pode lhe ajudar no momento.", false);
    }

    @Override
    public void confirmAction() {
        confirmHelp = true;
        assemblyNotification("O usuário "+me.getUserName()+" está disponível para lhe ajudar!", true);
    }

    private void assemblyNotification(String message, Boolean status){
        responseHelp = new ResponseHelp();
        responseHelp.user = me;
        responseHelp.status = status;
        responseHelp.message = message;

        String auxUrl = URL.replace("#ID_USER", userTo.getId().toString());
        final String urlFormatted = auxUrl;
        String auxParam =  new JsonUtils().ResponseHelptToJson(responseHelp);
        final String params = auxParam;

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                initAction();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                new BaseHandler<MessageResponse>(new MessageResponse(), params, urlFormatted, activity,
                        confirmHelp ? BaseHandler.TASK : BaseHandler.SERVICE, BaseHandler.POST, serviceAction).makeServiceCall();
                return null;
            }
        }.execute();

    }

    @Override
    public void initAction() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(confirmHelp){
                    loadingDialog = new LoadingDialog(activity);
                    loadingDialog.show();
                }
            }
        });
    }

    @Override
    public void finalizeAction() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(confirmHelp && loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();
            }
        });
    }

    @Override
    public void finalize(MessageResponse entity) {
        if(confirmHelp) {
            if (entity != null) {
                Toast.makeText(activity, entity.getMsg(), Toast.LENGTH_LONG).show();
                if(entity.getStatus())
                    ((MainActivity) activity).changeFragment(new ChatFragment(activity, me.getId(), userTo.getId()));
            }else
                Toast.makeText(activity, "Problemas ao notificar usuário.", Toast.LENGTH_LONG).show();
        }
    }
}
