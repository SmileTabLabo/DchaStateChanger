package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
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
            getPackageManager().setComponentEnabledSetting(new ComponentName(this, DchaStateChanger.class), 1, 1);
            msg = "DchaState を 3 に設定しました";
            putInt(getContentResolver(), "hide_navigation_bar", 0);
        } else {
            startActivity(new Intent("android.intent.action.DELETE").setData(Uri.parse("package:" + getPackageName())));
            msg = "このアプリをアンインストールしてください";
        }
        textView.setText(msg);
        setContentView(linearLayout);
    }
}
