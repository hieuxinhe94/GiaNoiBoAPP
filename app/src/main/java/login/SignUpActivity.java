package login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import butterknife.ButterKnife;
import butterknife.InjectView;
import connectWebservice.CheckServer;
import hieuxinhe.notificationappication.R;
import object.Message;

public class SignUpActivity extends AppCompatActivity {
    CheckServer  util =  new CheckServer();
    private static final String TAG = "SignupActivity";
    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override public void onCreate(Bundle savedInstanceState)                                  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void signup()                                                                       {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_PopupOverlay);  //AppTheme_Dark_Dialog
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

       if (util.registerLogin(name,password)!=1) {
           Log.i("Register"," Register FALSE");
           return;
       }
        else {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }}
    public void onSignupSuccess()                                                              {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
        finish();

    }
    public void onSignupFailed()                                                               {
        Toast.makeText(getBaseContext(), "Đăng ký thất bại , Vui lòng thử lại .", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public boolean validate()                                                                  {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Định dạng không hợp lệ");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Mật khẩu từ 3 - 10 ký tự");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
