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
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
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
    final static String PREPOSITIONS[] = {" A "," E "," O "," DA "," DE "," DI "," DO "," EM "," NA "};

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

            mStringResult = new ArrayList<String>();
            for(String s : PREPOSITIONS)
                auxi.replaceAll(s, " ");

            String[] values = auxi.trim().split(" ");


            User user = new SharedPreferencesUtils().getUSer(getActivity());
            HelpWrapper helpWrapper = new HelpWrapper();
            helpWrapper.user = user;
            helpWrapper.searchTerms = values;
            helpWrapper.text = "O usuario " + user.getUserName() + ", precisa de ajuda.";

            RequestHelpService requestHelpService = new RequestHelpService(getActivity());
            requestHelpService.execute(helpWrapper);


        }else{
            tags.setError(getResources().getString(R.string.required_field).toString());
            tags.setFocusable(true);
        }
    }


}
