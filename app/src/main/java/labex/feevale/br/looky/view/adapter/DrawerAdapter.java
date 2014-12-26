package labex.feevale.br.looky.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Vitor/Thaiane on 18/12/2014.
 */
public class DrawerAdapter extends BaseAdapter {
    private Activity activity;
    private List<ItemAdapter> itemsAdapter;
    private LayoutInflater inflater;
    private DrawerHandler drawerHandler;

    public DrawerAdapter(Activity activity, List<ItemAdapter> itemsAdapter, DrawerHandler drawerHandler) {
        this.activity = activity;
        this.itemsAdapter = itemsAdapter;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.drawerHandler = drawerHandler;
    }

    @Override
    public int getCount() {
        return itemsAdapter.size();
    }

    @Override
    public Object getItem(int i) {
        return itemsAdapter.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = itemsAdapter.get(position).setItemView(activity, drawerHandler);
        return view;
    }

}