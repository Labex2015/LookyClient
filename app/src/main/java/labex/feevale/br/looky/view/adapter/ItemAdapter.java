package labex.feevale.br.looky.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Area;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.view.fragment.ListHelpersFragment;

/**
 * Created by Vitor/Thaiane on 18/12/2014.
 */

public enum ItemAdapter { // TODO trocar para dados reias!

    HELPERS("Helpers"){
        public void executeAction(Activity activity){
            Context context = null;
            User u = new User();
            Knowledge k = new Knowledge();
            Area a = new Area();
            a.setId(1L);
            a.setName("Java");
            k.setArea(a);
            k.setNivel("Iniciante");
            u.setId(1L);
            u.setUserName("Joaozinho");
            u.setEmail("jo@mail.com");
            List<Knowledge> ks = new ArrayList<Knowledge>();
            ks.add(k);
            u.setKnowledgeList(ks);
            List<User> us = new ArrayList<User>();

            us.add(u);
            ((MainActivity)activity).changeFragment(new ListHelpersFragment(context,us , 1L));
        }
    },
    KNOWLEDGES("Knowledges"){
        public void executeAction(Activity activity){

        }
    },
    REQUESTS("Requests"){
        public void executeAction(Activity activity){

        }
    },
    PROFILE("Profile"){
        public void executeAction(Activity activity){

        }
    };

    private Activity activity;
    private DrawerHandler drawerHandler;
    private String text;

    ItemAdapter(String text){
        this.text = text;
    }

    public View setItemView(Activity activity, DrawerHandler drawerHandler){
        this.activity = activity;
        this.drawerHandler = drawerHandler;
        View myItem = activity.getLayoutInflater().inflate(R.layout.drawer_item, null);
        TextView txMenu = (TextView)myItem.findViewById(R.id.txt_menutext);
        txMenu.setText(text);
        myItem.setOnClickListener(callFuncionality());
        return myItem;
    }

    public View.OnClickListener callFuncionality(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerHandler.executeActionDrawer();
                executeAction(activity);
            }
        };
    }

    public void executeAction(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
