package labex.feevale.br.looky.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.service.BaseHandler;
import labex.feevale.br.looky.service.utils.BaseServiceAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by 0139612 on 16/12/2014.
 */
public class KnowledgeAddAdapter extends BaseAdapter implements BaseServiceAction<MessageResponse>{
    private List<Knowledge> knowledgeList;
    private Context context;
    private BaseServiceAction serviceAction;

    public KnowledgeAddAdapter(List<Knowledge> knowledgeList, Context context) {
        this.knowledgeList = knowledgeList;
        this.context = context;
        serviceAction = this;
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
                String url = AppVariables.URL+ AppVariables.USER_VERB +new SharedPreferencesUtils().getUSer(context).getId()
                        + "/knowledge/remove/"+ knowledge.getArea().getId();
                new BaseHandler<MessageResponse>(new MessageResponse(),url, context, BaseHandler.TASK,
                        BaseHandler.POST, serviceAction).makeServiceCall();
            }
        };
    }

    @Override
    public void initAction() {
        //TODO: Adicionar chamada ao dialog
    }

    @Override
    public void finalizeAction() {
        //TODO: Encerrar exibição do dialog
    }

    @Override
    public void finalize(MessageResponse entity) {
        if(entity != null){
            if(entity.getStatus())
                notifyDataSetChanged();

            Toast.makeText(context,entity.getMsg(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Problemas ao remover dado.", Toast.LENGTH_LONG).show();
        }
    }
}
