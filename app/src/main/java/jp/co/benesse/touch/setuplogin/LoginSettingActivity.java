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
        // DchaStateが３に成った形跡が有る場合
        if (new File("/factory/count_dcha_completed").exists()) {
            // DchaStateを３に変更
            putInt(getContentResolver(), "dcha_state", 3);
        } else {
            // 必要無い場合はアンインストールを要求
            startActivity(new Intent("android.intent.action.DELETE").setData(parse("package:jp.co.benesse.touch.setuplogin")));
        }
        // ナビゲーションバーを表示
        putInt(getContentResolver(), "hide_navigation_bar", 0);
        // 文字表示
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(17);
        TextView textView = new TextView(this);
        textView.setText("ホームボタンを押して､ 設定アプリから開発者向けオプションを有効にして下さい｡");
        linearLayout.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        setContentView(linearLayout);
    }
}
