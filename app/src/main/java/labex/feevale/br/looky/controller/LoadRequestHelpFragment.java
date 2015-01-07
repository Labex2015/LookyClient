package labex.feevale.br.looky.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.service.BaseHandler;
import labex.feevale.br.looky.service.utils.BaseServiceAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.view.fragment.RequestHelpFragment;

/**
 * Created by PabloGilvan on 25/12/2014.
 */
public class LoadRequestHelpFragment extends AsyncTask<Void, Void, Void> implements BaseServiceAction<List<String>>{
    public static final String URL = AppVariables.URL+AppVariables.AREAS_NAME;

    private ProgressDialog progressDialog;
    private Activity activity;
    private List<String> areaNames = new ArrayList<String>();

    public LoadRequestHelpFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        initAction();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new BaseHandler<List<String>>(areaNames, URL, activity, BaseHandler.TASK, BaseHandler.GET, this).makeServiceCall();
        return null;
    }

    @Override
    public void initAction() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando.....");
        progressDialog.show();
    }

    @Override
    public void finalizeAction() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void finalize(List<String> entity) {
        if(entity != null){
            ((MainActivity)activity).changeFragment(new RequestHelpFragment(entity));
        }else{
            Toast.makeText(activity, "Problema ao carregar tela de ajuda", Toast.LENGTH_LONG).show();
        }
    }
}
