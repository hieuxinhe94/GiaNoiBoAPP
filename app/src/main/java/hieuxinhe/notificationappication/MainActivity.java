package hieuxinhe.notificationappication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import connectWebservice.DisplayService;
import connectWebservice.TVSFunction;
import intro.IntroActivity;
import login.LoginActivity;

public class MainActivity extends Activity {
    SharedPreferences introPref = null;

    @Override protected void onCreate(Bundle savedInstanceState)           {
        super.onCreate(savedInstanceState);
        TVSFunction func = new TVSFunction();
        String s = func.MD5Cytography("123");
        Log.e("------------- > MD5",s);
       if (!isWatchIntro())
       {
            startActivity(new Intent(MainActivity.this, IntroActivity.class));
            return;
       }
       if (!isLogin())
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return;
        }
        // Alarm service
        Intent intent = new Intent(MainActivity.this, DisplayService.class);
        intent.putExtra("userName", getUsers());
        intent.putExtra("passWord", getPasswords());
        PendingIntent pintent = PendingIntent.getService(MainActivity.this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 30 * 1000, pintent);
        // repeat service with 30s .
        Toast.makeText(this,"Đăng nhập thành công  ... ",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this,MessageActivity.class));
        finish();
    }
    public  boolean isLogin()                                              {
        SharedPreferences sp = getSharedPreferences("LogIn",0);
        String username_pref = sp.getString("username",null);
        String password_pref = sp.getString("password",null);
        return username_pref != null && password_pref != null;
    }
    public  boolean isWatchIntro()                                         {
        introPref = getSharedPreferences("Intro",0);
        return introPref.getBoolean("watchIntro",false);
    }
    @Override protected void onResume()                                    {
        SharedPreferences.Editor editor = introPref.edit();
        editor.putBoolean("watchIntro",Boolean.TRUE);
        editor.apply();
        super.onResume();
    }
    @Override protected void onStop()                                      {
        super.onStop();
    }
    @Override protected void onDestroy()                                   {
        super.onDestroy();
    }
    public String getUsers()                                               {
        SharedPreferences introPref = getSharedPreferences("LogIn", 0);

        return introPref.getString("username", null);
    }
    public String getPasswords()                                           {
        SharedPreferences introPref = getSharedPreferences("LogIn", 0);
        return introPref.getString("password", null);
    }


}

