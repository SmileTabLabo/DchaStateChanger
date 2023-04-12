package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import static android.net.Uri.parse;
import static android.provider.Settings.System.putInt;

public class LoginSettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 文字表示のセットアップ
        String msg;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(17);
        TextView textView = new TextView(this);
        linearLayout.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        // DchaStateが３に成った形跡が有る場合
        if (new File("/factory/count_dcha_completed").exists()) {
            // DchaStateを３に変更
            putInt(getContentResolver(), "dcha_state", 3);
            msg = "DchaState を 3 に設定しました";
        } else {
            // 必要無い場合はアンインストールを要求
            startActivity(new Intent(Intent.ACTION_DELETE).setData(parse("package:jp.co.benesse.touch.setuplogin")));
            msg = "このアプリをアンインストールしてください";
        }
        // ナビゲーションバーを表示
        putInt(getContentResolver(), "hide_navigation_bar", 0);
        // 文字表示
        textView.setText(msg);
        setContentView(linearLayout);
    }
}
