package labex.feevale.br.looky.controller;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.service.ListAreasNameService;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.view.fragment.RequestHelpFragment;

/**
 * Created by PabloGilvan on 25/12/2014.
 */
public class LoadRequestHelpFragment extends ModelBasicTask {


    private List<String> areaNames = new ArrayList<String>();

    public LoadRequestHelpFragment(String messageDialog, Activity activity) {
        super(messageDialog, activity);
        messageResponse = new MessageResponse("", false);
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), messageResponse.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        ((MainActivity)getActivity()).changeFragment(new RequestHelpFragment(areaNames));
    }

    @Override
    protected MessageResponse doInBackground(Void... voids) {
        new ListAreasNameService(getActivity(), areaNames, messageResponse).execute();
        if(areaNames == null)
            messageResponse.setStatus(false);
        return messageResponse;
    }
}
