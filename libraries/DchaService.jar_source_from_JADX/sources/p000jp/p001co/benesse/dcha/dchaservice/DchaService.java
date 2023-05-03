package p000jp.p001co.benesse.dcha.dchaservice;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityThread;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.app.Service;
import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.NetworkPolicyManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.Process;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.IWindowManager;
import com.android.internal.app.LocalePicker;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.LockPatternUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import p000jp.p001co.benesse.dcha.dchaservice.IDchaService;
import p000jp.p001co.benesse.dcha.dchaservice.util.Log;
import p000jp.p001co.benesse.dcha.util.Logger;

/* renamed from: jp.co.benesse.dcha.dchaservice.DchaService */
public class DchaService extends Service {
    /* access modifiers changed from: private */
    public static boolean doCancelDigichalizedFlg = false;
    private static Signature[] sSystemSignature;
    protected IDchaService.Stub mDchaServiceStub = new IDchaService.Stub(this) {
        final DchaService this$0;

        /* renamed from: jp.co.benesse.dcha.dchaservice.DchaService$1$PackageDeleteObserver */
        class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
            boolean finished;
            boolean result;
            final C00001 this$1;

            PackageDeleteObserver(C00001 r1) {
                this.this$1 = r1;
            }

            public void packageDeleted(String str, int i) {
                boolean z = true;
                Log.m0d("DchaService", "packageDeleted 0001");
                synchronized (this) {
                    this.finished = true;
                    if (i != 1) {
                        z = false;
                    }
                    this.result = z;
                    notifyAll();
                }
            }
        }

        /* renamed from: jp.co.benesse.dcha.dchaservice.DchaService$1$PackageInstallSessionCallback */
        class PackageInstallSessionCallback extends PackageInstaller.SessionCallback {
            boolean finished;
            boolean result;
            final C00001 this$1;

            PackageInstallSessionCallback(C00001 r1) {
                this.this$1 = r1;
            }

            public void onActiveChanged(int i, boolean z) {
                Log.m0d("DchaService", "PackageInstallSessionCallback#onActiveChanged 0001");
            }

            public void onBadgingChanged(int i) {
                Log.m0d("DchaService", "PackageInstallSessionCallback#onBadgingChanged 0001");
            }

            public void onCreated(int i) {
                Log.m0d("DchaService", "PackageInstallSessionCallback#onCreated 0001");
            }

            public void onFinished(int i, boolean z) {
                Log.m0d("DchaService", "PackageInstallSessionCallback#onFinished 0001");
                synchronized (this) {
                    this.finished = true;
                    this.result = z;
                    notifyAll();
                }
            }

            public void onProgressChanged(int i, float f) {
                Log.m0d("DchaService", "PackageInstallSessionCallback#onProgressChanged 0001");
            }
        }

        {
            this.this$0 = r1;
        }

        /* JADX WARNING: Removed duplicated region for block: B:22:0x0049 A[SYNTHETIC, Splitter:B:22:0x0049] */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x004e A[SYNTHETIC, Splitter:B:25:0x004e] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void installAppSession(android.content.pm.PackageInstaller.Session r9, java.lang.String r10) throws java.io.IOException {
            /*
                r8 = this;
                r6 = 0
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "installAppSession 0001"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                java.io.File r0 = new java.io.File
                r0.<init>(r10)
                boolean r1 = r0.isFile()
                if (r1 == 0) goto L_0x0052
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "installAppSession 0002"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                long r4 = r0.length()
            L_0x001e:
                java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0085, all -> 0x0081 }
                r7.<init>(r10)     // Catch:{ IOException -> 0x0085, all -> 0x0081 }
                java.lang.String r1 = "jp.co.benesse.dcha.dchaservice"
                r2 = 0
                r0 = r9
                java.io.OutputStream r1 = r0.openWrite(r1, r2, r4)     // Catch:{ IOException -> 0x007e, all -> 0x007a }
                r0 = 65536(0x10000, float:9.18355E-41)
                byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x003c, all -> 0x009b }
            L_0x0030:
                int r2 = r7.read(r0)     // Catch:{ IOException -> 0x003c, all -> 0x009b }
                r3 = -1
                if (r2 == r3) goto L_0x0055
                r3 = 0
                r1.write(r0, r3, r2)     // Catch:{ IOException -> 0x003c, all -> 0x009b }
                goto L_0x0030
            L_0x003c:
                r0 = move-exception
            L_0x003d:
                r2 = r7
            L_0x003e:
                java.lang.String r3 = "DchaService"
                java.lang.String r4 = "installAppSession 0003"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r3, r4, r0)     // Catch:{ all -> 0x0046 }
                throw r0     // Catch:{ all -> 0x0046 }
            L_0x0046:
                r0 = move-exception
            L_0x0047:
                if (r1 == 0) goto L_0x004c
                r1.close()     // Catch:{ IOException -> 0x0089 }
            L_0x004c:
                if (r2 == 0) goto L_0x0051
                r2.close()     // Catch:{ IOException -> 0x0092 }
            L_0x0051:
                throw r0
            L_0x0052:
                r4 = -1
                goto L_0x001e
            L_0x0055:
                r9.fsync(r1)     // Catch:{ IOException -> 0x003c, all -> 0x009b }
                if (r1 == 0) goto L_0x005d
                r1.close()     // Catch:{ IOException -> 0x0068 }
            L_0x005d:
                r7.close()     // Catch:{ IOException -> 0x0071 }
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "installAppSession 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                return
            L_0x0068:
                r0 = move-exception
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "installAppSession 0004"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r1, r2, r0)
                throw r0
            L_0x0071:
                r0 = move-exception
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "installAppSession 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r1, r2, r0)
                throw r0
            L_0x007a:
                r0 = move-exception
                r1 = r6
            L_0x007c:
                r2 = r7
                goto L_0x0047
            L_0x007e:
                r0 = move-exception
                r1 = r6
                goto L_0x003d
            L_0x0081:
                r0 = move-exception
                r1 = r6
                r2 = r6
                goto L_0x0047
            L_0x0085:
                r0 = move-exception
                r1 = r6
                r2 = r6
                goto L_0x003e
            L_0x0089:
                r0 = move-exception
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "installAppSession 0004"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r1, r2, r0)
                throw r0
            L_0x0092:
                r0 = move-exception
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "installAppSession 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r1, r2, r0)
                throw r0
            L_0x009b:
                r0 = move-exception
                goto L_0x007c
            */
            throw new UnsupportedOperationException("Method not decompiled: p000jp.p001co.benesse.dcha.dchaservice.DchaService.C00001.installAppSession(android.content.pm.PackageInstaller$Session, java.lang.String):void");
        }

        public void cancelSetup() throws RemoteException {
            Log.m0d("DchaService", "cancelSetup 0001");
            this.this$0.doCancelDigichalized();
        }

        public boolean checkPadRooted() throws RemoteException {
            Log.m0d("DchaService", "checkPadRooted 0001");
            return false;
        }

        public void clearDefaultPreferredApp(String str) throws RemoteException {
            Log.m0d("DchaService", "clearDefaultPreferredApp 0001 packageName:" + str);
            try {
                this.this$0.getPackageManager().clearPackagePreferredActivities(str);
            } catch (Exception e) {
                Log.m1e("DchaService", "clearDefaultPreferredApp 0002", e);
                throw new RemoteException(e.toString());
            }
        }

        /* access modifiers changed from: protected */
        public void close(Closeable closeable) {
            Log.m0d("DchaService", "close 0001");
            if (closeable == null) {
                Log.m0d("DchaService", "close 0002");
                return;
            }
            try {
                closeable.close();
            } catch (IOException e) {
                Log.m1e("DchaService", "close 0003", e);
            }
            Log.m0d("DchaService", "close 0004");
        }

        public boolean copyFile(String str, String str2) throws RemoteException {
            boolean z;
            Log.m0d("DchaService", "copyFile 0001 srcFilePath:" + str + " dstFilePath:" + str2);
            try {
                String canonicalExternalPath = getCanonicalExternalPath(System.getenv("SECONDARY_STORAGE"));
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                    Log.m0d("DchaService", "copyFile 0002");
                    throw new IllegalArgumentException();
                }
                String canonicalExternalPath2 = getCanonicalExternalPath(str);
                String canonicalExternalPath3 = getCanonicalExternalPath(str2);
                if (canonicalExternalPath2.startsWith(canonicalExternalPath) || canonicalExternalPath3.startsWith(canonicalExternalPath)) {
                    File file = new File(canonicalExternalPath2);
                    File file2 = new File(canonicalExternalPath3);
                    if (!file.isFile() || (!file2.isDirectory() && !file2.isFile() && (file2.exists() || !file2.getParentFile().isDirectory()))) {
                        Log.m0d("DchaService", "copyFile 0005");
                        throw new IllegalArgumentException();
                    }
                    Log.m0d("DchaService", "copyFile 0004");
                    z = fileCopy(file, file2);
                    Log.m0d("DchaService", "copyFile 0007 result:" + z);
                    return z;
                }
                Log.m0d("DchaService", "copyFile 0003");
                throw new SecurityException("The path is not a external storage.");
            } catch (Exception e) {
                Log.m1e("DchaService", "copyFile 0006", e);
                z = false;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:31:0x00ac  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00b8  */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x00d5  */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x00e1  */
        /* JADX WARNING: Removed duplicated region for block: B:61:0x00ff  */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x010b  */
        /* JADX WARNING: Removed duplicated region for block: B:76:0x012a  */
        /* JADX WARNING: Removed duplicated region for block: B:80:0x0136  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean copyUpdateImage(java.lang.String r9, java.lang.String r10) {
            /*
                r8 = this;
                r0 = 1
                r7 = 0
                r2 = 0
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r3 = "copyUpdateImage 0001 srcFile:"
                r1.append(r3)
                r1.append(r9)
                java.lang.String r3 = " dstFile:"
                r1.append(r3)
                r1.append(r10)
                java.lang.String r3 = "DchaService"
                java.lang.String r1 = r1.toString()
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r3, r1)
                boolean r1 = android.text.TextUtils.isEmpty(r9)
                if (r1 != 0) goto L_0x002d
                boolean r1 = android.text.TextUtils.isEmpty(r10)
                if (r1 == 0) goto L_0x0035
            L_0x002d:
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "copyUpdateImage 0009"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
            L_0x0034:
                return r7
            L_0x0035:
                java.lang.String r1 = "/cache"
                boolean r1 = r10.startsWith(r1)
                if (r1 != 0) goto L_0x0045
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "copyUpdateImage 0010"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                goto L_0x0034
            L_0x0045:
                java.io.File r1 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0151, IOException -> 0x014d, Exception -> 0x0148, all -> 0x0143 }
                r1.<init>(r9)     // Catch:{ FileNotFoundException -> 0x0151, IOException -> 0x014d, Exception -> 0x0148, all -> 0x0143 }
                java.io.File r3 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0151, IOException -> 0x014d, Exception -> 0x0148, all -> 0x0143 }
                r3.<init>(r10)     // Catch:{ FileNotFoundException -> 0x0151, IOException -> 0x014d, Exception -> 0x0148, all -> 0x0143 }
                java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0151, IOException -> 0x014d, Exception -> 0x0148, all -> 0x0143 }
                r4.<init>(r1)     // Catch:{ FileNotFoundException -> 0x0151, IOException -> 0x014d, Exception -> 0x0148, all -> 0x0143 }
                java.nio.channels.FileChannel r1 = r4.getChannel()     // Catch:{ FileNotFoundException -> 0x0151, IOException -> 0x014d, Exception -> 0x0148, all -> 0x0143 }
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0118, IOException -> 0x00ed, Exception -> 0x00c3, all -> 0x00a1 }
                r4.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0118, IOException -> 0x00ed, Exception -> 0x00c3, all -> 0x00a1 }
                java.nio.channels.FileChannel r6 = r4.getChannel()     // Catch:{ FileNotFoundException -> 0x0118, IOException -> 0x00ed, Exception -> 0x00c3, all -> 0x00a1 }
                r2 = 0
                long r4 = r1.size()     // Catch:{ FileNotFoundException -> 0x0169, IOException -> 0x016b, Exception -> 0x016d, all -> 0x0170 }
                r1.transferTo(r2, r4, r6)     // Catch:{ FileNotFoundException -> 0x0169, IOException -> 0x016b, Exception -> 0x016d, all -> 0x0170 }
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r2, r3)
                if (r1 == 0) goto L_0x007d
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r2, r3)
                r1.close()     // Catch:{ IOException -> 0x015a }
            L_0x007d:
                if (r6 == 0) goto L_0x017a
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r6.close()     // Catch:{ IOException -> 0x0173 }
            L_0x0089:
                r7 = r0
            L_0x008a:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "copyUpdateImage 0008 return:"
                r0.append(r1)
                r0.append(r7)
                java.lang.String r1 = "DchaService"
                java.lang.String r0 = r0.toString()
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r0)
                goto L_0x0034
            L_0x00a1:
                r0 = move-exception
                r6 = r2
            L_0x00a3:
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r2, r3)
                if (r1 == 0) goto L_0x00b6
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r2, r3)
                r1.close()     // Catch:{ IOException -> 0x0163 }
            L_0x00b6:
                if (r6 == 0) goto L_0x00c2
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r6.close()     // Catch:{ IOException -> 0x0166 }
            L_0x00c2:
                throw r0
            L_0x00c3:
                r0 = move-exception
                r6 = r2
            L_0x00c5:
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0004"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r2, r3, r0)     // Catch:{ all -> 0x0155 }
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)
                if (r1 == 0) goto L_0x00df
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)
                r1.close()     // Catch:{ IOException -> 0x015d }
            L_0x00df:
                if (r6 == 0) goto L_0x008a
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                r6.close()     // Catch:{ IOException -> 0x0176 }
                r0 = r7
                goto L_0x0089
            L_0x00ed:
                r0 = move-exception
                r6 = r2
            L_0x00ef:
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0003"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r2, r3, r0)     // Catch:{ all -> 0x0155 }
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)
                if (r1 == 0) goto L_0x0109
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)
                r1.close()     // Catch:{ IOException -> 0x015f }
            L_0x0109:
                if (r6 == 0) goto L_0x008a
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                r6.close()     // Catch:{ IOException -> 0x0176 }
                r0 = r7
                goto L_0x0089
            L_0x0118:
                r0 = move-exception
                r6 = r2
            L_0x011a:
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0002"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r2, r3, r0)     // Catch:{ all -> 0x0155 }
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)
                if (r1 == 0) goto L_0x0134
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)
                r1.close()     // Catch:{ IOException -> 0x0161 }
            L_0x0134:
                if (r6 == 0) goto L_0x008a
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                r6.close()     // Catch:{ IOException -> 0x0176 }
                r0 = r7
                goto L_0x0089
            L_0x0143:
                r0 = move-exception
                r6 = r2
                r1 = r2
                goto L_0x00a3
            L_0x0148:
                r0 = move-exception
                r6 = r2
                r1 = r2
                goto L_0x00c5
            L_0x014d:
                r0 = move-exception
                r6 = r2
                r1 = r2
                goto L_0x00ef
            L_0x0151:
                r0 = move-exception
                r6 = r2
                r1 = r2
                goto L_0x011a
            L_0x0155:
                r0 = move-exception
                r2 = r6
                r6 = r2
                goto L_0x00a3
            L_0x015a:
                r1 = move-exception
                goto L_0x007d
            L_0x015d:
                r0 = move-exception
                goto L_0x00df
            L_0x015f:
                r0 = move-exception
                goto L_0x0109
            L_0x0161:
                r0 = move-exception
                goto L_0x0134
            L_0x0163:
                r1 = move-exception
                goto L_0x00b6
            L_0x0166:
                r1 = move-exception
                goto L_0x00c2
            L_0x0169:
                r0 = move-exception
                goto L_0x011a
            L_0x016b:
                r0 = move-exception
                goto L_0x00ef
            L_0x016d:
                r0 = move-exception
                goto L_0x00c5
            L_0x0170:
                r0 = move-exception
                goto L_0x00a3
            L_0x0173:
                r1 = move-exception
                goto L_0x0089
            L_0x0176:
                r0 = move-exception
                r0 = r7
                goto L_0x0089
            L_0x017a:
                r7 = r0
                goto L_0x008a
            */
            throw new UnsupportedOperationException("Method not decompiled: p000jp.p001co.benesse.dcha.dchaservice.DchaService.C00001.copyUpdateImage(java.lang.String, java.lang.String):boolean");
        }

        public boolean deleteFile(String str) throws RemoteException {
            boolean z = false;
            Log.m0d("DchaService", "deleteFile 0001 path:" + str);
            try {
                String canonicalExternalPath = getCanonicalExternalPath(System.getenv("SECONDARY_STORAGE"));
                if (!TextUtils.isEmpty(str)) {
                    String canonicalExternalPath2 = getCanonicalExternalPath(str);
                    if (canonicalExternalPath2.startsWith(canonicalExternalPath)) {
                        File file = new File(canonicalExternalPath2);
                        if (file.isDirectory()) {
                            Log.m0d("DchaService", "deleteFile 0004");
                            File[] listFiles = file.listFiles();
                            if (listFiles == null) {
                                Log.m0d("DchaService", "deleteFile 0005");
                            } else {
                                for (File canonicalPath : listFiles) {
                                    if (!deleteFile(canonicalPath.getCanonicalPath())) {
                                        Log.m0d("DchaService", "deleteFile 0006");
                                        break;
                                    }
                                }
                            }
                            return z;
                        }
                        z = file.delete();
                        Log.m0d("DchaService", "deleteFile 0008 result:" + z);
                        return z;
                    }
                    Log.m0d("DchaService", "deleteFile 0003");
                    throw new SecurityException("The path is not a external storage.");
                }
                Log.m0d("DchaService", "deleteFile 0002");
                throw new IllegalArgumentException();
            } catch (Exception e) {
                Log.m1e("DchaService", "deleteFile 0007", e);
            }
        }

        public void disableADB() {
            Log.m0d("DchaService", "disableADB 0001");
            Settings.Secure.putInt(this.this$0.getContentResolver(), "adb_enabled", 0);
        }

        /* access modifiers changed from: protected */
        public boolean fileCopy(File file, File file2) {
            FileInputStream fileInputStream;
            FileOutputStream fileOutputStream;
            FileChannel fileChannel;
            FileChannel fileChannel2;
            FileInputStream fileInputStream2;
            FileChannel fileChannel3;
            Throwable th;
            FileOutputStream fileOutputStream2;
            Throwable th2;
            Log.m0d("DchaService", "fileCopy 0001");
            if (file2.isDirectory()) {
                Log.m0d("DchaService", "fileCopy 0002");
                file2 = new File(file2.getPath() + File.separator + file.getName());
            } else {
                Log.m0d("DchaService", "fileCopy 0003");
            }
            try {
                file2.createNewFile();
                fileInputStream = new FileInputStream(file);
                try {
                    fileChannel = fileInputStream.getChannel();
                    try {
                        fileOutputStream2 = new FileOutputStream(file2);
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream2 = null;
                        fileChannel2 = null;
                        fileOutputStream = fileOutputStream2;
                        try {
                            Log.m1e("DchaService", "fileCopy 0005", e);
                            Log.m0d("DchaService", "fileCopy 0006");
                            close(fileChannel);
                            close(fileChannel2);
                            close(fileInputStream);
                            close(fileOutputStream);
                            Log.m0d("DchaService", "fileCopy 0007");
                            return false;
                        } catch (Throwable th3) {
                            fileOutputStream2 = fileOutputStream;
                            th2 = th3;
                            Log.m0d("DchaService", "fileCopy 0006");
                            close(fileChannel);
                            close(fileChannel2);
                            close(fileInputStream);
                            close(fileOutputStream2);
                            throw th2;
                        }
                    } catch (Throwable th4) {
                        fileChannel3 = fileChannel;
                        th = th4;
                        fileChannel = fileChannel3;
                        fileOutputStream2 = null;
                        th2 = th;
                        fileChannel2 = null;
                        Log.m0d("DchaService", "fileCopy 0006");
                        close(fileChannel);
                        close(fileChannel2);
                        close(fileInputStream);
                        close(fileOutputStream2);
                        throw th2;
                    }
                    try {
                        fileChannel2 = fileOutputStream2.getChannel();
                        try {
                            fileChannel.transferTo(0, fileChannel.size(), fileChannel2);
                            file2.setLastModified(file.lastModified());
                            file2.setReadable(true, false);
                            file2.setWritable(true, false);
                            file2.setExecutable(true, false);
                            Log.m0d("DchaService", "fileCopy 0004");
                            Log.m0d("DchaService", "fileCopy 0006");
                            close(fileChannel);
                            close(fileChannel2);
                            close(fileInputStream);
                            close(fileOutputStream2);
                            return true;
                        } catch (Exception e2) {
                            e = e2;
                            fileOutputStream = fileOutputStream2;
                            Log.m1e("DchaService", "fileCopy 0005", e);
                            Log.m0d("DchaService", "fileCopy 0006");
                            close(fileChannel);
                            close(fileChannel2);
                            close(fileInputStream);
                            close(fileOutputStream);
                            Log.m0d("DchaService", "fileCopy 0007");
                            return false;
                        } catch (Throwable th5) {
                            th2 = th5;
                            Log.m0d("DchaService", "fileCopy 0006");
                            close(fileChannel);
                            close(fileChannel2);
                            close(fileInputStream);
                            close(fileOutputStream2);
                            throw th2;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        fileChannel2 = null;
                        fileOutputStream = fileOutputStream2;
                        Log.m1e("DchaService", "fileCopy 0005", e);
                        Log.m0d("DchaService", "fileCopy 0006");
                        close(fileChannel);
                        close(fileChannel2);
                        close(fileInputStream);
                        close(fileOutputStream);
                        Log.m0d("DchaService", "fileCopy 0007");
                        return false;
                    } catch (Throwable th6) {
                        th2 = th6;
                        fileChannel2 = null;
                        Log.m0d("DchaService", "fileCopy 0006");
                        close(fileChannel);
                        close(fileChannel2);
                        close(fileInputStream);
                        close(fileOutputStream2);
                        throw th2;
                    }
                } catch (Exception e4) {
                    e = e4;
                } catch (Throwable th7) {
                    th = th7;
                    fileInputStream2 = fileInputStream;
                    fileChannel3 = null;
                    fileInputStream = fileInputStream2;
                    th = th;
                    fileChannel = fileChannel3;
                    fileOutputStream2 = null;
                    th2 = th;
                    fileChannel2 = null;
                    Log.m0d("DchaService", "fileCopy 0006");
                    close(fileChannel);
                    close(fileChannel2);
                    close(fileInputStream);
                    close(fileOutputStream2);
                    throw th2;
                }
            } catch (Exception e5) {
                e = e5;
                fileInputStream = null;
                fileOutputStream = null;
                fileChannel = null;
                e = e;
                fileChannel2 = null;
                Log.m1e("DchaService", "fileCopy 0005", e);
                Log.m0d("DchaService", "fileCopy 0006");
                close(fileChannel);
                close(fileChannel2);
                close(fileInputStream);
                close(fileOutputStream);
                Log.m0d("DchaService", "fileCopy 0007");
                return false;
            } catch (Throwable th8) {
                th = th8;
                fileInputStream2 = null;
                fileChannel3 = null;
                fileInputStream = fileInputStream2;
                th = th;
                fileChannel = fileChannel3;
                fileOutputStream2 = null;
                th2 = th;
                fileChannel2 = null;
                Log.m0d("DchaService", "fileCopy 0006");
                close(fileChannel);
                close(fileChannel2);
                close(fileInputStream);
                close(fileOutputStream2);
                throw th2;
            }
        }

        public String getCanonicalExternalPath(String str) throws RemoteException {
            Log.m0d("DchaService", "getCanonicalExternalPath 0001 linkPath:" + str);
            if (TextUtils.isEmpty(str)) {
                Log.m0d("DchaService", "getCanonicalExternalPath 0002");
            } else {
                try {
                    str = new File(str).getCanonicalPath();
                } catch (Exception e) {
                    Log.m1e("DchaService", "getCanonicalExternalPath 0003", e);
                }
                String str2 = System.getenv("SECONDARY_STORAGE");
                if (!TextUtils.isEmpty(str) && str.startsWith(str2)) {
                    Log.m0d("DchaService", "getCanonicalExternalPath 0004");
                    try {
                        Iterator<StorageVolume> it = ((StorageManager) this.this$0.getSystemService("storage")).getStorageVolumes().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            StorageVolume next = it.next();
                            if (next.isRemovable()) {
                                String internalPath = next.getInternalPath();
                                Log.m0d("DchaService", "getCanonicalExternalPath 0005 path:" + internalPath);
                                str = str.replace(str2, internalPath);
                                break;
                            }
                        }
                    } catch (Exception e2) {
                        Log.m1e("DchaService", "getCanonicalExternalPath 0006", e2);
                    }
                }
                Log.m0d("DchaService", "getCanonicalExternalPath 0007 return:" + str);
            }
            return str;
        }

        public String getForegroundPackageName() throws RemoteException {
            Log.m0d("DchaService", "getForegroundPackageName 0001");
            try {
                List tasks = ActivityManagerNative.getDefault().getTasks(1);
                String packageName = ((ActivityManager.RunningTaskInfo) tasks.get(0)).baseActivity.getPackageName();
                Log.m0d("DchaService", "Foreground package name :" + packageName);
                tasks.clear();
                Log.m0d("DchaService", "getForegroundPackageName 0003 return:" + packageName);
                return packageName;
            } catch (Exception e) {
                Log.m1e("DchaService", "getForegroundPackageName 0002", e);
                throw new RemoteException();
            }
        }

        public int getSetupStatus() {
            Log.m0d("DchaService", "getSetupStatus 0001");
            int i = PreferenceManager.getDefaultSharedPreferences(this.this$0).getInt("DigichalizedStatus", -1);
            Log.m0d("DchaService", "getSetupStatus 0002 return:" + i);
            return i;
        }

        public int getUserCount() {
            Log.m0d("DchaService", "getUserCount 0001");
            int userCount = ((UserManager) this.this$0.getSystemService("user")).getUserCount();
            Log.m0d("DchaService", "getUserCount 0002 return:" + userCount);
            return userCount;
        }

        public void hideNavigationBar(boolean z) {
            Log.m0d("DchaService", "hideNavigationBar 0001 hide:" + z);
            this.this$0.hideNavigationBar(z);
        }

        /* JADX INFO: finally extract failed */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x00d1  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean installApp(java.lang.String r9, int r10) throws android.os.RemoteException {
            /*
                r8 = this;
                r2 = 0
                r0 = 1
                r1 = 0
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "installApp 0001 path:"
                r3.append(r4)
                r3.append(r9)
                java.lang.String r4 = " installFlag:"
                r3.append(r4)
                r3.append(r10)
                java.lang.String r4 = "DchaService"
                java.lang.String r3 = r3.toString()
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r4, r3)
                jp.co.benesse.dcha.dchaservice.DchaService r3 = r8.this$0     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                android.content.pm.PackageInstaller r3 = r3.getPackageInstaller()     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                android.content.pm.PackageInstaller$SessionParams r4 = new android.content.pm.PackageInstaller$SessionParams     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                r5 = 1
                r4.<init>(r5)     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                r5 = 1
                r4.setInstallLocation(r5)     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                r5 = 2
                if (r10 != r5) goto L_0x004d
            L_0x0038:
                r4.setAllowDowngrade(r0)     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                int r0 = r3.createSession(r4)     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                android.content.pm.PackageInstaller$Session r4 = r3.openSession(r0)     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                if (r4 != 0) goto L_0x004f
                java.lang.String r0 = "DchaService"
                java.lang.String r3 = "installApp 0002"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r3)     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
            L_0x004c:
                return r1
            L_0x004d:
                r0 = r1
                goto L_0x0038
            L_0x004f:
                android.os.HandlerThread r1 = new android.os.HandlerThread     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                java.lang.String r5 = "installAppThread"
                r1.<init>(r5)     // Catch:{ Exception -> 0x0100, all -> 0x0103 }
                r1.start()     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                android.os.Handler r2 = new android.os.Handler     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                android.os.Looper r5 = r1.getLooper()     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                r2.<init>(r5)     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                jp.co.benesse.dcha.dchaservice.DchaService$1$PackageInstallSessionCallback r5 = new jp.co.benesse.dcha.dchaservice.DchaService$1$PackageInstallSessionCallback     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                r5.<init>(r8)     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                r3.registerSessionCallback(r5, r2)     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                r8.installAppSession(r4, r9)     // Catch:{ all -> 0x00af }
                android.content.Intent r2 = new android.content.Intent     // Catch:{ all -> 0x00af }
                java.lang.String r6 = "jp.co.benesse.dcha.dchaservice.INSTALL_COMPLETE"
                r2.<init>(r6)     // Catch:{ all -> 0x00af }
                jp.co.benesse.dcha.dchaservice.DchaService r6 = r8.this$0     // Catch:{ all -> 0x00af }
                int r7 = android.os.Binder.getCallingPid()     // Catch:{ all -> 0x00af }
                java.lang.String r6 = r6.getPackageNameFromPid(r7)     // Catch:{ all -> 0x00af }
                r2.setPackage(r6)     // Catch:{ all -> 0x00af }
                jp.co.benesse.dcha.dchaservice.DchaService r6 = r8.this$0     // Catch:{ all -> 0x00af }
                android.content.Context r6 = r6.getBaseContext()     // Catch:{ all -> 0x00af }
                r7 = 0
                android.app.PendingIntent r0 = android.app.PendingIntent.getBroadcast(r6, r0, r2, r7)     // Catch:{ all -> 0x00af }
                android.content.IntentSender r0 = r0.getIntentSender()     // Catch:{ all -> 0x00af }
                r4.commit(r0)     // Catch:{ all -> 0x00af }
                monitor-enter(r5)     // Catch:{ all -> 0x00af }
            L_0x0094:
                boolean r0 = r5.finished     // Catch:{ all -> 0x00ac }
                if (r0 != 0) goto L_0x00dc
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "installApp 0003"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)     // Catch:{ InterruptedException -> 0x00a3 }
                r5.wait()     // Catch:{ InterruptedException -> 0x00a3 }
                goto L_0x0094
            L_0x00a3:
                r0 = move-exception
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "installApp 0004"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)     // Catch:{ all -> 0x00ac }
                goto L_0x0094
            L_0x00ac:
                r0 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x00ac }
                throw r0     // Catch:{ all -> 0x00af }
            L_0x00af:
                r0 = move-exception
                java.lang.String r2 = "DchaService"
                java.lang.String r6 = "installApp 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r2, r6)     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                r3.unregisterSessionCallback(r5)     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                r4.close()     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                throw r0     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
            L_0x00be:
                r0 = move-exception
            L_0x00bf:
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "installApp 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r2, r3, r0)     // Catch:{ all -> 0x00cc }
                android.os.RemoteException r0 = new android.os.RemoteException     // Catch:{ all -> 0x00cc }
                r0.<init>()     // Catch:{ all -> 0x00cc }
                throw r0     // Catch:{ all -> 0x00cc }
            L_0x00cc:
                r0 = move-exception
                r2 = r1
            L_0x00ce:
                r1 = r2
            L_0x00cf:
                if (r1 == 0) goto L_0x00db
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "installApp 0008"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r2, r3)
                r1.quit()
            L_0x00db:
                throw r0
            L_0x00dc:
                monitor-exit(r5)     // Catch:{ all -> 0x00ac }
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "installApp 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                r3.unregisterSessionCallback(r5)     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                r4.close()     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "installApp 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                boolean r0 = r5.result     // Catch:{ Exception -> 0x00be, all -> 0x0105 }
                java.lang.String r2 = "DchaService"
                java.lang.String r3 = "installApp 0008"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r2, r3)
                r1.quit()
                r1 = r0
                goto L_0x004c
            L_0x0100:
                r0 = move-exception
                r1 = r2
                goto L_0x00bf
            L_0x0103:
                r0 = move-exception
                goto L_0x00ce
            L_0x0105:
                r0 = move-exception
                goto L_0x00cf
            */
            throw new UnsupportedOperationException("Method not decompiled: p000jp.p001co.benesse.dcha.dchaservice.DchaService.C00001.installApp(java.lang.String, int):boolean");
        }

        public boolean isDeviceEncryptionEnabled() {
            Log.m0d("DchaService", "isDeviceEncryptionEnabled 0001");
            return false;
        }

        public void rebootPad(int i, String str) throws RemoteException {
            Log.m0d("DchaService", "rebootPad 0001 rebootMode:" + i + " srcFile:" + str);
            try {
                PowerManager powerManager = (PowerManager) this.this$0.getSystemService("power");
                switch (i) {
                    case 0:
                        Log.m0d("DchaService", "rebootPad 0002");
                        powerManager.reboot((String) null);
                        break;
                    case 1:
                        Log.m0d("DchaService", "rebootPad 0003");
                        RecoverySystem.rebootWipeUserData(this.this$0.getBaseContext());
                        break;
                    case Logger.ALL:
                        Log.m0d("DchaService", "rebootPad 0004");
                        if (str != null) {
                            RecoverySystem.installPackage(this.this$0.getBaseContext(), new File(str));
                            break;
                        }
                        break;
                    default:
                        Log.m0d("DchaService", "rebootPad 0005");
                        break;
                }
                Log.m0d("DchaService", "rebootPad 0007");
            } catch (Exception e) {
                Log.m1e("DchaService", "rebootPad 0006", e);
                throw new RemoteException();
            }
        }

        public void removeTask(String str) throws RemoteException {
            Log.m0d("DchaService", "removeTask 0001 packageName:" + str);
            try {
                IActivityManager service = ActivityManager.getService();
                List<ActivityManager.RecentTaskInfo> list = service.getRecentTasks(30, 1, UserHandle.myUserId()).getList();
                if (str != null) {
                    Log.m0d("DchaService", "removeTask 0002");
                    for (ActivityManager.RecentTaskInfo recentTaskInfo : list) {
                        if (str.equals(recentTaskInfo.baseIntent.getComponent().getPackageName())) {
                            Log.m0d("DchaService", "removeTask 0003");
                            service.removeTask(recentTaskInfo.persistentId);
                        }
                    }
                } else {
                    Log.m0d("DchaService", "removeTask 0004");
                    for (ActivityManager.RecentTaskInfo recentTaskInfo2 : list) {
                        service.removeTask(recentTaskInfo2.persistentId);
                    }
                }
                Log.m0d("DchaService", "removeTask 0006");
            } catch (Exception e) {
                Log.m1e("DchaService", "removeTask 0005", e);
                throw new RemoteException();
            }
        }

        public void sdUnmount() throws RemoteException {
            Log.m0d("DchaService", "sdUnmount 0001");
            try {
                StorageManager storageManager = (StorageManager) this.this$0.getSystemService("storage");
                for (StorageVolume next : storageManager.getStorageVolumes()) {
                    if (next.isRemovable()) {
                        Log.m0d("DchaService", "sdUnmount 0002");
                        storageManager.unmount(next.getId());
                    }
                }
                Log.m0d("DchaService", "sdUnmount 0004");
            } catch (Exception e) {
                Log.m1e("DchaService", "sdUnmount 0003", e);
                throw new RemoteException();
            }
        }

        public void setDefaultParam() throws RemoteException {
            Log.m0d("DchaService", "setDefaultParam 0001");
            try {
                this.this$0.setInitialSettingsWirelessNetwork();
                this.this$0.setInitialSettingsTerminal();
                this.this$0.setInitialSettingsUser();
                this.this$0.setInitialSettingsAccount();
                this.this$0.setInitialSettingsSystem();
                if (!TextUtils.equals(Build.TYPE, "eng")) {
                    Log.m0d("DchaService", "setDefaultParam 0002");
                    this.this$0.setInitialSettingsDevelopmentOptions();
                }
                this.this$0.pokeSystemProperties();
                Log.m0d("DchaService", "setDefaultParam 0004");
            } catch (RemoteException e) {
                Log.m1e("DchaService", "setDefaultParam 0003", e);
                throw new RemoteException();
            }
        }

        public void setDefaultPreferredHomeApp(String str) throws RemoteException {
            try {
                Log.m0d("DchaService", "setDefalutPreferredHomeApp 0001 packageName:" + str);
                PackageManager packageManager = this.this$0.getPackageManager();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.MAIN");
                intentFilter.addCategory("android.intent.category.HOME");
                intentFilter.addCategory("android.intent.category.DEFAULT");
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.addCategory("android.intent.category.DEFAULT");
                List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
                ArrayList arrayList = new ArrayList();
                ComponentName componentName = null;
                for (ResolveInfo next : queryIntentActivities) {
                    String str2 = next.activityInfo.applicationInfo.packageName;
                    String str3 = next.activityInfo.name;
                    Log.m0d("DchaService", "setDefalutPreferredHomeApp packName:" + str2);
                    Log.m0d("DchaService", "setDefalutPreferredHomeApp activityName:" + str3);
                    ComponentName componentName2 = new ComponentName(str2, str3);
                    arrayList.add(componentName2);
                    if (str2.equalsIgnoreCase(str)) {
                        Log.m0d("DchaService", "setDefalutPreferredHomeApp 0002");
                        Log.m0d("DchaService", "setDefalutPreferredHomeApp defaultHomeComponentName:" + componentName2);
                        componentName = componentName2;
                    }
                }
                ComponentName[] componentNameArr = (ComponentName[]) arrayList.toArray(new ComponentName[arrayList.size()]);
                if (componentName != null) {
                    Log.m0d("DchaService", "setDefalutPreferredHomeApp 0003");
                    packageManager.addPreferredActivityAsUser(intentFilter, 1081344, componentNameArr, componentName, 0);
                }
                Log.m0d("DchaService", "setDefalutPreferredHomeApp 0005");
            } catch (Exception e) {
                Log.m1e("DchaService", "setDefalutPrefferredHomeApp 0004", e);
                throw new RemoteException(e.toString());
            }
        }

        public void setPermissionEnforced(boolean z) throws RemoteException {
            Log.m0d("DchaService", "setPermissionEnforced 0001 enforced:" + z);
            ActivityThread.getPackageManager().setPermissionEnforced("android.permission.READ_EXTERNAL_STORAGE", z);
            Log.m0d("DchaService", "setPermissionEnforced 0002");
        }

        public void setSetupStatus(int i) {
            Log.m0d("DchaService", "setSetupStatus 0001 status:" + i);
            PreferenceManager.getDefaultSharedPreferences(this.this$0).edit().putInt("DigichalizedStatus", i).commit();
            Settings.System.putInt(this.this$0.getContentResolver(), "dcha_state", i);
        }

        public void setSystemTime(String str, String str2) {
            Log.m0d("DchaService", "setSystemTime 0001 time:" + str + " timeFormat:" + str2);
            try {
                Date parse = new SimpleDateFormat(str2, Locale.JAPAN).parse(str);
                String packageNameFromPid = this.this$0.getPackageNameFromPid(Binder.getCallingPid());
                Calendar instance = Calendar.getInstance(Locale.JAPAN);
                instance.set(2016, 1, 1, 0, 0);
                Calendar instance2 = Calendar.getInstance(Locale.JAPAN);
                instance2.setTime(parse);
                if (instance.compareTo(instance2) > 0) {
                    Log.m0d("DchaService", "setSystemTime 0002");
                    DchaService dchaService = this.this$0;
                    EmergencyLog.write(dchaService, "ELK008", str + " " + packageNameFromPid);
                } else {
                    Log.m0d("DchaService", "setSystemTime 0003");
                    SystemClock.setCurrentTimeMillis(parse.getTime());
                }
                Log.m0d("DchaService", "setSystemTime set time :" + parse);
            } catch (Exception e) {
                Log.m1e("DchaService", "setSystemTime 0004", e);
            }
            Log.m0d("DchaService", "setSystemTime 0005");
        }

        public boolean uninstallApp(String str, int i) throws RuntimeException {
            Log.m0d("DchaService", "uninstallApp 0001 packageName:" + str + " uninstallFlag:" + i);
            try {
                String packageNameFromPid = this.this$0.getPackageNameFromPid(getCallingPid());
                DchaService dchaService = this.this$0;
                EmergencyLog.write(dchaService, "ELK002", str + " " + packageNameFromPid);
                PackageManager packageManager = this.this$0.getPackageManager();
                int i2 = 2;
                if (i != 1) {
                    Log.m0d("DchaService", "uninstallApp 0003");
                } else {
                    Log.m0d("DchaService", "uninstallApp 0002");
                    i2 = 3;
                }
                PackageDeleteObserver packageDeleteObserver = new PackageDeleteObserver(this);
                packageManager.deletePackage(str, packageDeleteObserver, i2);
                synchronized (packageDeleteObserver) {
                    while (!packageDeleteObserver.finished) {
                        try {
                            packageDeleteObserver.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
                Log.m0d("DchaService", "uninstallApp 0004");
                return packageDeleteObserver.result;
            } catch (Exception e2) {
                Log.m1e("DchaService", "uninstallApp 0005", e2);
                throw new RuntimeException();
            }
        }

        public boolean verifyUpdateImage(String str) {
            Log.m0d("DchaService", "verifyUpdateImage 0001 updateFile:" + str);
            return true;
        }
    };

    /* renamed from: jp.co.benesse.dcha.dchaservice.DchaService$SystemPropPoker */
    static class SystemPropPoker extends AsyncTask<Void, Void, Void> {
        private final String TAG = "SystemPropPoker";

        SystemPropPoker() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            Log.m0d("SystemPropPoker", "doInBackground 0001");
            for (String str : ServiceManager.listServices()) {
                IBinder checkService = ServiceManager.checkService(str);
                if (checkService != null) {
                    Log.m0d("SystemPropPoker", "doInBackground 0003");
                    Parcel obtain = Parcel.obtain();
                    try {
                        checkService.transact(1599295570, obtain, (Parcel) null, 0);
                    } catch (RemoteException e) {
                        Log.m1e("SystemPropPoker", "doInBackground 0004", e);
                    } catch (Exception e2) {
                        Log.m2v("DevSettings", "Somone wrote a bad service '" + str + "' that doesn't like to be poked: " + e2);
                        Log.m1e("SystemPropPoker", "doInBackground 0005", e2);
                    }
                    obtain.recycle();
                }
            }
            Log.m0d("SystemPropPoker", "doInBackground 0006");
            return null;
        }
    }

    private static Signature getFirstSignature(PackageInfo packageInfo) {
        if (packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
            return null;
        }
        return packageInfo.signatures[0];
    }

    private int getNewPriorityCategories(NotificationManager.Policy policy, boolean z, int i) {
        int i2 = policy.priorityCategories;
        return z ? i2 | i : i2 & (i ^ -1);
    }

    private static Signature getSystemSignature(PackageManager packageManager) {
        try {
            return getFirstSignature(packageManager.getPackageInfo("android", 64));
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    protected static boolean isSystemPackage(PackageManager packageManager, PackageInfo packageInfo) {
        if (sSystemSignature == null) {
            sSystemSignature = new Signature[]{getSystemSignature(packageManager)};
        }
        return sSystemSignature[0] != null && sSystemSignature[0].equals(getFirstSignature(packageInfo));
    }

    protected static void resetDebuggerOptions() {
        Log.m0d("DchaService", "resetDebuggerOptions 0001");
        try {
            ActivityManagerNative.getDefault().setDebugApp((String) null, false, true);
        } catch (RemoteException e) {
        }
    }

    private NotificationManager.Policy savePolicy(NotificationManager.Policy policy, int i, int i2, int i3, int i4) {
        NotificationManager.Policy policy2 = new NotificationManager.Policy(i, i2, i3, i4);
        NotificationManager.from(getApplicationContext()).setNotificationPolicy(policy2);
        return policy2;
    }

    public void doCancelDigichalized() throws RemoteException {
        try {
            Log.m0d("DchaService", "doCancelDigichalized 0001");
            int setupStatus = this.mDchaServiceStub.getSetupStatus();
            Log.m0d("DchaService", "status:" + Integer.toString(setupStatus));
            String packageNameFromPid = getPackageNameFromPid(Binder.getCallingPid());
            EmergencyLog.write(this, "ELK000", setupStatus + " " + isFinishDigichalize() + " " + packageNameFromPid);
            if (setupStatus == 3 || isFinishDigichalize()) {
                Log.m0d("DchaService", "doCancelDigichalized 0008");
                EmergencyLog.write(this, "ELK005", setupStatus + " " + isFinishDigichalize() + " " + packageNameFromPid);
            } else {
                Log.m0d("DchaService", "doCancelDigichalized 0002");
                EmergencyLog.write(this, "ELK004", setupStatus + " " + isFinishDigichalize() + " " + packageNameFromPid);
                Intent intent = new Intent();
                intent.setPackage("jp.co.benesse.dcha.databox");
                intent.setAction("jp.co.benesse.dcha.databox.intent.action.COMMAND");
                intent.addCategory("jp.co.benesse.dcha.databox.intent.category.WIPE");
                intent.putExtra("send_service", "DchaService");
                sendBroadcast(intent);
                Log.m0d("DchaService", "doCancelDigichalized send wipeDataBoxIntent intent");
                HandlerThread handlerThread = new HandlerThread("handlerThread");
                handlerThread.start();
                new Handler(handlerThread.getLooper()).post(new Runnable(this) {
                    final DchaService this$0;

                    {
                        this.this$0 = r1;
                    }

                    public void run() {
                        if (!DchaService.doCancelDigichalizedFlg) {
                            Log.m0d("DchaService", "doCancelDigichalized 0003");
                            try {
                                Log.m0d("DchaService", "start uninstallApp");
                                boolean unused = DchaService.doCancelDigichalizedFlg = true;
                                for (ApplicationInfo next : this.this$0.getPackageManager().getInstalledApplications(128)) {
                                    if ((next.flags & 1) == 1) {
                                        Log.m0d("DchaService", "doCancelDigichalized 0004");
                                    } else {
                                        this.this$0.mDchaServiceStub.uninstallApp(next.packageName, 0);
                                    }
                                }
                                this.this$0.mDchaServiceStub.setSetupStatus(0);
                                Log.m0d("DchaService", "end uninstallApp");
                            } catch (RemoteException e) {
                                Log.m1e("DchaService", "doCancelDigichalized 0005", e);
                            } catch (Throwable th) {
                                Log.m0d("DchaService", "doCancelDigichalized 0006");
                                boolean unused2 = DchaService.doCancelDigichalizedFlg = false;
                                throw th;
                            }
                            Log.m0d("DchaService", "doCancelDigichalized 0006");
                            boolean unused3 = DchaService.doCancelDigichalizedFlg = false;
                        }
                        Log.m0d("DchaService", "doCancelDigichalized 0007");
                    }
                });
            }
            Log.m0d("DchaService", "doCancelDigichalized 0010");
        } catch (Exception e) {
            Log.m1e("DchaService", "doCancelDigichalized 0009", e);
            throw new RemoteException();
        }
    }

    /* access modifiers changed from: protected */
    public String getPackageNameFromPid(int i) {
        String str;
        Log.m0d("DchaService", "getPackageNameFromPid 0001");
        String str2 = "Unknown";
        for (ActivityManager.RunningAppProcessInfo next : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
            if (i == next.pid) {
                Log.m0d("DchaService", "getPackageNameFromPid 0002");
                str = next.processName;
            } else {
                str = str2;
            }
            str2 = str;
        }
        Log.m0d("DchaService", "getPackageNameFromPid 0003");
        return str2;
    }

    public void hideNavigationBar(boolean z) {
        Log.m0d("DchaService", "hideNavigationBar 0001");
        Settings.System.putInt(getContentResolver(), "hide_navigation_bar", z ? 1 : 0);
    }

    /* access modifiers changed from: protected */
    public boolean isFinishDigichalize() {
        Log.m0d("DchaService", "isFinishDigichalize 0001");
        return UpdateLog.exists();
    }

    public IBinder onBind(Intent intent) {
        Log.m0d("DchaService", "onBind 0001");
        return this.mDchaServiceStub;
    }

    public void onCreate() {
        Log.m0d("DchaService", "onCreate 0001");
        super.onCreate();
    }

    public void onDestroy() {
        Log.m0d("DchaService", "onDestroy 0001");
        super.onDestroy();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int onStartCommand(android.content.Intent r4, int r5, int r6) {
        /*
            r3 = this;
            r2 = 0
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0001"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
            if (r4 == 0) goto L_0x0037
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0002"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
            java.lang.String r0 = "REQ_COMMAND"
            int r0 = r4.getIntExtra(r0, r2)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "onStartCommand intent command:"
            r1.append(r2)
            r1.append(r0)
            java.lang.String r2 = "DchaService"
            java.lang.String r1 = r1.toString()
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r2, r1)
            switch(r0) {
                case 1: goto L_0x0061;
                case 2: goto L_0x0055;
                case 3: goto L_0x0040;
                default: goto L_0x0030;
            }
        L_0x0030:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0006"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x004c }
        L_0x0037:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0008"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
            r0 = 2
            return r0
        L_0x0040:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0005"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x004c }
            r0 = 1
            r3.hideNavigationBar(r0)     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x004c:
            r0 = move-exception
            java.lang.String r1 = "DchaService"
            java.lang.String r2 = "onStartCommand 0007"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r1, r2, r0)
            goto L_0x0037
        L_0x0055:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0004"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x004c }
            r0 = 0
            r3.hideNavigationBar(r0)     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        L_0x0061:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0003"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x004c }
            r0 = 0
            r3.hideNavigationBar(r0)     // Catch:{ Exception -> 0x004c }
            r3.doCancelDigichalized()     // Catch:{ Exception -> 0x004c }
            goto L_0x0037
        */
        throw new UnsupportedOperationException("Method not decompiled: p000jp.p001co.benesse.dcha.dchaservice.DchaService.onStartCommand(android.content.Intent, int, int):int");
    }

    /* access modifiers changed from: package-private */
    public void pokeSystemProperties() {
        Log.m0d("DchaService", "pokeSystemProperties 0001");
        new SystemPropPoker().execute(new Void[0]);
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsAccount() {
        Log.m0d("DchaService", "setInitialSettingsAccount start");
        Context applicationContext = getApplicationContext();
        LocalePicker.updateLocale(new Locale("ja", "JP"));
        Settings.Secure.putInt(getContentResolver(), "show_ime_with_hard_keyboard", 0);
        Settings.Secure.putInt(getContentResolver(), "spell_checker_enabled", 0);
        Settings.Secure.putString(getContentResolver(), "autofill_service", "");
        ((InputManager) applicationContext.getSystemService("input")).setPointerSpeed(applicationContext, 0);
        ContentResolver.setMasterSyncAutomaticallyAsUser(false, Process.myUserHandle().getIdentifier());
        Log.m0d("DchaService", "setInitialSettingsAccount 0002");
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsDevelopmentOptions() throws RemoteException {
        Log.m0d("DchaService", "setInitialSettingsDevelopmentOptions start");
        getApplicationContext();
        Settings.Global.putInt(getContentResolver(), "development_settings_enabled", 0);
        Settings.Global.putInt(getContentResolver(), "stay_on_while_plugged_in", 0);
        writeQsTileDisable();
        Settings.Global.putInt(getContentResolver(), "adb_enabled", 0);
        Settings.Global.putInt(getContentResolver(), "bugreport_in_power_menu", 0);
        AppOpsManager appOpsManager = (AppOpsManager) getSystemService("appops");
        List<AppOpsManager.PackageOps> packagesForOps = appOpsManager.getPackagesForOps(new int[]{58});
        if (packagesForOps != null) {
            for (AppOpsManager.PackageOps packageOps : packagesForOps) {
                if (((AppOpsManager.OpEntry) packageOps.getOps().get(0)).getMode() != 2) {
                    String packageName = packageOps.getPackageName();
                    try {
                        appOpsManager.setMode(58, getPackageManager().getApplicationInfo(packageName, 512).uid, packageName, 2);
                    } catch (PackageManager.NameNotFoundException e) {
                    }
                }
            }
        }
        Settings.Global.putInt(getContentResolver(), "debug_view_attributes", 0);
        Settings.Global.putString(getContentResolver(), "debug_app", "");
        resetDebuggerOptions();
        Settings.Global.putInt(getContentResolver(), "wait_for_debugger", 0);
        Settings.Global.putInt(getContentResolver(), "verifier_verify_adb_installs", 0);
        Settings.System.putInt(getContentResolver(), "allow_screen_shot", 0);
        SystemProperties.set("persist.logd.size", "256K");
        Settings.System.putInt(getContentResolver(), "show_touches", 0);
        Settings.System.putInt(getContentResolver(), "pointer_location", 0);
        writeShowUpdatesOption();
        SystemProperties.set("debug.layout", "false");
        Settings.Global.putInt(getContentResolver(), "debug.force_rtl", 0);
        SystemProperties.set("debug.force_rtl", "0");
        writeAnimationScaleOption(0, (ListPreference) null, (Object) null);
        writeAnimationScaleOption(1, (ListPreference) null, (Object) null);
        writeAnimationScaleOption(2, (ListPreference) null, (Object) null);
        Settings.Global.putString(getContentResolver(), "overlay_display_devices", (String) null);
        SystemProperties.set("persist.sys.ui.hw", "false");
        SystemProperties.set("debug.hwui.show_dirty_regions", (String) null);
        SystemProperties.set("debug.hwui.show_layers_updates", (String) null);
        SystemProperties.set("debug.hwui.overdraw", "0");
        SystemProperties.set("debug.hwui.show_non_rect_clip", "0");
        SystemProperties.set("debug.egl.force_msaa", "false");
        writeDisableOverlaysOption();
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "usb_audio_automatic_routing_disabled", 0);
        writeStrictModeVisualOptions();
        writeCpuUsageOptions();
        SystemProperties.set("debug.hwui.profile", "");
        SystemProperties.set("debug.egl.trace", "");
        writeImmediatelyDestroyActivitiesOptions();
        writeAppProcessLimitOptions((Object) null);
        Settings.Secure.putInt(getContentResolver(), "anr_show_background", 0);
        Settings.Global.putInt(getContentResolver(), "show_notification_channel_warnings", 0);
        SystemProperties.set("persist.bluetooth.showdeviceswithoutnames", "false");
        Settings.Global.putInt(getContentResolver(), "development_settings_enabled", 0);
        if (Settings.Global.getInt(getContentResolver(), "development_settings_enabled", 0) != 0) {
            Log.m0d("DchaService", "setInitialSettingsDevelopmentOptions 0002");
            writeShowUpdatesOption();
            Settings.Global.putInt(getContentResolver(), "development_settings_enabled", 0);
        }
        Log.m0d("DchaService", "setInitialSettingsDevelopmentOptions 0003");
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsSystem() {
        Log.m0d("DchaService", "setInitialSettingsSystem start");
        Settings.Global.putInt(getContentResolver(), "auto_time", 1);
        ((AlarmManager) getSystemService("alarm")).setTimeZone("Asia/Tokyo");
        Settings.System.putString(getContentResolver(), "time_12_24", "12");
        Settings.System.putString(getContentResolver(), "date_format", "");
        Settings.Secure.putInt(getContentResolver(), "accessibility_captioning_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_magnification_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_magnification_navbar_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "high_text_contrast_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "speak_password", 0);
        Settings.Global.putInt(getContentResolver(), "enable_accessibility_global_gesture_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "long_press_timeout", 400);
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_inversion_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", 0);
        Log.m0d("DchaService", "setInitialSettingsSystem 0003");
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsTerminal() throws RemoteException {
        Log.m0d("DchaService", "setInitialSettingsTerminal 0001");
        try {
            Settings.System.putInt(getContentResolver(), "screen_brightness_mode", 0);
            Settings.System.putLong(getContentResolver(), "screen_off_timeout", 900000);
            Settings.System.putInt(getContentResolver(), "screen_dim_timeout", 300000);
            Settings.Secure.putInt(getContentResolver(), "screensaver_enabled", 0);
            Configuration configuration = new Configuration();
            configuration.fontScale = Float.parseFloat("1.0");
            ActivityManagerNative.getDefault().updatePersistentConfiguration(configuration);
            Settings.System.putInt(getContentResolver(), "accelerometer_rotation", 0);
            INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
            Context applicationContext = getApplicationContext();
            NotificationManager.Policy notificationPolicy = NotificationManager.from(applicationContext).getNotificationPolicy();
            NotificationManager.Policy savePolicy = savePolicy(notificationPolicy, getNewPriorityCategories(notificationPolicy, false, 1), notificationPolicy.priorityCallSenders, notificationPolicy.priorityMessageSenders, notificationPolicy.suppressedVisualEffects);
            NotificationManager.Policy savePolicy2 = savePolicy(savePolicy, getNewPriorityCategories(savePolicy, false, 2), savePolicy.priorityCallSenders, savePolicy.priorityMessageSenders, savePolicy.suppressedVisualEffects);
            NotificationManager.Policy savePolicy3 = savePolicy(savePolicy2, getNewPriorityCategories(savePolicy2, false, 4), savePolicy2.priorityCallSenders, savePolicy2.priorityMessageSenders, savePolicy2.suppressedVisualEffects);
            savePolicy(savePolicy3, getNewPriorityCategories(savePolicy3, false, 8), savePolicy3.priorityCallSenders, savePolicy3.priorityMessageSenders, savePolicy3.suppressedVisualEffects);
            if (this.mDchaServiceStub.getSetupStatus() != 3) {
                Log.m0d("DchaService", "setInitialSettingsTerminal 0002");
                AppOpsManager appOpsManager = (AppOpsManager) applicationContext.getSystemService("appops");
                try {
                    IPackageManager.Stub.asInterface(ServiceManager.getService("package")).resetApplicationPreferences(UserHandle.myUserId());
                } catch (RemoteException e) {
                }
                appOpsManager.resetAllModes();
            }
            PackageManager packageManager = getPackageManager();
            List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(512);
            for (int i = 0; i < installedApplications.size(); i++) {
                ApplicationInfo applicationInfo = installedApplications.get(i);
                try {
                    if (!isSystemPackage(packageManager, packageManager.getPackageInfo(applicationInfo.packageName, 64))) {
                        Log.m0d("DchaService", "setInitialSettingsTerminal 0003:" + applicationInfo.packageName);
                        asInterface.setNotificationsEnabledWithImportanceLockForPackage(applicationInfo.packageName, applicationInfo.uid, true);
                    }
                } catch (Exception e2) {
                }
                if (!TextUtils.equals("com.mediatek.mtklogger", applicationInfo.packageName)) {
                    Log.m0d("DchaService", "setInitialSettingsTerminal 0004");
                    if (!applicationInfo.enabled && packageManager.getApplicationEnabledSetting(applicationInfo.packageName) == 3) {
                        Log.m0d("DchaService", "setInitialSettingsTerminal 0005:" + applicationInfo.packageName);
                        packageManager.setApplicationEnabledSetting(applicationInfo.packageName, 0, 1);
                    }
                } else {
                    Log.m0d("DchaService", "setInitialSettingsTerminal 0006:" + applicationInfo.packageName);
                    packageManager.setApplicationEnabledSetting(applicationInfo.packageName, 3, 0);
                }
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, 4096);
                    if (!isSystemPackage(packageManager, packageInfo)) {
                        for (String str : packageInfo.requestedPermissions) {
                            if ((applicationContext.getPackageManager().getPermissionFlags(str, packageInfo.packageName, Process.myUserHandle()) & 16) == 0) {
                                if ("android.permission.READ_EXTERNAL_STORAGE".equals(str)) {
                                    packageManager.grantRuntimePermission(packageInfo.packageName, str, Process.myUserHandle());
                                }
                            }
                        }
                    }
                } catch (Exception e3) {
                }
            }
            AppOpsManager appOpsManager2 = (AppOpsManager) applicationContext.getSystemService("appops");
            for (ApplicationInfo next : packageManager.getInstalledApplications(128)) {
                packageManager.updateIntentVerificationStatusAsUser(next.packageName, 4, UserHandle.myUserId());
                appOpsManager2.setMode(24, next.uid, next.packageName, 0);
                appOpsManager2.setMode(23, next.uid, next.packageName, 0);
            }
            NetworkPolicyManager from = NetworkPolicyManager.from(applicationContext);
            int[] uidsWithPolicy = from.getUidsWithPolicy(1);
            int currentUser = ActivityManager.getCurrentUser();
            for (int i2 : uidsWithPolicy) {
                if (UserHandle.getUserId(i2) == currentUser) {
                    from.setUidPolicy(i2, 0);
                }
            }
            Settings.Secure.putInt(getContentResolver(), "lock_screen_show_notifications", 1);
            Settings.Secure.putInt(getContentResolver(), "lock_screen_allow_private_notifications", 1);
            Settings.Secure.putInt(getContentResolver(), "instant_apps_enabled", 0);
            Settings.Secure.putInt(getContentResolver(), "notification_badging", 0);
            Settings.System.putInt(getContentResolver(), "vibrate_when_ringing", 0);
            ((NotificationManager) applicationContext.getSystemService("notification")).setZenMode(0, (Uri) null, "DchaService");
            Settings.Secure.putString(getContentResolver(), "assistant", "");
            Settings.Secure.putString(getContentResolver(), "voice_interaction_service", "");
            Settings.Secure.putString(getContentResolver(), "voice_recognition_service", "");
            RingtoneManager.setActualDefaultRingtoneUri(applicationContext, 1, (Uri) null);
            RingtoneManager.setActualDefaultRingtoneUri(applicationContext, 2, (Uri) null);
            RingtoneManager.setActualDefaultRingtoneUri(applicationContext, 4, (Uri) null);
            AudioManager audioManager = (AudioManager) getSystemService("audio");
            audioManager.setStreamVolume(3, 5, 0);
            audioManager.adjustStreamVolume(3, 100, 0);
            audioManager.setStreamVolume(4, 12, 0);
            audioManager.adjustStreamVolume(4, 100, 0);
            audioManager.setStreamVolume(5, 8, 0);
            audioManager.adjustStreamVolume(5, 100, 0);
            audioManager.setStreamVolume(1, 8, 0);
            audioManager.adjustStreamVolume(1, 100, 0);
            audioManager.setStreamVolume(2, 8, 0);
            audioManager.adjustStreamVolume(2, 100, 0);
            audioManager.loadSoundEffects();
            Settings.System.putInt(getContentResolver(), "sound_effects_enabled", 1);
            Settings.System.putInt(getContentResolver(), "lockscreen_sounds_enabled", 0);
            UsbManager usbManager = (UsbManager) getSystemService("usb");
            usbManager.setCurrentFunctions(0);
            usbManager.setScreenUnlockedFunctions(0);
            ((PowerManager) getSystemService("power")).setPowerSaveMode(false);
            Settings.Global.putInt(getContentResolver(), "low_power_trigger_level", 0);
            Settings.Global.putInt(getContentResolver(), "adaptive_battery_management_enabled", 0);
            Settings.Global.putInt(getContentResolver(), "app_auto_restriction_enabled", 0);
            Settings.Secure.putInt(getContentResolver(), "night_display_activated", 0);
            WallpaperManager.getInstance(applicationContext).clearWallpaper();
            Log.m0d("DchaService", "setInitialSettingsTerminal 0008");
        } catch (RemoteException e4) {
            Log.m1e("DchaService", "setInitialSettingsTerminal 0007", e4);
            throw e4;
        }
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsUser() {
        Log.m0d("DchaService", "setInitialSettingsUser start");
        Settings.Secure.putInt(getContentResolver(), "location_mode", 0);
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this);
        int callingUserId = UserHandle.getCallingUserId();
        lockPatternUtils.resetKeyStore(callingUserId);
        lockPatternUtils.setLockScreenDisabled(true, callingUserId);
        Settings.System.putInt(getContentResolver(), "show_password", 1);
        Settings.Secure.putInt(getContentResolver(), "install_non_market_apps", 0);
        Settings.System.putInt(getContentResolver(), "lock_to_app_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "lock_to_app_exit_locked", 0);
        Log.m0d("DchaService", "setInitialSettingsUser 0002");
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsWirelessNetwork() throws RemoteException {
        Log.m0d("DchaService", "setInitialSettingsWirelessNetwork 0001");
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService("wifi");
        if (wifiManager != null) {
            Log.m0d("DchaService", "setInitialSettingsWirelessNetwork 0002");
            wifiManager.setWifiEnabled(true);
            wifiManager.enableVerboseLogging(0);
            wifiManager.stopSoftAp();
        }
        Settings.Secure.putInt(getContentResolver(), "wifi_networks_available_notification_on", 0);
        Settings.Global.putInt(getContentResolver(), "wifi_scan_always_enabled", 0);
        Settings.Global.putInt(getContentResolver(), "wifi_sleep_policy", 0);
        Settings.Global.putInt(getContentResolver(), "wifi_display_on", 0);
        Settings.Global.putInt(getContentResolver(), "wifi_display_certification_on", 0);
        Settings.Global.putInt(getContentResolver(), "bluetooth_on", 0);
        BluetoothAdapter.getDefaultAdapter().disable();
        Settings.Global.putInt(getContentResolver(), "ble_scan_always_enabled", 0);
        Settings.Global.putInt(getContentResolver(), "airplane_mode_on", 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.putExtra("state", false);
        sendBroadcast(intent);
        Log.m0d("DchaService", "setInitialSettingsWirelessNetwork 0003");
    }

    /* access modifiers changed from: protected */
    public void writeAnimationScaleOption(int i, ListPreference listPreference, Object obj) {
        Log.m0d("DchaService", "writeAnimationScaleOption 0001");
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).setAnimationScale(i, obj != null ? Float.parseFloat(obj.toString()) : 1.0f);
        } catch (RemoteException e) {
        }
    }

    /* access modifiers changed from: protected */
    public void writeAppProcessLimitOptions(Object obj) {
        int i;
        Log.m0d("DchaService", "writeAppProcessLimitOptions 0001");
        if (obj != null) {
            try {
                i = Integer.parseInt(obj.toString());
            } catch (RemoteException e) {
                return;
            }
        } else {
            i = -1;
        }
        ActivityManagerNative.getDefault().setProcessLimit(i);
    }

    /* access modifiers changed from: protected */
    public void writeCpuUsageOptions() {
        Log.m0d("DchaService", "writeCpuUsageOptions 0001");
        Settings.Global.putInt(getContentResolver(), "show_processes", 0);
        Intent className = new Intent().setClassName("com.android.systemui", "com.android.systemui.LoadAverageService");
        Log.m0d("DchaService", "writeCpuUsageOptions 0003");
        stopService(className);
    }

    /* access modifiers changed from: protected */
    public void writeDisableOverlaysOption() {
        Log.m0d("DchaService", "writeDisableOverlaysOption 0001");
        try {
            IBinder service = ServiceManager.getService("SurfaceFlinger");
            if (service != null) {
                Log.m0d("DchaService", "writeDisableOverlaysOption 0002");
                Parcel obtain = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                obtain.writeInt(0);
                service.transact(1008, obtain, (Parcel) null, 0);
                obtain.recycle();
            }
        } catch (RemoteException e) {
        }
    }

    /* access modifiers changed from: protected */
    public void writeImmediatelyDestroyActivitiesOptions() {
        Log.m0d("DchaService", "writeImmediatelyDestroyActivitiesOptions 0001");
        try {
            ActivityManagerNative.getDefault().setAlwaysFinish(false);
        } catch (RemoteException e) {
        }
    }

    /* access modifiers changed from: protected */
    public void writeQsTileDisable() {
        Log.m0d("DchaService", "writeQsTileDisable 0001");
        PackageManager packageManager = getApplicationContext().getPackageManager();
        IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
        for (ResolveInfo resolveInfo : packageManager.queryIntentServices(new Intent("android.service.quicksettings.action.QS_TILE"), 512)) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
            packageManager.setComponentEnabledSetting(componentName, 2, 1);
            if (asInterface != null) {
                try {
                    Log.m0d("DchaService", "writeQsTileDisable 0002");
                    asInterface.remTile(componentName);
                } catch (RemoteException e) {
                    Log.m1e("DchaService", "writeQsTileDisable 0003", e);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeShowUpdatesOption() {
        Log.m0d("DchaService", "writeShowUpdatesOption 0001");
        try {
            IBinder service = ServiceManager.getService("SurfaceFlinger");
            if (service != null) {
                Log.m0d("DchaService", "writeShowUpdatesOption 0002");
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                service.transact(1010, obtain, obtain2, 0);
                obtain2.readInt();
                obtain2.readInt();
                int readInt = obtain2.readInt();
                obtain2.readInt();
                obtain2.readInt();
                obtain2.recycle();
                obtain.recycle();
                if (readInt != 0) {
                    Log.m0d("DchaService", "writeShowUpdatesOption 0003");
                    Parcel obtain3 = Parcel.obtain();
                    obtain3.writeInterfaceToken("android.ui.ISurfaceComposer");
                    obtain3.writeInt(0);
                    service.transact(1002, obtain3, (Parcel) null, 0);
                    obtain3.recycle();
                }
            }
        } catch (RemoteException e) {
        }
    }

    /* access modifiers changed from: protected */
    public void writeStrictModeVisualOptions() {
        Log.m0d("DchaService", "writeStrictModeVisualOptions 0001");
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).setStrictModeVisualIndicatorPreference("");
        } catch (RemoteException e) {
        }
    }
}
