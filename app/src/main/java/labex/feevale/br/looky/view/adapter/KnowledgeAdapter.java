package labex.feevale.br.looky.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;

/**
 * Created by jeferson on 17/12/2014.
 */
public class KnowledgeAdapter extends BaseAdapter {
    private List<Knowledge> knowledgeList;
    private Context context;

    public KnowledgeAdapter(List<Knowledge> knowledgeList, Context context) {
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

        view = LayoutInflater.from(context).inflate(R.layout.fragment_item_knowledge,null);

        TextView mTitle = (TextView) view.findViewById(R.id.knowledge_title);
        mTitle.setText((knowledge.getArea().getName() + " - " + knowledge.getNivel()).toUpperCase());

        return view;
    }
}
