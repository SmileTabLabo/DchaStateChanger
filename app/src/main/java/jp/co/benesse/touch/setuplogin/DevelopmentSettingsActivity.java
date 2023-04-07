package jp.co.benesse.touch.setuplogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static android.net.Uri.parse;
import static android.provider.Settings.Global.getInt;
import static android.provider.Settings.SettingNotFoundException;
import static android.provider.Settings.System.canWrite;
import static android.provider.Settings.System.putInt;

public class DevelopmentSettingsActivity extends Activity {
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishAndRemoveTask();
        if (!canWrite(getApplicationContext())) {
            startActivity(new Intent("android.settings.action.MANAGE_WRITE_SETTINGS", parse("package:jp.co.benesse.touch.setuplogin")).setFlags(268435456));
        } else {
            putInt(getContentResolver(), "dcha_state", 3);
            startActivity(new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS"));
            try {
                if ((getInt(getContentResolver(), "development_settings_enabled") == 1) && (getInt(getContentResolver(), "adb_enabled") == 1)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            putInt(getContentResolver(), "dcha_state", 0);
                        }
                    }, 1000);
                }
            } catch (SettingNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
