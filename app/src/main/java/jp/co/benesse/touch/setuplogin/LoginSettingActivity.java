package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BenesseExtension;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.LinearLayout;
import android.widget.TextView;
import jp.co.benesse.dcha.dchaservice.IDchaService;

public class LoginSettingActivity extends Activity {
    IDchaService mDchaService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 文字表示のセットアップ
        String msg;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(17);
        TextView textView = new TextView(this);
        linearLayout.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        PackageManager packageManager = getPackageManager();
        // DchaStateが３に成った形跡が有る場合
        if (BenesseExtension.COUNT_DCHA_COMPLETED_FILE.exists()) {
            // DchaStateを３に変更
            BenesseExtension.setDchaState(3);
            msg = "DchaState を 3 に設定しました";
        } else {
            // 必要無い場合はアンインストールを要求
            startActivity(new Intent(Intent.ACTION_DELETE).setData(Uri.parse("package:jp.co.benesse.touch.setuplogin")));
            msg = "このアプリをアンインストールしてください";
            // LAUNCHER を無効化
            packageManager.setComponentEnabledSetting(new ComponentName(this, jp.co.benesse.touch.setuplogin.DchaStateChanger.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
        // ナビゲーションバーを表示
        try {
            mDchaService.hideNavigationBar(false);
        } catch (RemoteException ignored) {}
        // 文字表示
        textView.setText(msg);
        setContentView(linearLayout);
    }
}
