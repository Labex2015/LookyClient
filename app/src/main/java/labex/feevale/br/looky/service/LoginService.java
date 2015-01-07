package labex.feevale.br.looky.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.Toast;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.utils.BaseServiceAction;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.dialogs.LoadingDialog;
import labex.feevale.br.looky.view.fragment.MainFragment;
import labex.feevale.br.looky.wrapper.RegisterLogin;

/**
 * Created by 0118230 on 30/12/2014.
 */
public class LoginService extends BaseHandler<User> {

    public static String URL_LOGIN = AppVariables.URL+AppVariables.LOGIN_USER;


    protected LoginService(User entity, String params, Context context,
                           Integer type, Integer methodConnection, BaseServiceAction baseServiceAction) {
        super(entity, params, URL_LOGIN, context, type, methodConnection, baseServiceAction);
    }


    @Override
    protected void finalize(User entity) {
        if(entity != null && entity.getId() > 0){
            new SharedPreferencesUtils().saveUser(getContext(), entity);
            ((MainActivity)getContext()).changeFragment(new MainFragment());
        }else{
            Toast.makeText(getContext(), "Dados não conferem!", Toast.LENGTH_LONG).show();
        }
    }
 }

