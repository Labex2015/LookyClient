package labex.feevale.br.looky.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.service.LoginService;

/**
 * Created by Jeferson on 30/12/2014
 */
public class LoginFragment extends Fragment {

    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    TextView textRegister;

    private Context context;


    public LoginFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        edtEmail    = (EditText) view.findViewById(R.id.edt_email);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        textRegister = (TextView) view.findViewById(R.id.text_register);

        textRegister.setOnClickListener(toRegister());
        btnLogin.setOnClickListener(login());
        return view;
    }

    //Cadastro
    private View.OnClickListener toRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ir para tela de cadastro
            }
        };
    }

    //Login
    private View.OnClickListener login() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                boolean error = false;

                if(email==null || email.isEmpty()){
                    edtEmail.setError(context.getResources().getString(R.string.required_field));
                    error = true;
                }

                if(password==null || password.isEmpty()){
                    edtPassword.setError(context.getResources().getString(R.string.required_field));
                    error = true;
                }

                if(!error){
                   //TODO logar
                    LoginService loginService = new LoginService(getActivity());
                    loginService.execute();
                }
            }
        };
    }


}
