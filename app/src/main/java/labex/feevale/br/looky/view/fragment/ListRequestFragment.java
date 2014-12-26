package labex.feevale.br.looky.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.view.adapter.ListRequestHelpAdapter;
import labex.feevale.br.looky.wrapper.HelpResponseWrapper;


/**
 * Created by Jeferson on 15/12/2014.
 * ticket 1.2
 */
public class ListRequestFragment extends Fragment {
    List<HelpResponseWrapper> helpResponseWrapperList;
    ListView listRequest;

    public ListRequestFragment(List<HelpResponseWrapper> helpResponseWrapperList) {
        this.helpResponseWrapperList = helpResponseWrapperList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list_helpers, container, false);

        listRequest = (ListView) view.findViewById(R.id.listHelpers);
        listRequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: abrir proxima tela

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listRequest.setAdapter((new ListRequestHelpAdapter(getActivity(), helpResponseWrapperList)));
    }
}

