package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.net.Uri.parse;
import static android.os.BenesseExtension.*;
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
        textView.setTextSize(2, 50);
        // DchaStateが３に成った形跡が有る場合
        if (COUNT_DCHA_COMPLETED_FILE.exists()) {
            // DchaStateを３に変更
            setDchaState(3);
            msg = "DchaState を 3 に設定しました";
            // アクティビティを有効化
            getPackageManager().setComponentEnabledSetting(new ComponentName(this, DchaStateChanger.class), 1, 1);
        } else {
            // 必要無い場合はアンインストールを要求
            startActivity(new Intent("android.intent.action.DELETE").setData(parse("package:jp.co.benesse.touch.setuplogin")));
            msg = "このアプリをアンインストールしてください";
        }
        // ナビゲーションバーを表示
        putInt(getContentResolver(), "hide_navigation_bar", 0);
        // 文字表示
        textView.setText(msg);
        setContentView(linearLayout);
    }
}
