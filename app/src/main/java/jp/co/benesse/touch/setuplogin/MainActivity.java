package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.System;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        System.putInt(getContentResolver(), "dcha_state", 3);
        System.putInt(getContentResolver(), "hide_navigation_bar", 0);
        System.putInt(getContentResolver(), "allow_screen_shot", 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        }, 2000);
    }
}
