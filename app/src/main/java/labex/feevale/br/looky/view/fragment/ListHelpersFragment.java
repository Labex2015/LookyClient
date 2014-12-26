package labex.feevale.br.looky.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;


import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.view.adapter.ListHelperAdapter;

/**
 * Created by Jeferson on 09/12/2014.
 * ticket 1.2
 */
public class ListHelpersFragment extends Fragment {

    private Context context;
    List<User> userList;
    ListView listHelpers;
    Long idKnowledgeFind;

    public ListHelpersFragment(Context context, List<User> userList, Long idKnowledgeFind) {
        this.context = context;
        this.userList = userList;
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
        listHelpers.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //TODO: Chamar tela de pedido de ajuda!!
                //ChatFragment chatFragment = new ChatFragment((MainActivity)context);
                //((MainActivity) context).changeFragment(chatFragment);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listHelpers.setAdapter((new ListHelperAdapter(getActivity(),userList, idKnowledgeFind)));
    }
}

