package connectWebservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import hieuxinhe.notificationappication.R;
import object.Message;

public class DisplayService extends Service {
             int NOTIFICATION_ID = 1;
    public   String userName = null;
    public   String passWord = null;
    @Nullable
    @Override  public IBinder onBind(Intent intent) {
        Toast.makeText(this, "SERVICE IS RUN", Toast.LENGTH_LONG).show();
        return null;
    }
    @Override public int onStartCommand(Intent intent, int flags, int startId)                 {
        // Let it continue running until it is stopped.
        Log.i("Service"," calling service");
        SharedPreferences introPref = getSharedPreferences("LogIn",0);
        userName = introPref.getString("username",null);
        Log.i("Pref "," "+ userName);
        if (userName == null)
        {
            passWord = intent.getStringExtra("passWord");
            userName = intent.getStringExtra("userName");
            Log.i("Extra  "," UserName : "+userName + "pass : " + passWord);
        }
        Message msg = null  ;
        GetMessage message = new GetMessage();
        try {
            if (userName != null) msg= message.execute(userName).get();
        }
        catch (Exception e)
        {
            msg = null;
        }
        TVSFunction func = new TVSFunction();
        if (msg != null) {
            NOTIFICATION_ID ++;
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("BẠN CÓ TIN NHẮN MỚI : ")
                            .setContentText(msg.getSMS());
            builder.setAutoCancel(true);
            builder.setTicker("Gianoibo.com: ");
            builder.setNumber(NOTIFICATION_ID);
            Intent targetIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://gianoibo.com/TinNhan.aspx?version="            // #msg
                    +"android"+"&username="+userName
                    +"&password="+func.MD5Cytography(passWord)+""));
            targetIntent.putExtra("userName",userName);
            targetIntent.putExtra("passWord",passWord);
           targetIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT); //FLAG_UPDATE_CURRENT
            builder.setContentIntent(contentIntent);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(NOTIFICATION_ID, builder.build());
           }
        return START_STICKY;
    }
    @Override public void onDestroy()                                                          {
        Toast.makeText(this, "Gianoibo Destroyed", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}