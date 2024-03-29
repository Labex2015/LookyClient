package labex.feevale.br.looky;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import labex.feevale.br.looky.model.ChatResponse;
import labex.feevale.br.looky.model.RequestHelp;
import labex.feevale.br.looky.model.ResponseHelp;
import labex.feevale.br.looky.service.GetPositionService;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.adapter.ItemAdapter;
import labex.feevale.br.looky.view.dialogs.CloseAppAction;
import labex.feevale.br.looky.view.dialogs.DialogMaker;
import labex.feevale.br.looky.view.dialogs.RequestHelpDialogActions;
import labex.feevale.br.looky.view.dialogs.ResponseRequestDialogAction;
import labex.feevale.br.looky.view.fragment.ChatFragment;
import labex.feevale.br.looky.view.fragment.LoginFragment;
import labex.feevale.br.looky.view.fragment.MainFragment;
import labex.feevale.br.looky.view.fragment.RegisterFragment;

import static labex.feevale.br.looky.R.drawable.icon_action_bar;


public class MainActivity extends FragmentActivity {

    private Fragment mFragment;
    private  Intent mService;
    private CharSequence mTitle;
    private Fragment     fragment;

    //protected DrawerLayout drawerLayout;
    //protected ActionBarDrawerToggle actionBarDrawerToggle;
    //protected ListView drawerListView;
    protected List<ItemAdapter> itemsAdapter;

    public static int pass = 0;
    public MainActivity() {
        itemsAdapter = Arrays.asList(ItemAdapter.HELPERS, ItemAdapter.KNOWLEDGES, ItemAdapter.PROFILE,
                ItemAdapter.REQUESTS);
    }

    public MainActivity(Fragment fragment) {
        this();
        this.fragment = fragment;
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);


        //TODO descomentar quando for usar Deawer
        /**
        drawerListView = (ListView) findViewById(navigation_drawer);
        drawerListView.setAdapter(new DrawerAdapter(this, itemsAdapter, (DrawerHandler) this));
        drawerLayout = (DrawerLayout) findViewById(drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        **/


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(icon_action_bar);

        //ativa serviço
        mService = new Intent(this, GetPositionService.class);
        startService(mService);

        //Login
        if(new SharedPreferencesUtils().getUSer(this) == null) {
            changeFragment(new LoginFragment(this));
        }else {

            Intent intent = getIntent();
            Bundle params = intent.getBundleExtra("TYPE_FRAG");
            if (params != null) {

                if (params.containsKey("CHAT")) {
                    ChatResponse chatResponse = (ChatResponse) params.getSerializable("CHAT");
                    changeFragment(new ChatFragment(this, chatResponse));
                } else if (params.containsKey("REQUEST")) {
                    RequestHelp requestHelp = (RequestHelp) params.getSerializable("REQUEST");
                    if (requestHelp != null) {
                        RequestHelpDialogActions actions = new RequestHelpDialogActions(requestHelp.getUser(), this);
                        new DialogMaker("Solicitação de ajuda", requestHelp.getText(), actions).createDialog(this).show();
                    }
                } else if (params.containsKey("RESPONSE")) {
                    ResponseHelp responseHelp = (ResponseHelp) params.getSerializable("RESPONSE");
                    if (responseHelp != null) {
                        if(responseHelp.status) {
                            ResponseRequestDialogAction action = new ResponseRequestDialogAction(this,
                                    new SharedPreferencesUtils().getUSer(this).getId(), responseHelp.user.getId());
                            new DialogMaker("Resposta...", responseHelp.message, action).createDialog(this).show();
                        }else{
                            Toast.makeText(this, responseHelp.message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            } else {
                loadMainFragment();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stopService(mService);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * @autor Jeferson
     * @param fragment fragment que sera exibida
     * @ticket 0.1
     */
    public void changeFragment(Fragment fragment){
        super.onPostResume();
        if(!(fragment instanceof ChatFragment))
            new SharedPreferencesUtils().saveChatStatus(false, this);

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.mFragment = fragment;
        if(mFragment instanceof LoginFragment || mFragment instanceof RegisterFragment)
            getActionBar().hide();
        else
            getActionBar().show();

        this.invalidateOptionsMenu();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO descomentar quando for usar Deawer
        /**
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
         **/


        switch (item.getItemId()){
            case android.R.id.home:
                changeFragment(new MainFragment());
                break;
            case R.id.action_logout:
                logout();
                break;

            case R.id.action_chat_logout:
                if(mFragment instanceof ChatFragment)
                    ((ChatFragment)mFragment).onExit();

                loadMainFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.LOGOUT)
                .setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
                        sharedPreferencesUtils.clear(getBaseContext());
                        finish();
                    }
                })
                .setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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
        if (mFragment instanceof ChatFragment) {
            getMenuInflater().inflate(R.menu.chat_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }
    public void loadMainFragment(){
        changeFragment(new MainFragment());
    }

    @Override
    public void onBackPressed() {
        if(new SharedPreferencesUtils().getUSer(this) == null ||
                new SharedPreferencesUtils().getUSer(this).getId() == 0) {
            changeFragment(new LoginFragment(this));
        }else{
            if(mFragment instanceof ChatFragment){

            }else if(mFragment instanceof MainFragment) {
                new DialogMaker("Sair do aplicativo?", "Você quer sair do aplicativo?",
                        new CloseAppAction(this)).createDialog(this).show();
            }else {
                super.onBackPressed();
            }
        }
    }

    //TODO descomentar quando for usar Deawer
    /**
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

    **/


    public Fragment getmFragment() {
        return mFragment;
    }
}

