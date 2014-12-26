package labex.feevale.br.looky.view.fragment;



import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.RequestHelpService;
import labex.feevale.br.looky.utils.PreferencesHelp;
import labex.feevale.br.looky.wrapper.HelpWrapper;


/**
 * Created by Jeferson on 09/12/2014.
 * ticket 1.3
 */
public class RequestHelpFragment extends Fragment{
    Context context;
    AutoCompleteTextView tags;
    Button btnRequestHelp;

    TextView labelPage;

    List<String> mAreaList;
    List<String> mStringResult;
    final static String AUXI_A = "A";
    final static String AUXI_E = "E";
    final static String AUXI_O = "O";
    final static String AUXI_DA = "DA";
    final static String AUXI_DE = "DE";
    final static String AUXI_DI = "DI";
    final static String AUXI_DO = "DO";
    final static String AUXI_EM = "EM";
    final static String AUXI_NA = "NA";

    public RequestHelpFragment(List<String> mAreaList) {
        this.mAreaList = mAreaList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_help, container, false);

        tags            = (AutoCompleteTextView) view.findViewById(R.id.tags_autoComplete);
        btnRequestHelp  = (Button) view.findViewById(R.id.requestHelp_button);
        labelPage       = (TextView) view.findViewById(R.id.request_help_labelPage);
        labelPage.setText(R.string.request_help_label_page);

        btnRequestHelp.setOnClickListener(requestHelp());

        //button done of keyboard
        tags.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    sendHelp();
                    return  true;
                }else {
                    return false;
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = new ArrayAdapter (getActivity(),android.R.layout.select_dialog_item, mAreaList);
        tags.setAdapter(adapter);
    }

    private View.OnClickListener requestHelp() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendHelp();
            }
        };
    }

    private void sendHelp() {
        if(!tags.getText().toString().isEmpty()){

            String auxi = tags.getText().toString().trim().toUpperCase();
            String auxi2;
            int position = auxi.indexOf(" ");
            mStringResult = new ArrayList<String>();

            if(position != -1){
                //enquanto tiver espaço
                while(position != -1){
                    auxi2    = auxi.substring(0, position);

                    if(!auxi2.equals(AUXI_A) && !auxi2.equals(AUXI_E) && !auxi2.equals(AUXI_O) && !auxi2.equals(AUXI_DA) &&
                            !auxi2.equals(AUXI_DE) && !auxi2.equals(AUXI_DO) && !auxi2.equals(AUXI_DI) && !auxi2.equals(AUXI_EM) && !auxi2.equals(AUXI_NA))
                        mStringResult.add(auxi2);


                    auxi = auxi.substring(position + 1);

                    position = auxi.indexOf(" ");

                    if(position == -1)
                        mStringResult.add(auxi);
                }
            }

            //TODO enviar para o webservice
            PreferencesHelp preferencesHelp = new PreferencesHelp(context);
            User user = preferencesHelp.getUserToPreferences();
            HelpWrapper helpWrapper = new HelpWrapper();
            helpWrapper.user = user;
            helpWrapper.searchTerms = (String[])mStringResult.toArray();
            helpWrapper.text = "O usuário " + user.getUserName() + ", precisa de ajuda.";

            RequestHelpService requestHelpService = new RequestHelpService(context);
            requestHelpService.execute(helpWrapper);


        }else{
            tags.setError(getResources().getString(R.string.required_field).toString());
            tags.setFocusable(true);
        }
    }


}
