package labex.feevale.br.looky.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.view.fragment.ListHelpersFragment;
import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.wrapper.HelpWrapper;

/**
 * Created by Vitor on 12/12/2014.
 */
public class RequestHelpService extends ServiceHandler {
    Context context;

    public RequestHelpService(Context context){
        super((Activity)context);
        this.context = context;
    }

    @Override
    public boolean validation() {
        return new AppHelp(context).validateConnection();
    }

    @Override
    public void onFailValidation() {
        Toast.makeText(context, R.string.CONNECTION_ERROR, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void postExecute(String response) {
        List<User> users = new ArrayList<User>();
        if(!response.trim().equals("[]")) {
            users = new JsonUtils().JsonToListUsers(response);
            //TODO: conferir como tratar o conhecimento que esta sendo procurado
        }
        ((MainActivity) context).changeFragment(new ListHelpersFragment(context, users));
    }

    @Override
    public void execute() {}

    public void execute(HelpWrapper helpWrapper){
        super.makeServiceCall(AppVariables.URL + AppVariables.REQUEST_HELP_VERB, POST, new JsonUtils().RequestToJson(helpWrapper));
    }
}
