package labex.feevale.br.looky.service;

import android.app.Activity;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.utils.ProcessMessage;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by 0139612 on 17/12/2014.
 */
public class RemoveKnowledge extends ServiceHandler implements ProcessMessage {

    Context context;
    private Knowledge knowledge;
    private MessageResponse messageResponse;
    private List<Knowledge> knowledges;
    private BaseAdapter baseAdapter;

    public RemoveKnowledge(Context context, Knowledge knowledge, List<Knowledge> knowledges){
        super((Activity)context);
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
        User user           = new SharedPreferencesUtils().getUSer(context);
        JsonUtils jsonUtils = new JsonUtils();
        super.makeServiceCall(AppVariables.URL
                + AppVariables.USER_VERB
                + user.getId() + "/knowledge/remove/"
                + knowledge.getArea().getId(),
                POST, null);
    }

    @Override
    protected void postExecute(String response) {
        messageResponse = new JsonUtils().JsonToMessageResponse(response);
        if(messageResponse.getStatus()) {
            knowledges.remove(knowledge);
            ((MainActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    baseAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public MessageResponse getResponse() {
        return messageResponse;
    }

    @Override
    public void notifyAdapterChanged(BaseAdapter adapter) {
        this.baseAdapter = adapter;
    }
}
