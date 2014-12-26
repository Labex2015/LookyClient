package labex.feevale.br.looky;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.Arrays;
import java.util.List;

import labex.feevale.br.looky.service.CancelHelpService;
import labex.feevale.br.looky.view.adapter.DrawerAdapter;
import labex.feevale.br.looky.view.adapter.DrawerHandler;
import labex.feevale.br.looky.view.adapter.ItemAdapter;
import static labex.feevale.br.looky.R.id.drawer_layout;
import static labex.feevale.br.looky.R.id.navigation_drawer;


public class MainActivity extends FragmentActivity implements DrawerHandler{

    private Fragment mFragment;
    private  Intent mService;
    private CharSequence mTitle;
    private Fragment     fragment;

    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    protected ListView drawerListView;
    protected List<ItemAdapter> itemsAdapter;

    public MainActivity() {
        itemsAdapter = Arrays.asList(ItemAdapter.HELPERS, ItemAdapter.KNOWLEDGES, ItemAdapter.PROFILE,
                ItemAdapter.REQUESTS);
    }

    public MainActivity(Fragment fragment) {
        this();
        this.fragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);



        drawerListView = (ListView) findViewById(navigation_drawer);
        drawerListView.setAdapter(new DrawerAdapter(this, itemsAdapter, (DrawerHandler) this));
        drawerLayout = (DrawerLayout) findViewById(drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //ativa serviço
        /*mService = new Intent(this, GetPositionService.class);
        startService(mService);*/

        //new GetKnowledges(this).execute();
        //new GCMService(this).execute();
        //changeFragment(new ChatFragment(this));

        //new LoadProfileService(MainActivity.this).execute(1L);


    }

    @Override
    protected void onResume() {
        super.onResume();

        //verifica se uma notificação chamou o app
        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        /*if(params!=null)
            switch (intent.getExtras().getInt(AppVariables.EXTRA_NOTIFICATION)){

                case AppVariables.OPEN_FRAGMENT_CHAT:
                    break;

                default:
                    break;

        }*/
    }

    /**
     * @autor Jeferson
     * @param fragment fragment que sera exibida
     * @ticket 0.1
     */
    public void changeFragment(Fragment fragment){
        super.onPostResume();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.mFragment = fragment;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();
        switch (id){
            case action_settings:
                break;

            case action_cancel_help:
                endHelp();
                break;
        }

        return super.onOptionsItemSelected(item);*/
        int id = item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void endHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
       // builder.setTitle("Titulo");
        builder.setMessage(getBaseContext().getResources().getString(R.string.FINALIZE_DIALOG));

        builder.setPositiveButton(getBaseContext().getResources().getString(R.string.YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                CancelHelpService cancelHelpService = new CancelHelpService(getBaseContext());
                cancelHelpService.execute();
            }
        });

        builder.setNegativeButton(getBaseContext().getResources().getString(R.string.YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int arg1) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void executeActionDrawer() {
        drawerLayout.closeDrawer(drawerListView);
    }
}

