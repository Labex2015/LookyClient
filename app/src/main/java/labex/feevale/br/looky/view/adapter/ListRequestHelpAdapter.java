package labex.feevale.br.looky.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.wrapper.HelpResponseWrapper;

/**
 * Created by Jeferson on 15/12/2014.
 * ticket 1.2
 */
public class ListRequestHelpAdapter extends BaseAdapter{
    private List<HelpResponseWrapper> helpResponseWrapperList;
    private Context context;

    public ListRequestHelpAdapter(Context context, List<HelpResponseWrapper> helpResponseWrapperList){
        this.context = context;
        this.helpResponseWrapperList = helpResponseWrapperList;
    }

    @Override
    public int getCount() {
        return helpResponseWrapperList.size();
    }

    @Override
    public Object getItem(int position) {
        return helpResponseWrapperList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        HelpResponseWrapper helpResponseWrapper = (HelpResponseWrapper) getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.fragment_item_helper,null);

        TextView mName = (TextView) view.findViewById(R.id.textName);
        TextView mSearchTerms = (TextView) view.findViewById(R.id.textLevel);
        ImageView mImgUser = (ImageView) view.findViewById(R.id.imageUser);

        mName.setText(helpResponseWrapper.user.getUserName());

        String mAux = "";

        for(String term : helpResponseWrapper.searchTerms){
            mAux += term + " ";
        }

        mSearchTerms.setText(mAux);
        //TODO fazer o set da imagem do usuário quando tiver

        return view;
    }
}
