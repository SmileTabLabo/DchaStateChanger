package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.os.Bundle;

import java.io.File;

import static android.provider.Settings.System.putInt;

public class LoginSettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DchaStateが３に成った形跡が有る場合
        if (new File("/factory/count_dcha_completed").exists()) {
            // DchaStateを３に変更
            putInt(getContentResolver(), "dcha_state", 3);
        }
        // ナビゲーションバーを表示
        putInt(getContentResolver(), "hide_navigation_bar", 0);
        // 文字表示
        setContentView(R.layout.generic);
    }
}
