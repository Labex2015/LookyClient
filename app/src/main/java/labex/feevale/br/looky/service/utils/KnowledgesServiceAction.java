package labex.feevale.br.looky.service.utils;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.view.fragment.ListKnowledgeFragment;
import labex.feevale.br.looky.wrapper.KnowledgeWrapper;

/**
 * Created by 0126128 on 07/01/2015.
 */
public class KnowledgesServiceAction implements BaseServiceAction<KnowledgeWrapper> {

    private Activity activity;

    public KnowledgesServiceAction(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void initAction() {

    }

    @Override
    public void finalizeAction() {

    }

    @Override
    public void finalize(KnowledgeWrapper entity) {
        if(entity != null){
            List<Knowledge> knowledges = entity.knowledges;
            List<String> parameters = entity.parameters;
            ((MainActivity) activity).changeFragment(new ListKnowledgeFragment(activity, knowledges, parameters));
        }else{
            Toast.makeText(activity,"Não foi possível carregar a tela de conhecimentos.", Toast.LENGTH_LONG).show();
        }
    }
}
