package labex.feevale.br.looky.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.BaseHandler;
import labex.feevale.br.looky.service.utils.BaseServiceAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.view.adapter.ListHelperAdapter;

/**
 * Created by Jeferson on 09/12/2014.
 * ticket 1.2
 */
public class ListHelpersFragment extends Fragment implements BaseServiceAction<User>{

    private Context context;
    private List<User> userList;
    private ListView listHelpers;
    private Long idKnowledgeFind;
    private BaseServiceAction baseServiceAction;

    public ListHelpersFragment(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        baseServiceAction = this;
    }

    public ListHelpersFragment(Context context, List<User> userList, Long idKnowledgeFind) {
        this(context, userList);
        this.idKnowledgeFind = idKnowledgeFind;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list_helpers, container, false);
        listHelpers = (ListView) view.findViewById(R.id.listHelpers);
        listHelpers.setEmptyView(view.findViewById(R.id.emptyListTextView));

        listHelpers.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = userList.get(i);
                String url = AppVariables.URL + AppVariables.USER_VERB + user.getId();
                new BaseHandler<User>(new User(), url, getActivity(), BaseHandler.TASK, BaseHandler.GET, baseServiceAction).makeServiceCall();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listHelpers.setAdapter((new ListHelperAdapter(getActivity(),userList)));
    }

    @Override
    public void initAction() {}

    @Override
    public void finalizeAction() {

    }

    @Override
    public void finalize(User entity) {
        if(entity != null && entity.getId() > 0)
            ((MainActivity) context).changeFragment(new ProfileUserFragment(context, entity));
        else
            Toast.makeText(getActivity(), "Problemas ao carregar perfil do usuário.", Toast.LENGTH_LONG).show();
    }
}

