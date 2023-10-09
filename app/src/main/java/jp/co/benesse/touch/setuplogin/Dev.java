package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static android.os.BenesseExtension.setDchaState;
import static android.provider.Settings.*;
import static android.provider.Settings.Global.*;

public class Dev extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDchaState(3);
        finishAndRemoveTask();
        startActivity(new Intent(ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
        try {
            if ((getInt(getContentResolver(), ADB_ENABLED) == 1) && (getInt(getContentResolver(), DEVELOPMENT_SETTINGS_ENABLED) == 1)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDchaState(0);
                    }
                }, 1000);
            }
        } catch (SettingNotFoundException ignored) {
        }
    }
}
