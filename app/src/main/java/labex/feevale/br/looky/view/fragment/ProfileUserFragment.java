package labex.feevale.br.looky.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.SendRequestUserHelpService;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.adapter.KnowledgeAdapter;
import labex.feevale.br.looky.view.dialogs.DialogActions;
import labex.feevale.br.looky.view.dialogs.DialogMaker;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ProfileUserFragment extends Fragment implements DialogActions{
    private Context context;
    private User user;
    private double distance;
    private List<Knowledge> knowledgeList;

    private TextView textName, textDistance;
    private ImageView imgBUser;
    private ListView listVKnowledges;
    private ImageButton requestHelpButton;
    private DialogActions actions;

    public ProfileUserFragment(Context context, User user, double distance) {
        this.context = context;
        this.user = user;
        this.distance = distance;
        this.knowledgeList = user.getKnowledgeList();
        this.actions = this;
    }

    public ProfileUserFragment(Context context, User user) {
        this.context = context;
        this.user = user;
        this.knowledgeList = user.getKnowledgeList();
        this.actions = this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile_user, container, false);
        textName        = (TextView) view.findViewById(R.id.text_name);
        textDistance    = (TextView) view.findViewById(R.id.text_distance);
        imgBUser        = (ImageView) view.findViewById(R.id.imgb_User);
        listVKnowledges = (ListView) view.findViewById(R.id.listV_knowledges);
        requestHelpButton = (ImageButton) view.findViewById(R.id.buttonRequestHelp);

        requestHelpButton.setOnClickListener(requestHelpListener());
        imgBUser.setOnClickListener(showImage());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listVKnowledges.setAdapter(new KnowledgeAdapter(knowledgeList, context));
        textName.setText(user.getUserName());
        textDistance.setText(user.getDistance(new SharedPreferencesUtils().getUSer(context)));
    }

    private View.OnClickListener showImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO exibir imagem do usuário
            }
        };
    }

    private View.OnClickListener requestHelpListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogMaker("Solicitação de ajuda", "Solicitar a ajuda de "+
                        user.getUserName()+" ?", actions).createDialog(getActivity()).show();
            }
        };
    }

    @Override
    public void cancelAction() {}//TODO: ver o que fazer quando o usuário cancelar a solicitacao

    @Override
    public void confirmAction() {
        new SendRequestUserHelpService(getActivity(),user.getId()).execute();
    }
}
