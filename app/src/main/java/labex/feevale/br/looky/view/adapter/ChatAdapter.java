package labex.feevale.br.looky.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Talk;

/**
 * Created by Vitor on 11/12/2014.
 */
public class ChatAdapter extends ArrayAdapter<List<Talk>> {

    List<Talk> talk;
    Context context;

    public ChatAdapter(Context context, List<Talk> talk) {
        super(context, R.layout.fragment_chat);
        this.talk = talk;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.talk.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_chat, parent, false);
        TextView t = (TextView) rowView.findViewById(R.id.titulo);
        return rowView;
    }
}
