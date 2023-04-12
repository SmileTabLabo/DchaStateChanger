package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.provider.Settings.SettingNotFoundException;
import static android.provider.Settings.System.getInt;
import static android.provider.Settings.System.putInt;

public class DchaStateChangeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(17);
        TextView textView = new TextView(this);
        linearLayout.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        try {
            // DchaState が 3 の時に 0 に設定する
            if (getInt(getContentResolver(), "dcha_state") == 3) {
                putInt(getContentResolver(), "dcha_state", 0);
                textView.setText("DchaState を 0 に設定しました");
            } else {
                // 0 の時は 3 に設定する
                finishAndRemoveTask();
                startActivity((new Intent()).setClassName("jp.co.benesse.touch.setuplogin", "jp.co.benesse.touch.setuplogin.LoginSettingActivity"));
            }
            setContentView(textView);
        } catch (SettingNotFoundException e) {
            // 未定義の場合の処理は不要
            throw new RuntimeException(e);
        }
    }
}
