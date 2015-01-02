package labex.feevale.br.looky.service.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.fragment.ListKnowledgeFragment;
import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.ServiceHandler;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.PreferencesHelp;
import labex.feevale.br.looky.wrapper.KnowledgeWrapper;

/**
 * Created by Vitor on 16/12/2014.
 */
public class GetKnowledges extends ServiceHandler {

    Context context;

    public GetKnowledges(Context context){
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

    public void execute(){
        User user           = new SharedPreferencesUtils().getUSer(context);
        super.makeServiceCall(AppVariables.URL + AppVariables.USER_VERB + user.getId() + "/" + AppVariables.KNOWLEDGE_VERB, GET, null);
    }

    @Override
    protected void postExecute(String response) {
        KnowledgeWrapper knowledgeWrapper = new JsonUtils().JsonToKnowledgeWrapper(response);
        List<Knowledge> knowledges = knowledgeWrapper.knowledges;
        List<String> parameters = knowledgeWrapper.parameters;

        ((MainActivity) context).changeFragment(new ListKnowledgeFragment(context, knowledges, parameters));
    }
}
