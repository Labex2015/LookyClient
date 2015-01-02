package labex.feevale.br.looky.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import labex.feevale.br.looky.view.fragment.ProfileUserFragment;
import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.PreferencesHelp;

/**
 * Created by 0118230 on 17/12/2014.
 */
public class LoadProfileService extends ServiceHandler {
    Context context;

    public LoadProfileService(Context context) {
        super((MainActivity)context);
        this.context = context;
    }

    @Override
    public boolean validation() {
        return new AppHelp(context).validateConnection();
    }

    @Override
    public void onFailValidation() {
        Log.w(context.getResources().getString(R.string.TAG_CONNECTION).toString(), context.getResources().getString(R.string.CONNECTION_ERROR).toString());
    }

    @Override
    protected void postExecute(String response) {

        User userHelper;
        User user = new PreferencesHelp(context).getUserToPreferences();
        JsonUtils jsonUtils = new JsonUtils();
        userHelper = jsonUtils.JsonToUser(response);


        if(userHelper != null){
            ((MainActivity) context).changeFragment(new ProfileUserFragment(context, userHelper, user.getDistance(userHelper.getLatitude(),userHelper.getLongitude(),'K')));
        }else{
            Toast.makeText(context, context.getResources().getText(R.string.OPERATION_FAIL),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void execute() {
    }

    public void execute(Long idUser){
        super.makeServiceCall(AppVariables.URL + AppVariables.USER_VERB + idUser , GET, null );
    }




}
