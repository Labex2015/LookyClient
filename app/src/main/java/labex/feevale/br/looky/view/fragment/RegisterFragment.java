package labex.feevale.br.looky.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.service.GCMService;
import labex.feevale.br.looky.wrapper.RegisterLogin;

/**
 * Created by Jeferson on 30/12/2014.
 */
public class RegisterFragment extends Fragment {
    EditText edtName;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtPassword2;
    Button btnNext;
    Context context;

    public RegisterFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        edtName = (EditText) view.findViewById(R.id.edt_name);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        edtPassword2 = (EditText) view.findViewById(R.id.edt_password2);
        btnNext = (Button) view.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(register());
        return view;
    }

    private View.OnClickListener register() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String password2 = edtPassword2.getText().toString();
                Boolean error = false;

                if( name == null || name.isEmpty() ){
                    edtName.setError(getResources().getString(R.string.required_field));
                    error = true;
                }

                if( email == null || email.isEmpty() ){
                    edtEmail.setError(getResources().getString(R.string.required_field));
                    error = true;
                }

                if( password == null || password.isEmpty() ){
                    edtPassword.setError(getResources().getString(R.string.required_field));
                    error = true;
                }

                if( password2 == null || password2.isEmpty() ){
                    edtPassword2.setError(getResources().getString(R.string.required_field));
                    error = true;
                }

                if(!password.equals(password2)){
                    edtPassword2.setText("");
                    edtPassword.setText("");
                    edtPassword2.setError(getResources().getString(R.string.field_not_match));
                    edtPassword.setError(getResources().getString(R.string.field_not_match));
                    error = true;
                }

                if(!error){
                    RegisterLogin registerLogin = new RegisterLogin();
                    registerLogin.setUsername(name);
                    registerLogin.setEmail(email);
                    registerLogin.setPassword(password);

                    new GCMService((Activity)context,registerLogin,GCMService.REGISTER).execute();
                }
            }
        };
    }
}
