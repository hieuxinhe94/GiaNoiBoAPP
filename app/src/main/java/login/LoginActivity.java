package login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import connectWebservice.CheckServer;

import hieuxinhe.notificationappication.MainActivity;
import hieuxinhe.notificationappication.R;

public class LoginActivity extends AppCompatActivity {

    public ProgressDialog progressDialog ;
    public   static boolean errored = false;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private TextView statusTV;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;
    private GoogleApiClient client;
    //  method to check
    String editTextUsername;
    boolean loginStatus;
    String editTextPassword;
    //
    @Override
    public void onCreate(Bundle savedInstanceState)                                            {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        getControls();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isOnline()){
                    statusTV.setText("Vui lòng kiểm tra lại kết nối Internet .");
                    Toast.makeText(LoginActivity.this,"Không có kết nối ... ",Toast.LENGTH_LONG).show();
                    return;
                }
                login();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext()," Chức năng chưa được cung cấp , Vui lòng quay lại sau ",Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);  //SignUpActivity.class
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public void login()                                                                        {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_PopupOverlay);  // AppTheme_Dark_Dialog
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Tiến hành xác thực ...");
         editTextUsername = _emailText.getText().toString();
         editTextPassword = _passwordText.getText().toString();
        // TODO: Implement your own authentication logic here.
        AsyncCallNetService sync = new AsyncCallNetService();
        sync.execute();
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 5000);
    }
    public void getControls()                  {
        statusTV = (TextView)findViewById(R.id.tv_result);
    }
    public void saveLogIn(String username,String password)                                     {
        SharedPreferences sp = getSharedPreferences("LogIn",0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("username",username);
        ed.putString("password",password);
        ed.commit();
    }
    private class AsyncCallNetService extends AsyncTask                                        {
        @Override protected void onPostExecute(Object o)           {
            //   Make Progress Bar invisible
            progressDialog.dismiss();
            Intent intObj = new Intent(LoginActivity.this, MainActivity.class);
            if (!errored) {
                if (loginStatus) {
                    saveLogIn(editTextUsername,editTextPassword);
                    startActivity(intObj);
                    return;
                }
                else {
                    Toast.makeText(LoginActivity.this,"Login Failed, try again",Toast.LENGTH_LONG).show();
                    statusTV.setText("Mật khẩu hoặc Tài khoản không đúng , Xin thử lại");
                    return;
                }
            } else {
                Toast.makeText(LoginActivity.this,"Error occured in invoking webservice",Toast.LENGTH_LONG).show();
                 statusTV.setText("Không thể kết nối tới máy chủ");
            }
            errored = false;
        }
        @Override protected Object doInBackground(Object[] params) {
            CheckServer checkLogin = new CheckServer();
            loginStatus =   checkLogin.checkLogin(editTextUsername,editTextPassword);
            return null;
        }
        @Override protected void onPreExecute()                    {
            progressDialog.show();
        }
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)    {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // start mainActivity with pref manager in here

                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }
    @Override public void onBackPressed()                                                      {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
    public void onLoginSuccess()                                                               {
        _loginButton.setEnabled(true);
        //   finish();
    }
    public void onLoginFailed()                                                                {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }
    public boolean validate()                                                                  {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("Tên đăng nhập không được để trống .");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3 || password.length() > 10) {
            _passwordText.setError("Mật khẩu phải có từ 3 ký tự trỡ lên .");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
    public Action getIndexApiAction()                                                          {
        Thing object = new Thing.Builder()
                .setName("Login Page")

                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))   // <----------------------------- URL
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    @Override public void onStart()                                                            {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    @Override public void onStop()                                                             {
        super.onStop();
        // Clear state data
        SharedPreferences sp = getSharedPreferences("LogIn",0);
        sp.edit().clear().commit();

        // ATTENTION: This was auto-generated to implement the App Indexing API// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    public boolean isOnline()                                                                  {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
