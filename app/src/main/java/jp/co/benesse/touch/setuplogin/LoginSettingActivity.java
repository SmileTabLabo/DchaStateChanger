package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.app.admin.DevicePolicyManager.*;
import static android.content.pm.PackageManager.*;
import static android.provider.Settings.System.*;

import static android.os.BenesseExtension.*;

public class LoginSettingActivity extends Activity {
    String msg;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(17);
        TextView textView = new TextView(this);
        linearLayout.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        textView.setTextSize(2, 50.0f);
        if (COUNT_DCHA_COMPLETED_FILE.exists() && canWrite(this)) {
            setDchaState(3);
            getPackageManager().setComponentEnabledSetting(new ComponentName(this, DchaStateChanger.class), COMPONENT_ENABLED_STATE_ENABLED, DONT_KILL_APP);
            msg = "DchaState を 3 に設定しました";
            putInt(getContentResolver(), "hide_navigation_bar", 0);
            startActivity(new Intent(ACTION_ADD_DEVICE_ADMIN).putExtra(EXTRA_DEVICE_ADMIN, new ComponentName(this, DeviceAdminReceiver.class)).putExtra(EXTRA_ADD_EXPLANATION, "｢端末管理アプリ｣を有効にする事で､ DchaService による自動アンインストールをブロックします｡\n\nNova Launcher等のサードパーティ製のランチャーをインストールしていない場合は､\n設定アプリ→｢アプリと通知｣→｢DchaState Changer｣ (無い場合は｢〇〇個のアプリを全て表示｣から選択)→｢詳細設定｣→｢アプリ内のその他の設定｣から DchaState を変更できます｡"));
        } else {
            startActivity(new Intent(Intent.ACTION_DELETE).setData(Uri.parse("package:" + getPackageName())));
            msg = "このアプリをアンインストールしてください";
        }
        textView.setText(msg);
        setContentView(linearLayout);
    }
}
