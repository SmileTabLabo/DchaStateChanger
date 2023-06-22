package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.co.benesse.dcha.dchaservice.IDchaService;
import static android.os.BenesseExtension.*;

public class LoginSettingActivity extends Activity {
    IDchaService mDchaService;
    public void onCreate(Bundle savedInstanceState) {
        String msg;
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(17);
        TextView textView = new TextView(this);
        linearLayout.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        textView.setTextSize(2, 50.0f);
        if (COUNT_DCHA_COMPLETED_FILE.exists()) {
            setDchaState(3);
            getPackageManager().setComponentEnabledSetting(new ComponentName(this, DchaStateChanger.class), 1, 1);
            msg = "DchaState を 3 に設定しました";
        } else {
            startActivity(new Intent("android.intent.action.DELETE").setData(Uri.parse("package:" + getPackageName())));
            msg = "このアプリをアンインストールしてください";
        }
        bindService(new Intent("jp.co.benesse.dcha.dchaservice.DchaService").setPackage("jp.co.benesse.dcha.dchaservice"), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mDchaService = IDchaService.Stub.asInterface(iBinder);
                try {
                    mDchaService.hideNavigationBar(false);
                } catch (RemoteException ignored) {
                }
                unbindService(this);
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                unbindService(this);
            }
        }, 1);
        textView.setText(msg);
        setContentView(linearLayout);
    }
}
