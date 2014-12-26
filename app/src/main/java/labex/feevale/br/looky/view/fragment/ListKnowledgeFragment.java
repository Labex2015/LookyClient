package labex.feevale.br.looky.view.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Area;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.service.utils.AddKnowledge;
import labex.feevale.br.looky.view.adapter.KnowledgeAddAdapter;

/**
 * Created by 0139612 on 16/12/2014.
 */
public class ListKnowledgeFragment extends Fragment {
    public List<Knowledge> knowledges;
    public List<String> parameters;
    public ListView lsKnlwledges;
    public ImageButton btnAddKnowledge;
    public ImageButton btnExcluir;
    private Context context;
    public AutoCompleteTextView txAutoComplete;
    private ArrayAdapter adapter;
    public RadioGroup rg;
    private RadioButton intermediary;
    private RadioButton advanced;
    private RadioButton basic;
    private Area area;

    public ListKnowledgeFragment(Context context, List<Knowledge> knowledges, List<String> parameters) {
        this.knowledges = knowledges;
        this.parameters = parameters;
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            setRetainInstance(true);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_knowledge, container, false);

        lsKnlwledges = (ListView) view.findViewById(R.id.lv_knowledges);
        btnAddKnowledge = (ImageButton) view.findViewById(R.id.ibtn_addknowledge);
        btnExcluir = (ImageButton) view.findViewById(R.id.ibtn_excluir);

        btnAddKnowledge.setOnClickListener( newKnowledge() );
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lsKnlwledges.setAdapter((new KnowledgeAddAdapter(knowledges, getActivity())));
        adapter = new ArrayAdapter (getActivity(),android.R.layout.select_dialog_item, parameters);

    }

    private View.OnClickListener newKnowledge() {
    return new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_dialog_add_knowledge);
            dialog.setTitle(R.string.NEW_KNOWLEDGE_TITLE);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

            advanced = (RadioButton) dialog.findViewById(R.id.radioButton3);
            intermediary = (RadioButton) dialog.findViewById(R.id.radioButton2);
            basic = (RadioButton) dialog.findViewById(R.id.radioButton);

            txAutoComplete = (AutoCompleteTextView) dialog.findViewById(R.id.actv_autocomplete);
            txAutoComplete.setAdapter(adapter);

            Knowledge knowledge = new Knowledge();
            area = new Area();

            rg = (RadioGroup) dialog.findViewById(R.id.radioGroup);
            dialogButton.setOnClickListener(newKnowledgeAction(knowledge, dialog));

            dialog.show();

        }
    };
    }

    public View.OnClickListener newKnowledgeAction(final Knowledge knowledge, final Dialog dialog){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                area.setName(txAutoComplete.getText().toString().trim());
                knowledge.setArea(area);

                knowledge.setNivel(basic.isChecked() ? Knowledge.INITIATE : intermediary.isChecked() ? Knowledge.INTERMEDIARY : Knowledge.ADVANCED);
                AddKnowledge addKnowledge = new AddKnowledge(context, knowledge, knowledges);
                addKnowledge.execute();
                dialog.dismiss();
            }
        };
    }

    public void verifyNivelAndView(String nivel){
        if(nivel.equals(Knowledge.INITIATE))
            basic.setSelected(true);
        else if(nivel.equals(Knowledge.ADVANCED))
            advanced.setSelected(true);
        else if (nivel.equals(Knowledge.INTERMEDIARY))
            intermediary.setSelected(true);
    }
}
