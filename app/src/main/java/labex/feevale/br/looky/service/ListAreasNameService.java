package labex.feevale.br.looky.service;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by PabloGilvan on 25/12/2014.
 */
public class ListAreasNameService extends ServiceHandler {

    private Activity activity;
    private List<String> names;
    private MessageResponse messageResponse;

    public ListAreasNameService(Activity activity, List<String> names, MessageResponse messageResponse) {
        super(activity);
        this.activity = activity;
        this.names = names;
        this.messageResponse = messageResponse;
    }

    @Override
    public boolean validation() {
        return new AppHelp(activity).validateConnection();
    }

    @Override
    public void onFailValidation() {
        messageResponse.setStatus(false);
        messageResponse.setMsg("Sem conexão com a internet!");
    }

    @Override
    protected void postExecute(String response) {
        List<String> responseNames = new JsonUtils(new ArrayList<String>()).jsonStringList(response);
        if(response != null) {
            names.addAll(responseNames);
            messageResponse.setStatus(true);
        }else {
            messageResponse.setMsg("Problemas ao processar consulta ao servidor.");
            messageResponse.setStatus(false);
        }
    }

    @Override
    public void execute() {
        makeServiceCall(AppVariables.URL+AppVariables.AREAS_NAME, GET, null);
    }
}
