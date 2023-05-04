package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.pm.PackageManager.*;
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
        // DchaStateが３に成った形跡が有る場合
        if (COUNT_DCHA_COMPLETED_FILE.exists()) {
            // DchaStateを３に変更
            setDchaState(3);
            msg = "DchaState を 3 に設定しました";
        } else {
            // 必要無い場合はアンインストールを要求
            startActivity(new Intent(Intent.ACTION_DELETE).setData(Uri.parse("package:jp.co.benesse.touch.setuplogin")));
            msg = "このアプリをアンインストールしてください";
            // LAUNCHER を無効化
            getPackageManager().setComponentEnabledSetting(new ComponentName(this, jp.co.benesse.touch.setuplogin.DchaStateChanger.class), COMPONENT_ENABLED_STATE_DISABLED, DONT_KILL_APP);
        }
        // ナビゲーションバーを表示
        putInt(getContentResolver(), "hide_navigation_bar", 0);
        // 文字表示
        textView.setText(msg);
        setContentView(linearLayout);
    }
}
