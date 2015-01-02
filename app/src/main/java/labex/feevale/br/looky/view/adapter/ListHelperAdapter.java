package labex.feevale.br.looky.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;

/**
 * Created by Jeferson on 09/12/2014.
 * ticket 1.2
 */
public class ListHelperAdapter extends BaseAdapter{
    private List<User> users;
    private Context context;
    private Long idKnowledge;

    private TextView mName, mKnowledge, mLevel, labelPage, textDistance;

    Typeface tf;

    public ListHelperAdapter (Context context, List<User> users){
        this.context = context;
        this.users = users;
        this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
    }

    public ListHelperAdapter (Context context, List<User> users, Long idKnowledge){
        this(context, users);
        this.idKnowledge = idKnowledge;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        User user       = (User) getItem(position);
        view            = LayoutInflater.from(context).inflate(R.layout.fragment_item_helper,null);

        labelPage       = (TextView) view.findViewById(R.id.item_helper_labelPage);
        labelPage.setText(R.string.list_helpers_label_page);
        labelPage.setTypeface(tf);
        mName           = (TextView) view.findViewById(R.id.textName);
        mName.setTypeface(tf);
        mKnowledge      = (TextView) view.findViewById(R.id.textknowledge);
        mKnowledge.setTypeface(tf);
        mLevel          = (TextView) view.findViewById(R.id.textLevel);
        mLevel.setTypeface(tf);
        textDistance    = (TextView) view.findViewById(R.id.text_distance);
        textDistance.setText(R.string.list_helpers_text_distance);
        textDistance.setTypeface(tf);
        ImageView mImgUser = (ImageView) view.findViewById(R.id.imageUser);


        mName.setText(user.getUserName());
        //TODO analisar como sera exibido o nivel ou os conhecimentos, sendo que a busca não é feita mais por tags
        mLevel.setText(user.getLevelById(idKnowledge));
        //TODO fazer o set da imagem do usuário quando tiver

        return view;
    }
}
