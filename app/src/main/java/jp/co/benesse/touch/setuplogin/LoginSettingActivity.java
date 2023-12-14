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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import static android.app.admin.DevicePolicyManager.*;
import static android.content.pm.PackageManager.*;
import static android.util.TypedValue.*;
import static android.view.Gravity.*;
import static android.widget.LinearLayout.LayoutParams.*;

import jp.co.benesse.dcha.dchaservice.IDchaService;
import static android.os.BenesseExtension.*;

public class LoginSettingActivity extends Activity {

    IDchaService mDchaService;

    public void onBackPressed() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String msg;
        float fontSize = 50.0F;

        putInt("bc_password_hit", 1);
        if (COUNT_DCHA_COMPLETED_FILE.exists()) {
            getPackageManager().setComponentEnabledSetting(new ComponentName(this, Dev.class), COMPONENT_ENABLED_STATE_ENABLED, DONT_KILL_APP);
            bindService(new Intent("jp.co.benesse.dcha.dchaservice.DchaService").setPackage("jp.co.benesse.dcha.dchaservice"), new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    mDchaService = IDchaService.Stub.asInterface(iBinder);
                    try {
                        mDchaService.setSetupStatus(3);
                        mDchaService.hideNavigationBar(false);
                    } catch (RemoteException ignored) {
                    }
                    unbindService(this);
                }
                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    unbindService(this);
                }
            }, BIND_AUTO_CREATE);

            msg = "DchaState を 3 に設定しました";
            startActivity(new Intent(ACTION_ADD_DEVICE_ADMIN).putExtra(EXTRA_DEVICE_ADMIN, new ComponentName(this, DeviceAdminReceiver.class)).putExtra(EXTRA_ADD_EXPLANATION,
                "｢端末管理アプリ｣を有効にする事で､ DchaService による自動アンインストールをブロックします｡\n\nNova Launcher等のサードパーティ製のランチャーをインストールしていない場合は､\n設定アプリ→｢アプリと通知｣→｢DchaState Changer｣ (無い場合は｢〇〇個のアプリを全て表示｣から選択)→｢詳細設定｣→｢アプリ内のその他の設定｣から DchaState を変更できます｡"
            ));
        } else {
            startActivity(new Intent(Intent.ACTION_DELETE).setData(Uri.parse("package:" + getPackageName())));
            msg = "このアプリをアンインストールしてください";
        }

        LinearLayout linearLayout = new LinearLayout(this);
        TextView textView = new TextView(this);
        linearLayout.setGravity(CENTER);
        textView.setTextSize(COMPLEX_UNIT_SP, fontSize);
        textView.setText(msg);
        linearLayout.addView(textView, new LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        setContentView(linearLayout);
    }
}
