package labex.feevale.br.looky.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.service.RemoveKnowledge;
import labex.feevale.br.looky.service.utils.KnowledgesTask;

/**
 * Created by 0139612 on 16/12/2014.
 */
public class KnowledgeAddAdapter extends BaseAdapter {
    private List<Knowledge> knowledgeList;
    private Context context;

    public KnowledgeAddAdapter(List<Knowledge> knowledgeList, Context context) {
        this.knowledgeList = knowledgeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return knowledgeList.size();
    }

    @Override
    public Object getItem(int position) {
        return knowledgeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Knowledge knowledge = (Knowledge) getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.fragment_item_knowledge_add,null);

        TextView mTitle = (TextView) view.findViewById(R.id.knowledge_title);
        TextView mLevel = (TextView) view.findViewById(R.id.knowledge_level);
        ImageButton delKnowledge = (ImageButton) view.findViewById(R.id.ibtn_excluir);

        mTitle.setText(knowledge.getArea().getName());
        mLevel.setText(knowledge.getNivel());

        delKnowledge.setOnClickListener( removeKnowledge(knowledge) );
        notifyDataSetChanged();
        return view;
    }

    private View.OnClickListener removeKnowledge(final Knowledge knowledge) {
        final BaseAdapter baseAdapter = this;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO:Adicionar um observer para notificar alteração na lista, ou algum padrão decente
                RemoveKnowledge removeKnowledge = new RemoveKnowledge(context,knowledge, knowledgeList);
                removeKnowledge.notifyAdapterChanged(baseAdapter);
                new KnowledgesTask(removeKnowledge,(MainActivity)context, removeKnowledge).execute();
                notifyDataSetChanged();
            }
        };
    }
}
