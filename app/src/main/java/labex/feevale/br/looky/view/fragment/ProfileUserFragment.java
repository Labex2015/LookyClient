package labex.feevale.br.looky.view.fragment;



import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.SendRequestUserHelpService;
import labex.feevale.br.looky.view.adapter.KnowledgeAdapter;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ProfileUserFragment extends Fragment {
    private Context context;
    private User user;
    private double distance;
    private List<Knowledge> knowledgeList;

    private TextView textName, textDistance;
    private ImageView imgBUser;
    private ListView listVKnowledges;

    public ProfileUserFragment(Context context, User user, double distance) {
        this.context = context;
        this.user = user;
        this.distance = distance;
        this.knowledgeList = user.getKnowledgeList();
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
        view.setOnLongClickListener(requestHelpListener());
        textName        = (TextView) view.findViewById(R.id.text_name);
        textDistance    = (TextView) view.findViewById(R.id.text_distance);
        imgBUser        = (ImageView) view.findViewById(R.id.imgb_User);
        listVKnowledges = (ListView) view.findViewById(R.id.listV_knowledges);

        imgBUser.setOnClickListener(showImage());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listVKnowledges.setAdapter(new KnowledgeAdapter(knowledgeList, context));
        textName.setText(user.getUserName());

        double value = distance * 1000;
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

        textDistance.setText("A " + bd.doubleValue() + " metros de você");
    }

    private View.OnClickListener showImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO exibir imagem do usuário
            }
        };
    }

    private View.OnLongClickListener requestHelpListener(){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new SendRequestUserHelpService(getActivity(),user.getId()).execute();
                return true;
            }
        };
    }

}
