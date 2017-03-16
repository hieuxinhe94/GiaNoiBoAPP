package intro;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import hieuxinhe.notificationappication.MainActivity;
import hieuxinhe.notificationappication.R;

public class IntroActivity extends AppIntro {
    SharedPreferences introPref = null;
    @Override protected void onCreate(@Nullable Bundle savedInstanceState)                               {
        super.onCreate(savedInstanceState);
        try {
            introPref= getSharedPreferences("Intro",0);
            SharedPreferences.Editor editor2 = introPref.edit();
            editor2.putBoolean("watchIntro", Boolean.TRUE);
            editor2.apply();
        }
        catch (Exception ignored)
        {}
        addSlide(AppIntroFragment.newInstance(" MUA ĐÚNG - MUA RẺ ", "CÔNG TY CỔ PHẦN CÔNG NGHỆ ETA", R.drawable.logo, Color.parseColor("#FF319EF9")));
        addSlide(AppIntroFragment.newInstance("Gianoibo.com", "NHANH CHÓNG - CẬP NHẬT - TIỆN LỢI ", R.drawable.favicon, Color.parseColor("#FF319EF9")));
        setBarColor(Color.parseColor("#FF319EF9"));
        setSeparatorColor(Color.parseColor("#2196F3"));
        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
        //setVibrate(true);+
        setVibrateIntensity(300);
    }
    @Override public void onSkipPressed(Fragment currentFragment)                                        {
        super.onSkipPressed(currentFragment);
        try {
            introPref= getSharedPreferences("Intro",0);
            SharedPreferences.Editor editor2 = introPref.edit();
            editor2.putBoolean("watchIntro", Boolean.TRUE);
            editor2.commit();
        }
        catch (Exception e)
        {}
        // Do something when users tap on Skip button.
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
    }
    @Override public void onDonePressed(Fragment currentFragment)                                        {
        super.onDonePressed(currentFragment);
        try {
            introPref= getSharedPreferences("Intro",0);
            SharedPreferences.Editor editor2 = introPref.edit();
            editor2.putBoolean("watchIntro", Boolean.TRUE);
            editor2.commit();
        }
        catch (Exception e)
        {
        }
        // Do something when users tap on Done button.
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
    }
    @Override public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}