package labex.feevale.br.looky.view.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.controller.LoadRequestHelpFragment;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.BaseHandler;
import labex.feevale.br.looky.service.utils.KnowledgesServiceAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.wrapper.KnowledgeWrapper;

/**
 * Created by PabloGilvan on 01/01/2015.
 */
public class MainFragment extends Fragment {

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton searchButton = (ImageButton) view.findViewById(R.id.menuPrincipalSearch);
        searchButton.setOnClickListener(loadSearchHelpFragment());
        ImageButton profileButton = (ImageButton) view.findViewById(R.id.menuPrincipalProfile);
        profileButton.setOnClickListener(loadProfileFragment());
        ImageButton chatButton = (ImageButton) view.findViewById(R.id.menuPrincipalChat);
        user = new SharedPreferencesUtils().getUSer(getActivity());
        return view;
    }

    private View.OnClickListener loadProfileFragment() {
            return new View.OnClickListener() {
                @Override
                public void onClick (View view){
                    if (user != null){
                        final String url = AppVariables.URL + AppVariables.USER_VERB + user.getId() + "/" + AppVariables.KNOWLEDGE_VERB;
                        final KnowledgesServiceAction knowledgesServiceAction = new KnowledgesServiceAction(getActivity());

                        new AsyncTask<Void,Void,Void>(){

                            @Override
                            protected void onPreExecute() {
                                knowledgesServiceAction.initAction();
                            }

                            @Override
                            protected Void doInBackground(Void... params) {
                                new BaseHandler<KnowledgeWrapper>(new KnowledgeWrapper(), url, getActivity(), BaseHandler.TASK, BaseHandler.GET,knowledgesServiceAction).makeServiceCall();
                                return null;
                            }
                        }.execute();

                    }
                }
            };
    }

    private View.OnClickListener loadSearchHelpFragment() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new LoadRequestHelpFragment(getActivity()).execute();
                }
            };
    }
}
