package jp.co.benesse.touch.setuplogin;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver {
    /* 有効時にパスワードを削除したい
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        ((DevicePolicyManager) context.getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE)).resetPassword("", 0);
    }
    */
    /* 無効時にパッケージをアンインストールしたい
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        context.startActivity(new Intent(Intent.ACTION_DELETE).setData(Uri.parse("package:" + context.getPackageName())));
    }
    */
}