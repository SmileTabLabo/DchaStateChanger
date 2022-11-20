package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.os.Bundle;
import static android.provider.Settings.System.putInt;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 文字表示
        setContentView(R.layout.main);
        // DchaStateを 3 に変更
        putInt(getContentResolver(), "dcha_state", 3);
        // ナビゲーションバーを表示
        putInt(getContentResolver(), "hide_navigation_bar", 0);
    }
}
