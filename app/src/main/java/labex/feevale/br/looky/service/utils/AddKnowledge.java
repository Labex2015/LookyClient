package labex.feevale.br.looky.service.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.ServiceHandler;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.PreferencesHelp;

/**
 * Created by Vitor on 16/12/2014.
 */
public class AddKnowledge extends ServiceHandler {
    Context context;
    private Knowledge knowledge;
    private List<Knowledge> knowledges;

    public AddKnowledge(Context context, Knowledge knowledge, List<Knowledge> knowledges){
        this.knowledge = knowledge;
        this.context = context;
        this.knowledges = knowledges;
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
        User user           = new PreferencesHelp(context).getUserToPreferences();
        JsonUtils jsonUtils = new JsonUtils();
        System.out.println(knowledge.getArea() == null);
        System.out.println(knowledge.getNivel() == null);
        System.out.println(knowledge.getArea().getName() == null);

        super.makeServiceCall(AppVariables.URL
                            + AppVariables.USER_VERB
                            + user.getId() + "/knowledge/", POST, jsonUtils.KnowledgeToJson(knowledge));
    }

    @Override
    protected void postExecute(String response) {
        JsonUtils jsonUtils = new JsonUtils();
        Knowledge knowledge = jsonUtils.JsonToKnowledge(response);

        if(knowledge != null){
            Toast.makeText(context, context.getResources().getString(R.string.SUCCESS), Toast.LENGTH_LONG).show();
            knowledges.add(knowledge);
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.OPERATION_FAIL), Toast.LENGTH_LONG).show();
        }
    }
}

