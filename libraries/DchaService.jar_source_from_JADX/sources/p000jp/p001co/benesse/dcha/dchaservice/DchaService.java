package p000jp.p001co.benesse.dcha.dchaservice;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityThread;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.NetworkPolicyManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Binder;
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
import android.os.storage.IMountService;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.IWindowManager;
import android.view.textservice.TextServicesManager;
import com.android.internal.app.LocalePicker;
import com.android.internal.widget.LockPatternUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import p000jp.p001co.benesse.dcha.dchaservice.IDchaService;
import p000jp.p001co.benesse.dcha.dchaservice.util.Log;

/* renamed from: jp.co.benesse.dcha.dchaservice.DchaService */
public class DchaService extends Service {
    /* access modifiers changed from: private */
    public static boolean doCancelDigichalizedFlg = false;
    private static Signature[] sSystemSignature;
    protected ListPreference mAnimatorDurationScale;
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

        /* renamed from: jp.co.benesse.dcha.dchaservice.DchaService$1$PackageInstallObserver */
        class PackageInstallObserver extends IPackageInstallObserver.Stub {
            boolean finished;
            int result;
            final C00001 this$1;

            PackageInstallObserver(C00001 r1) {
                this.this$1 = r1;
            }

            public void packageInstalled(String str, int i) {
                Log.m0d("DchaService", "packageInstalled 0001");
                synchronized (this) {
                    this.finished = true;
                    this.result = i;
                    notifyAll();
                }
            }
        }

        {
            this.this$0 = r1;
        }

        private String installFailureToString(int i) {
            Log.m0d("DchaService", "installFailureToString 0001");
            for (Field field : PackageManager.class.getFields()) {
                if (field.getType() == Integer.TYPE) {
                    Log.m0d("DchaService", "installFailureToString 0002");
                    int modifiers = field.getModifiers();
                    if (!((modifiers & 16) == 0 || (modifiers & 1) == 0 || (modifiers & 8) == 0)) {
                        Log.m0d("DchaService", "installFailureToString 0003");
                        String name = field.getName();
                        if (name.startsWith("INSTALL_FAILED_") || name.startsWith("INSTALL_PARSE_FAILED_")) {
                            Log.m0d("DchaService", "installFailureToString 0004");
                            try {
                                if (i == field.getInt((Object) null)) {
                                    Log.m0d("DchaService", "installFailureToString 0005");
                                    return name;
                                }
                            } catch (IllegalAccessException e) {
                                Log.m2e("DchaService", "installFailureToString 0006", e);
                            }
                        }
                    }
                }
            }
            Log.m0d("DchaService", "installFailureToString 0007");
            return Integer.toString(i);
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
            Log.m0d("DchaService", "clearDefaultPreferredApp 0001");
            try {
                this.this$0.getPackageManager().clearPackagePreferredActivities(str);
            } catch (Exception e) {
                Log.m2e("DchaService", "clearDefaultPreferredApp 0002", e);
                throw new RemoteException(e.toString());
            }
        }

        public boolean copyFile(String str, String str2) throws RemoteException {
            Log.m0d("DchaService", "copyFile 0001");
            return false;
        }

        /* JADX WARNING: Removed duplicated region for block: B:32:0x008e  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x009a  */
        /* JADX WARNING: Removed duplicated region for block: B:47:0x00c1  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x00cd  */
        /* JADX WARNING: Removed duplicated region for block: B:62:0x00f4  */
        /* JADX WARNING: Removed duplicated region for block: B:66:0x0100  */
        /* JADX WARNING: Removed duplicated region for block: B:74:0x011f  */
        /* JADX WARNING: Removed duplicated region for block: B:78:0x012b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean copyUpdateImage(java.lang.String r8, java.lang.String r9) {
            /*
                r7 = this;
                r0 = 0
                r3 = 0
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0001"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                boolean r1 = android.text.TextUtils.isEmpty(r8)
                if (r1 != 0) goto L_0x0015
                boolean r1 = android.text.TextUtils.isEmpty(r9)
                if (r1 == 0) goto L_0x001d
            L_0x0015:
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0009"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
            L_0x001c:
                return r0
            L_0x001d:
                java.lang.String r1 = "/cache"
                boolean r1 = r9.startsWith(r1)
                if (r1 != 0) goto L_0x002d
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0010"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                goto L_0x001c
            L_0x002d:
                java.io.File r1 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00e0, IOException -> 0x00ad, Exception -> 0x007a, all -> 0x0113 }
                r1.<init>(r8)     // Catch:{ FileNotFoundException -> 0x00e0, IOException -> 0x00ad, Exception -> 0x007a, all -> 0x0113 }
                java.io.File r2 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00e0, IOException -> 0x00ad, Exception -> 0x007a, all -> 0x0113 }
                r2.<init>(r9)     // Catch:{ FileNotFoundException -> 0x00e0, IOException -> 0x00ad, Exception -> 0x007a, all -> 0x0113 }
                java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x00e0, IOException -> 0x00ad, Exception -> 0x007a, all -> 0x0113 }
                r4.<init>(r1)     // Catch:{ FileNotFoundException -> 0x00e0, IOException -> 0x00ad, Exception -> 0x007a, all -> 0x0113 }
                java.nio.channels.FileChannel r1 = r4.getChannel()     // Catch:{ FileNotFoundException -> 0x00e0, IOException -> 0x00ad, Exception -> 0x007a, all -> 0x0113 }
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x015f, IOException -> 0x0167, Exception -> 0x0170, all -> 0x0156 }
                r4.<init>(r2)     // Catch:{ FileNotFoundException -> 0x015f, IOException -> 0x0167, Exception -> 0x0170, all -> 0x0156 }
                java.nio.channels.FileChannel r6 = r4.getChannel()     // Catch:{ FileNotFoundException -> 0x015f, IOException -> 0x0167, Exception -> 0x0170, all -> 0x0156 }
                r2 = 0
                long r4 = r1.size()     // Catch:{ FileNotFoundException -> 0x0163, IOException -> 0x016c, Exception -> 0x0175, all -> 0x015a }
                r1.transferTo(r2, r4, r6)     // Catch:{ FileNotFoundException -> 0x0163, IOException -> 0x016c, Exception -> 0x0175, all -> 0x015a }
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)
                if (r1 == 0) goto L_0x0065
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)
                r1.close()     // Catch:{ IOException -> 0x013e }
            L_0x0065:
                if (r6 == 0) goto L_0x0071
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                r6.close()     // Catch:{ IOException -> 0x0141 }
            L_0x0071:
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "copyUpdateImage 0008"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                r0 = 1
                goto L_0x001c
            L_0x007a:
                r1 = move-exception
                r2 = r1
                r6 = r3
                r4 = r3
            L_0x007e:
                java.lang.String r1 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0004"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m2e(r1, r3, r2)     // Catch:{ all -> 0x015d }
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                if (r4 == 0) goto L_0x0098
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r4.close()     // Catch:{ IOException -> 0x0144 }
            L_0x0098:
                if (r6 == 0) goto L_0x00a4
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r6.close()     // Catch:{ IOException -> 0x0147 }
            L_0x00a4:
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0008"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                goto L_0x001c
            L_0x00ad:
                r1 = move-exception
                r2 = r1
                r6 = r3
                r4 = r3
            L_0x00b1:
                java.lang.String r1 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0003"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m2e(r1, r3, r2)     // Catch:{ all -> 0x015d }
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                if (r4 == 0) goto L_0x00cb
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r4.close()     // Catch:{ IOException -> 0x014a }
            L_0x00cb:
                if (r6 == 0) goto L_0x00d7
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r6.close()     // Catch:{ IOException -> 0x014c }
            L_0x00d7:
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0008"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                goto L_0x001c
            L_0x00e0:
                r1 = move-exception
                r2 = r1
                r6 = r3
                r4 = r3
            L_0x00e4:
                java.lang.String r1 = "DchaService"
                java.lang.String r3 = "copyUpdateImage 0002"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m2e(r1, r3, r2)     // Catch:{ all -> 0x015d }
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                if (r4 == 0) goto L_0x00fe
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r4.close()     // Catch:{ IOException -> 0x014e }
            L_0x00fe:
                if (r6 == 0) goto L_0x010a
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r6.close()     // Catch:{ IOException -> 0x0150 }
            L_0x010a:
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0008"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                goto L_0x001c
            L_0x0113:
                r1 = move-exception
                r6 = r3
                r4 = r3
            L_0x0116:
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                if (r4 == 0) goto L_0x0129
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r4.close()     // Catch:{ IOException -> 0x0152 }
            L_0x0129:
                if (r6 == 0) goto L_0x0135
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                r6.close()     // Catch:{ IOException -> 0x0154 }
            L_0x0135:
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "copyUpdateImage 0008"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                goto L_0x001c
            L_0x013e:
                r0 = move-exception
                goto L_0x0065
            L_0x0141:
                r0 = move-exception
                goto L_0x0071
            L_0x0144:
                r1 = move-exception
                goto L_0x0098
            L_0x0147:
                r1 = move-exception
                goto L_0x00a4
            L_0x014a:
                r1 = move-exception
                goto L_0x00cb
            L_0x014c:
                r1 = move-exception
                goto L_0x00d7
            L_0x014e:
                r1 = move-exception
                goto L_0x00fe
            L_0x0150:
                r1 = move-exception
                goto L_0x010a
            L_0x0152:
                r1 = move-exception
                goto L_0x0129
            L_0x0154:
                r1 = move-exception
                goto L_0x0135
            L_0x0156:
                r2 = move-exception
                r6 = r3
                r4 = r1
                goto L_0x0116
            L_0x015a:
                r2 = move-exception
                r4 = r1
                goto L_0x0116
            L_0x015d:
                r1 = move-exception
                goto L_0x0116
            L_0x015f:
                r2 = move-exception
                r6 = r3
                r4 = r1
                goto L_0x00e4
            L_0x0163:
                r2 = move-exception
                r4 = r1
                goto L_0x00e4
            L_0x0167:
                r2 = move-exception
                r6 = r3
                r4 = r1
                goto L_0x00b1
            L_0x016c:
                r2 = move-exception
                r4 = r1
                goto L_0x00b1
            L_0x0170:
                r2 = move-exception
                r6 = r3
                r4 = r1
                goto L_0x007e
            L_0x0175:
                r2 = move-exception
                r4 = r1
                goto L_0x007e
            */
            throw new UnsupportedOperationException("Method not decompiled: p000jp.p001co.benesse.dcha.dchaservice.DchaService.C00001.copyUpdateImage(java.lang.String, java.lang.String):boolean");
        }

        public boolean deleteFile(String str) throws RemoteException {
            Log.m0d("DchaService", "deleteFile 0001");
            return false;
        }

        public void disableADB() {
            Log.m0d("DchaService", "disableADB 0001");
            Settings.Secure.putInt(this.this$0.getContentResolver(), "adb_enabled", 0);
        }

        public String getCanonicalExternalPath(String str) throws RemoteException {
            Log.m0d("DchaService", "getCanonicalExternalPath 0001");
            if (TextUtils.isEmpty(str)) {
                Log.m0d("DchaService", "getCanonicalExternalPath 0002");
            } else {
                try {
                    str = new File(str).getCanonicalPath();
                } catch (Exception e) {
                    Log.m2e("DchaService", "getCanonicalExternalPath 0003", e);
                }
                Log.m0d("DchaService", "getCanonicalExternalPath 0004 return: " + str);
            }
            return str;
        }

        public String getForegroundPackageName() throws RemoteException {
            Log.m0d("DchaService", "getForegroundPackageName 0001");
            try {
                List tasks = ActivityManagerNative.getDefault().getTasks(1, 0);
                String packageName = ((ActivityManager.RunningTaskInfo) tasks.get(0)).baseActivity.getPackageName();
                Log.m0d("DchaService", "Foreground package name :" + packageName);
                tasks.clear();
                Log.m0d("DchaService", "getForegroundPackageName 0003");
                return packageName;
            } catch (Exception e) {
                Log.m2e("DchaService", "getForegroundPackageName 0002", e);
                throw new RemoteException();
            }
        }

        public int getSetupStatus() {
            Log.m0d("DchaService", "getSetupStatus 0001");
            return PreferenceManager.getDefaultSharedPreferences(this.this$0).getInt("DigichalizedStatus", -1);
        }

        public int getUserCount() {
            Log.m0d("DchaService", "getUserCount 0001");
            int userCount = ((UserManager) this.this$0.getSystemService("user")).getUserCount();
            Log.m0d("DchaService", "getUserCount return: " + userCount);
            return userCount;
        }

        public void hideNavigationBar(boolean z) {
            Log.m0d("DchaService", "hideNavigationBar 0001");
            this.this$0.hideNavigationBar(z);
        }

        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            	at java.base/java.util.Objects.checkIndex(Objects.java:372)
            	at java.base/java.util.ArrayList.get(ArrayList.java:458)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        /* JADX WARNING: Can't fix incorrect switch cases order */
        public boolean installApp(java.lang.String r7, int r8) throws android.os.RemoteException {
            /*
                r6 = this;
                r0 = 1
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "installApp 0001"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
                jp.co.benesse.dcha.dchaservice.DchaService r1 = r6.this$0     // Catch:{ Exception -> 0x003f }
                android.content.pm.PackageManager r2 = r1.getPackageManager()     // Catch:{ Exception -> 0x003f }
                r1 = 64
                switch(r8) {
                    case 0: goto L_0x0037;
                    case 1: goto L_0x004d;
                    case 2: goto L_0x0057;
                    default: goto L_0x0013;
                }     // Catch:{ Exception -> 0x003f }
            L_0x0013:
                java.lang.String r3 = "DchaService"
                java.lang.String r4 = "installApp 0006"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r3, r4)     // Catch:{ Exception -> 0x003f }
            L_0x001a:
                jp.co.benesse.dcha.dchaservice.DchaService$1$PackageInstallObserver r3 = new jp.co.benesse.dcha.dchaservice.DchaService$1$PackageInstallObserver     // Catch:{ Exception -> 0x003f }
                r3.<init>(r6)     // Catch:{ Exception -> 0x003f }
                java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x003f }
                r4.<init>(r7)     // Catch:{ Exception -> 0x003f }
                android.net.Uri r4 = android.net.Uri.fromFile(r4)     // Catch:{ Exception -> 0x003f }
                r5 = 0
                r2.installPackage(r4, r3, r1, r5)     // Catch:{ Exception -> 0x003f }
                monitor-enter(r3)     // Catch:{ Exception -> 0x003f }
            L_0x002d:
                boolean r1 = r3.finished     // Catch:{ all -> 0x009d }
                if (r1 != 0) goto L_0x0061
                r3.wait()     // Catch:{ InterruptedException -> 0x0035 }
                goto L_0x002d
            L_0x0035:
                r1 = move-exception
                goto L_0x002d
            L_0x0037:
                java.lang.String r3 = "DchaService"
                java.lang.String r4 = "installApp 0003"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r3, r4)     // Catch:{ Exception -> 0x003f }
                goto L_0x001a
            L_0x003f:
                r0 = move-exception
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "installApp 0009"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m2e(r1, r2, r0)
                android.os.RemoteException r0 = new android.os.RemoteException
                r0.<init>()
                throw r0
            L_0x004d:
                java.lang.String r1 = "DchaService"
                java.lang.String r3 = "installApp 0004"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r3)     // Catch:{ Exception -> 0x003f }
                r1 = 66
                goto L_0x001a
            L_0x0057:
                java.lang.String r1 = "DchaService"
                java.lang.String r3 = "installApp 0005"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r3)     // Catch:{ Exception -> 0x003f }
                r1 = 194(0xc2, float:2.72E-43)
                goto L_0x001a
            L_0x0061:
                int r1 = r3.result     // Catch:{ all -> 0x009d }
                if (r1 != r0) goto L_0x006e
                monitor-exit(r3)     // Catch:{ Exception -> 0x003f }
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "installApp 0008"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)     // Catch:{ Exception -> 0x003f }
            L_0x006d:
                return r0
            L_0x006e:
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "installApp 0007"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ all -> 0x009d }
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x009d }
                r0.<init>()     // Catch:{ all -> 0x009d }
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "apk install failure:"
                java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x009d }
                int r2 = r3.result     // Catch:{ all -> 0x009d }
                java.lang.String r2 = r6.installFailureToString(r2)     // Catch:{ all -> 0x009d }
                java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x009d }
                java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x009d }
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m1e(r1, r0)     // Catch:{ all -> 0x009d }
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "installApp return: false"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ all -> 0x009d }
                monitor-exit(r3)     // Catch:{ Exception -> 0x003f }
                r0 = 0
                goto L_0x006d
            L_0x009d:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ Exception -> 0x003f }
                throw r0     // Catch:{ Exception -> 0x003f }
            */
            throw new UnsupportedOperationException("Method not decompiled: p000jp.p001co.benesse.dcha.dchaservice.DchaService.C00001.installApp(java.lang.String, int):boolean");
        }

        public boolean isDeviceEncryptionEnabled() {
            Log.m0d("DchaService", "isDeviceEncryptionEnabled 0001");
            boolean equalsIgnoreCase = "encrypted".equalsIgnoreCase(SystemProperties.get("ro.crypto.state", "unsupported"));
            Log.m0d("DchaService", "isDeviceEncryptionEnabled return: " + equalsIgnoreCase);
            return equalsIgnoreCase;
        }

        public void rebootPad(int i, String str) throws RemoteException {
            Log.m0d("DchaService", "rebootPad 0001");
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
                    case 2:
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
                Log.m2e("DchaService", "rebootPad 0006", e);
                throw new RemoteException();
            }
        }

        public void removeTask(String str) throws RemoteException {
            Log.m0d("DchaService", "removeTask 0001");
            try {
                ActivityManager activityManager = (ActivityManager) this.this$0.getSystemService("activity");
                List<ActivityManager.RecentTaskInfo> recentTasksForUser = activityManager.getRecentTasksForUser(30, 1, UserHandle.myUserId());
                if (str != null) {
                    Log.m0d("DchaService", "removeTask 0002");
                    for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasksForUser) {
                        Log.m0d("DchaService", "removeTask " + recentTaskInfo.baseIntent.getComponent().getPackageName());
                        if (str.equals(recentTaskInfo.baseIntent.getComponent().getPackageName())) {
                            Log.m0d("DchaService", "removeTask 0003");
                            activityManager.removeTask(recentTaskInfo.persistentId);
                        }
                    }
                } else {
                    Log.m0d("DchaService", "removeTask 0004");
                    for (ActivityManager.RecentTaskInfo recentTaskInfo2 : recentTasksForUser) {
                        activityManager.removeTask(recentTaskInfo2.persistentId);
                    }
                }
                Log.m0d("DchaService", "removeTask 0006");
            } catch (Exception e) {
                Log.m2e("DchaService", "removeTask 0005", e);
                throw new RemoteException();
            }
        }

        public void sdUnmount() throws RemoteException {
            Log.m0d("DchaService", "sdUnmount 0001");
            String str = System.getenv("SECONDARY_STORAGE");
            try {
                IMountService.Stub.asInterface(ServiceManager.getService("mount")).unmountVolume(getCanonicalExternalPath(str), true, false);
                Log.m0d("DchaService", "sdUnmount 0003");
            } catch (RemoteException e) {
                Log.m2e("DchaService", "sdUnmount 0002", e);
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
                this.this$0.setInitialSettingsDevelopmentOptions();
                Log.m0d("DchaService", "setDefaultParam 0003");
            } catch (RemoteException e) {
                Log.m2e("DchaService", "setDefaultParam 0002", e);
                throw new RemoteException();
            }
        }

        public void setDefaultPreferredHomeApp(String str) throws RemoteException {
            try {
                Log.m0d("DchaService", "setDefalutPreferredHomeApp 0001");
                Log.m0d("DchaService", "setDefalutPreferredHomeApp packageName:" + str);
                PackageManager packageManager = this.this$0.getPackageManager();
                ComponentName componentName = null;
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.MAIN");
                intentFilter.addCategory("android.intent.category.HOME");
                intentFilter.addCategory("android.intent.category.DEFAULT");
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.addCategory("android.intent.category.DEFAULT");
                List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
                ArrayList arrayList = new ArrayList();
                for (ResolveInfo resolveInfo : queryIntentActivities) {
                    String str2 = resolveInfo.activityInfo.applicationInfo.packageName;
                    String str3 = resolveInfo.activityInfo.name;
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
                Log.m2e("DchaService", "setDefalutPrefferredHomeApp 0004", e);
                throw new RemoteException(e.toString());
            }
        }

        public void setPermissionEnforced(boolean z) throws RemoteException {
            Log.m0d("DchaService", "setPermissionEnforced 0001");
            ActivityThread.getPackageManager().setPermissionEnforced("android.permission.READ_EXTERNAL_STORAGE", z);
            Log.m0d("DchaService", "setPermissionEnforced 0002");
        }

        public void setSetupStatus(int i) {
            Log.m0d("DchaService", "setSetupStatus 0001");
            PreferenceManager.getDefaultSharedPreferences(this.this$0).edit().putInt("DigichalizedStatus", i).commit();
            Settings.System.putInt(this.this$0.getContentResolver(), "dcha_state", i);
        }

        public void setSystemTime(String str, String str2) {
            Log.m0d("DchaService", "setSystemTime 0001");
            try {
                Date parse = new SimpleDateFormat(str2, Locale.JAPAN).parse(str);
                String packageNameFromPid = this.this$0.getPackageNameFromPid(Binder.getCallingPid());
                Calendar instance = Calendar.getInstance(Locale.JAPAN);
                instance.set(2016, 1, 1, 0, 0);
                Calendar instance2 = Calendar.getInstance(Locale.JAPAN);
                instance2.setTime(parse);
                if (instance.compareTo(instance2) > 0) {
                    Log.m0d("DchaService", "setSystemTime 0002");
                    EmergencyLog.write(this.this$0, "ELK008", str + " " + packageNameFromPid);
                } else {
                    Log.m0d("DchaService", "setSystemTime 0003");
                    SystemClock.setCurrentTimeMillis(parse.getTime());
                }
                Log.m0d("DchaService", "setSystemTime set time :" + parse);
            } catch (Exception e) {
                Log.m2e("DchaService", "setSystemTime 0004", e);
            }
            Log.m0d("DchaService", "setSystemTime 0005");
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean uninstallApp(java.lang.String r6, int r7) throws java.lang.RuntimeException {
            /*
                r5 = this;
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "uninstallApp 0001"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
                jp.co.benesse.dcha.dchaservice.DchaService r0 = r5.this$0     // Catch:{ Exception -> 0x0063 }
                int r1 = getCallingPid()     // Catch:{ Exception -> 0x0063 }
                java.lang.String r0 = r0.getPackageNameFromPid(r1)     // Catch:{ Exception -> 0x0063 }
                jp.co.benesse.dcha.dchaservice.DchaService r1 = r5.this$0     // Catch:{ Exception -> 0x0063 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0063 }
                r2.<init>()     // Catch:{ Exception -> 0x0063 }
                java.lang.String r3 = "ELK002"
                java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ Exception -> 0x0063 }
                java.lang.String r4 = " "
                java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x0063 }
                java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Exception -> 0x0063 }
                java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0063 }
                jp.co.benesse.dcha.dchaservice.EmergencyLog.write(r1, r3, r0)     // Catch:{ Exception -> 0x0063 }
                jp.co.benesse.dcha.dchaservice.DchaService r0 = r5.this$0     // Catch:{ Exception -> 0x0063 }
                android.content.pm.PackageManager r1 = r0.getPackageManager()     // Catch:{ Exception -> 0x0063 }
                r0 = 2
                switch(r7) {
                    case 1: goto L_0x004c;
                    default: goto L_0x0039;
                }     // Catch:{ Exception -> 0x0063 }
            L_0x0039:
                jp.co.benesse.dcha.dchaservice.DchaService$1$PackageDeleteObserver r2 = new jp.co.benesse.dcha.dchaservice.DchaService$1$PackageDeleteObserver     // Catch:{ Exception -> 0x0063 }
                r2.<init>(r5)     // Catch:{ Exception -> 0x0063 }
                r1.deletePackage(r6, r2, r0)     // Catch:{ Exception -> 0x0063 }
                monitor-enter(r2)     // Catch:{ Exception -> 0x0063 }
            L_0x0042:
                boolean r0 = r2.finished     // Catch:{ all -> 0x0060 }
                if (r0 != 0) goto L_0x0055
                r2.wait()     // Catch:{ InterruptedException -> 0x004a }
                goto L_0x0042
            L_0x004a:
                r0 = move-exception
                goto L_0x0042
            L_0x004c:
                java.lang.String r0 = "DchaService"
                java.lang.String r2 = "uninstallApp 0002"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r2)     // Catch:{ Exception -> 0x0063 }
                r0 = 3
                goto L_0x0039
            L_0x0055:
                monitor-exit(r2)     // Catch:{ Exception -> 0x0063 }
                java.lang.String r0 = "DchaService"
                java.lang.String r1 = "uninstallApp 0003"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x0063 }
                boolean r0 = r2.result     // Catch:{ Exception -> 0x0063 }
                return r0
            L_0x0060:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ Exception -> 0x0063 }
                throw r0     // Catch:{ Exception -> 0x0063 }
            L_0x0063:
                r0 = move-exception
                java.lang.String r1 = "DchaService"
                java.lang.String r2 = "uninstallApp 0004"
                p000jp.p001co.benesse.dcha.dchaservice.util.Log.m2e(r1, r2, r0)
                java.lang.RuntimeException r0 = new java.lang.RuntimeException
                r0.<init>()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: p000jp.p001co.benesse.dcha.dchaservice.DchaService.C00001.uninstallApp(java.lang.String, int):boolean");
        }

        public boolean verifyUpdateImage(String str) {
            Log.m0d("DchaService", "verifyUpdateImage 0001");
            boolean z = false;
            try {
                RecoverySystem.verifyPackage(new File(str), (RecoverySystem.ProgressListener) null, (File) null);
                z = true;
            } catch (IOException e) {
                Log.m2e("DchaService", "verifyUpdateImege IO Exception", e);
            } catch (GeneralSecurityException e2) {
                Log.m2e("DchaService", "verifyUpdateImege GeneralSecurityException", e2);
            } catch (Exception e3) {
                Log.m2e("DchaService", "verifyUpdateImege Exception", e3);
            }
            Log.m0d("DchaService", "verifyUpdateImage 0002");
            return z;
        }
    };
    protected String mDebugApp;
    protected boolean mDontPokeProperties;
    protected boolean mLastEnabledState;
    protected ListPreference mTransitionAnimationScale;
    protected ListPreference mWindowAnimationScale;
    protected IWindowManager mWindowManager;

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
                        Log.m2e("SystemPropPoker", "doInBackground 0004", e);
                    } catch (Exception e2) {
                        Log.m3v("DevSettings", "Somone wrote a bad service '" + str + "' that doesn't like to be poked: " + e2);
                        Log.m2e("SystemPropPoker", "doInBackground 0005", e2);
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
        if (sSystemSignature[0] != null) {
            return sSystemSignature[0].equals(getFirstSignature(packageInfo));
        }
        return false;
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
                intent.setAction("jp.co.benesse.dcha.databox.intent.action.COMMAND");
                intent.addCategory("jp.co.benesse.dcha.databox.intent.category.WIPE");
                intent.putExtra("send_service", "DchaService");
                sendBroadcastAsUser(intent, UserHandle.ALL);
                Log.m0d("DchaService", "doCancelDigichalized send wipeDataBoxIntent intent");
                HandlerThread handlerThread = new HandlerThread("handlerThread");
                handlerThread.start();
                new Handler(handlerThread.getLooper()).post(new Runnable(this) {
                    final DchaService this$0;

                    {
                        this.this$0 = r1;
                    }

                    public void run() {
                        String str;
                        String str2;
                        if (!DchaService.doCancelDigichalizedFlg) {
                            Log.m0d("DchaService", "doCancelDigichalized 0003");
                            try {
                                Log.m0d("DchaService", "start uninstallApp");
                                boolean unused = DchaService.doCancelDigichalizedFlg = true;
                                for (ApplicationInfo applicationInfo : this.this$0.getPackageManager().getInstalledApplications(128)) {
                                    if ((applicationInfo.flags & 1) == 1) {
                                        Log.m0d("DchaService", "doCancelDigichalized 0004");
                                    } else {
                                        this.this$0.mDchaServiceStub.uninstallApp(applicationInfo.packageName, 0);
                                    }
                                }
                                this.this$0.mDchaServiceStub.setSetupStatus(0);
                                Log.m0d("DchaService", "end uninstallApp");
                            } catch (RemoteException e) {
                                Log.m2e("DchaService", "doCancelDigichalized 0005", e);
                            } finally {
                                str = "DchaService";
                                str2 = "doCancelDigichalized 0006";
                                Log.m0d(str, str2);
                                boolean unused2 = DchaService.doCancelDigichalizedFlg = false;
                            }
                        }
                        Log.m0d("DchaService", "doCancelDigichalized 0007");
                    }
                });
            }
            Log.m0d("DchaService", "doCancelDigichalized 0010");
        } catch (Exception e) {
            Log.m2e("DchaService", "doCancelDigichalized 0009", e);
            throw new RemoteException();
        }
    }

    /* access modifiers changed from: protected */
    public String getPackageNameFromPid(int i) {
        Log.m0d("DchaService", "getPackageNameFromPid 0001");
        String str = "Unknown";
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
            if (i == runningAppProcessInfo.pid) {
                Log.m0d("DchaService", "getPackageNameFromPid 0002");
                str = runningAppProcessInfo.processName;
            }
        }
        Log.m0d("DchaService", "getPackageNameFromPid 0003");
        return str;
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
    public int onStartCommand(android.content.Intent r5, int r6, int r7) {
        /*
            r4 = this;
            r2 = 0
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0001"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
            if (r5 == 0) goto L_0x0039
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0002"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
            java.lang.String r0 = "REQ_COMMAND"
            int r0 = r5.getIntExtra(r0, r2)
            java.lang.String r1 = "DchaService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "onStartCommand intent command:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r1, r2)
            switch(r0) {
                case 1: goto L_0x0045;
                case 2: goto L_0x005d;
                case 3: goto L_0x0069;
                default: goto L_0x0032;
            }
        L_0x0032:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0006"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x0054 }
        L_0x0039:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0008"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)
            int r0 = super.onStartCommand(r5, r6, r7)
            return r0
        L_0x0045:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0003"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x0054 }
            r0 = 0
            r4.hideNavigationBar(r0)     // Catch:{ Exception -> 0x0054 }
            r4.doCancelDigichalized()     // Catch:{ Exception -> 0x0054 }
            goto L_0x0039
        L_0x0054:
            r0 = move-exception
            java.lang.String r1 = "DchaService"
            java.lang.String r2 = "onStartCommand 0007"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m2e(r1, r2, r0)
            goto L_0x0039
        L_0x005d:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0004"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x0054 }
            r0 = 0
            r4.hideNavigationBar(r0)     // Catch:{ Exception -> 0x0054 }
            goto L_0x0039
        L_0x0069:
            java.lang.String r0 = "DchaService"
            java.lang.String r1 = "onStartCommand 0005"
            p000jp.p001co.benesse.dcha.dchaservice.util.Log.m0d(r0, r1)     // Catch:{ Exception -> 0x0054 }
            r0 = 1
            r4.hideNavigationBar(r0)     // Catch:{ Exception -> 0x0054 }
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: p000jp.p001co.benesse.dcha.dchaservice.DchaService.onStartCommand(android.content.Intent, int, int):int");
    }

    /* access modifiers changed from: package-private */
    public void pokeSystemProperties() {
        Log.m0d("DchaService", "pokeSystemProperties 0001");
        if (!this.mDontPokeProperties) {
            Log.m0d("DchaService", "pokeSystemProperties 0002");
            new SystemPropPoker().execute(new Void[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsAccount() {
        Log.m0d("DchaService", "setInitialSettingsAccount start");
        LocalePicker.updateLocale(new Locale("ja", "JP"));
        ((TextServicesManager) getSystemService("textservices")).setSpellCheckerEnabled(true);
        Log.m0d("DchaService", "setInitialSettingsAccount 0002");
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsDevelopmentOptions() throws RemoteException {
        Log.m0d("DchaService", "setInitialSettingDevelopmentOptions start");
        this.mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        this.mDontPokeProperties = true;
        resetDebuggerOptions();
        Settings.Global.putInt(getContentResolver(), "development_settings_enabled", 0);
        Settings.Global.putInt(getContentResolver(), "stay_on_while_plugged_in", 0);
        Settings.Secure.putInt(getContentResolver(), "bluetooth_hci_log", 0);
        Settings.Global.putInt(getContentResolver(), "adb_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "bugreport_in_power_menu", 0);
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
        Settings.Global.putInt(getContentResolver(), "wait_for_debugger", 0);
        Settings.Global.putInt(getContentResolver(), "verifier_verify_adb_installs", 0);
        Settings.System.putInt(getContentResolver(), "screen_capture_on", 0);
        SystemProperties.set("persist.logd.size", "256K");
        Settings.System.putInt(getContentResolver(), "show_touches", 0);
        Settings.System.putInt(getContentResolver(), "pointer_location", 0);
        writeShowUpdatesOption();
        writeDebugLayoutOptions();
        writeDebuggerOptions();
        Settings.Global.putInt(getContentResolver(), "debug.force_rtl", 0);
        SystemProperties.set("debug.force_rtl", "0");
        writeAnimationScaleOption(0, this.mWindowAnimationScale, (Object) null);
        writeAnimationScaleOption(1, this.mTransitionAnimationScale, (Object) null);
        writeAnimationScaleOption(2, this.mAnimatorDurationScale, (Object) null);
        writeOverlayDisplayDevicesOptions((Object) null);
        writeHardwareUiOptions();
        writeShowHwScreenUpdatesOptions();
        writeShowHwLayersUpdatesOptions();
        writeShowHwOverdrawOptions();
        SystemProperties.set("debug.hwui.show_non_rect_clip", "0");
        writeMsaaOptions();
        writeDisableOverlaysOption();
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", 0);
        SystemProperties.set("persist.sys.media.use-awesome", "false");
        Settings.Secure.putInt(getContentResolver(), "usb_audio_automatic_routing_disabled", 0);
        writeStrictModeVisualOptions();
        writeCpuUsageOptions();
        writeTrackFrameTimeOptions();
        SystemProperties.set("debug.egl.trace", "");
        writeImmediatelyDestroyActivitiesOptions();
        writeAppProcessLimitOptions((Object) null);
        Settings.Secure.putInt(getContentResolver(), "anr_show_background", 0);
        this.mDontPokeProperties = false;
        pokeSystemProperties();
        Settings.Global.putInt(getContentResolver(), "development_settings_enabled", 0);
        this.mLastEnabledState = Settings.Global.getInt(getContentResolver(), "development_settings_enabled", 0) != 0;
        if (this.mLastEnabledState) {
            Log.m0d("DchaService", "setInitialSettingDevelopmentOptions 0002");
            writeShowUpdatesOption();
            Settings.Global.putInt(getContentResolver(), "development_settings_enabled", 0);
        }
        Log.m0d("DchaService", "setInitialSettingDevelopmentOptions 0003");
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
        Settings.Secure.putInt(getContentResolver(), "high_text_contrast_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "speak_password", 0);
        Settings.Global.putInt(getContentResolver(), "enable_accessibility_global_gesture_enabled", 0);
        Settings.Secure.putString(getContentResolver(), "long_press_timeout", "500");
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_inversion_enabled", 0);
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", 0);
        Log.m0d("DchaService", "setInitialSettingsSystem 0003");
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsTerminal() throws RemoteException {
        Log.m0d("DchaService", "setInitialSettingsTerminal start");
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
            AppOpsManager appOpsManager = (AppOpsManager) applicationContext.getSystemService("appops");
            try {
                IPackageManager.Stub.asInterface(ServiceManager.getService("package")).resetApplicationPreferences(UserHandle.myUserId());
            } catch (RemoteException e) {
            }
            try {
                appOpsManager.resetAllModes();
            } catch (RemoteException e2) {
                Log.m2e("DchaService", "setInitialSettingsTerminal 0005", e2);
                throw e2;
            }
        }
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(512);
        for (int i = 0; i < installedApplications.size(); i++) {
            ApplicationInfo applicationInfo = installedApplications.get(i);
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, 64);
                if (!isSystemPackage(packageManager, packageInfo)) {
                    Log.m0d("DchaService", "setInitialSettingsTerminal 0002:" + applicationInfo.packageName);
                    asInterface.setNotificationsEnabledForPackage(applicationInfo.packageName, applicationInfo.uid, false);
                    asInterface.setImportance(applicationInfo.packageName, packageInfo.applicationInfo.uid, -1000);
                }
            } catch (Exception e3) {
            }
            if (!applicationInfo.enabled) {
                Log.m0d("DchaService", "setInitialSettingsTerminal 0003");
                if (packageManager.getApplicationEnabledSetting(applicationInfo.packageName) == 3) {
                    Log.m0d("DchaService", "setInitialSettingsTerminal 0004:" + applicationInfo.packageName);
                    packageManager.setApplicationEnabledSetting(applicationInfo.packageName, 0, 1);
                }
            }
            try {
                PackageInfo packageInfo2 = packageManager.getPackageInfo(applicationInfo.packageName, 4096);
                if (!isSystemPackage(packageManager, packageInfo2)) {
                    for (String str : packageInfo2.requestedPermissions) {
                        if ((applicationContext.getPackageManager().getPermissionFlags(str, packageInfo2.packageName, Process.myUserHandle()) & 16) == 0 && "android.permission.READ_EXTERNAL_STORAGE".equals(str)) {
                            packageManager.grantRuntimePermission(packageInfo2.packageName, str, Process.myUserHandle());
                        }
                    }
                }
            } catch (Exception e4) {
            }
        }
        AppOpsManager appOpsManager2 = (AppOpsManager) applicationContext.getSystemService("appops");
        for (ApplicationInfo applicationInfo2 : packageManager.getInstalledApplications(128)) {
            packageManager.updateIntentVerificationStatusAsUser(applicationInfo2.packageName, 4, UserHandle.myUserId());
            appOpsManager2.setMode(24, applicationInfo2.uid, applicationInfo2.packageName, 0);
            appOpsManager2.setMode(23, applicationInfo2.uid, applicationInfo2.packageName, 0);
        }
        NetworkPolicyManager from = NetworkPolicyManager.from(applicationContext);
        int[] uidsWithPolicy = from.getUidsWithPolicy(1);
        int currentUser = ActivityManager.getCurrentUser();
        for (int i2 : uidsWithPolicy) {
            if (UserHandle.getUserId(i2) == currentUser) {
                from.setUidPolicy(i2, 0);
            }
        }
        RingtoneManager.setActualDefaultRingtoneUri(applicationContext, 2, (Uri) null);
        RingtoneManager.setActualDefaultRingtoneUri(applicationContext, 1, (Uri) null);
        RingtoneManager.setActualDefaultRingtoneUri(applicationContext, 4, (Uri) null);
        ((AudioManager) getSystemService("audio")).loadSoundEffects();
        Settings.System.putInt(getContentResolver(), "sound_effects_enabled", 1);
        ((UsbManager) getSystemService("usb")).setCurrentFunction("ptp");
        ((PowerManager) getSystemService("power")).setPowerSaveMode(false);
        Settings.Global.putInt(getContentResolver(), "low_power_trigger_level", 0);
        Log.m0d("DchaService", "setInitialSettingsTerminal 0006");
    }

    /* access modifiers changed from: protected */
    public void setInitialSettingsUser() {
        Log.m0d("DchaService", "setInitialSettingsUser start");
        Settings.Secure.putInt(getContentResolver(), "location_mode", 0);
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this);
        int callingUserId = UserHandle.getCallingUserId();
        lockPatternUtils.clearLock(callingUserId);
        lockPatternUtils.setLockScreenDisabled(true, callingUserId);
        Settings.System.putInt(getContentResolver(), "show_password", 1);
        Settings.Secure.putInt(getContentResolver(), "install_non_market_apps", 0);
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
            wifiManager.enableAggressiveHandover(0);
            wifiManager.setAllowScansWithTraffic(0);
        }
        Settings.Secure.putInt(getContentResolver(), "wifi_networks_available_notification_on", 0);
        Settings.Global.putInt(getContentResolver(), "wifi_scan_always_enabled", 0);
        Settings.Global.putInt(getContentResolver(), "wifi_sleep_policy", 0);
        Settings.Global.putInt(getContentResolver(), "wifi_display_on", 0);
        Settings.Global.putInt(getContentResolver(), "wifi_display_certification_on", 0);
        Settings.Global.putInt(getContentResolver(), "bluetooth_on", 0);
        BluetoothAdapter.getDefaultAdapter().disable();
        Settings.Global.putInt(getContentResolver(), "airplane_mode_on", 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.putExtra("state", false);
        sendBroadcast(intent);
        Log.m0d("DchaService", "setInitialSettingsWirelessNetwork 0003");
    }

    /* access modifiers changed from: protected */
    public void writeAnimationScaleOption(int i, ListPreference listPreference, Object obj) {
        float f;
        Log.m0d("DchaService", "writeAnimationScaleOption 0001");
        if (obj != null) {
            try {
                f = Float.parseFloat(obj.toString());
            } catch (RemoteException e) {
                return;
            }
        } else {
            f = 1.0f;
        }
        this.mWindowManager.setAnimationScale(i, f);
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
    public void writeDebugLayoutOptions() {
        Log.m0d("DchaService", "writeDebugLayoutOptions 0001");
        SystemProperties.set("debug.layout", "false");
        pokeSystemProperties();
    }

    /* access modifiers changed from: protected */
    public void writeDebuggerOptions() {
        Log.m0d("DchaService", "writeDebuggerOptions 0001");
        try {
            this.mDebugApp = Settings.Global.getString(getContentResolver(), "debug_app");
            ActivityManagerNative.getDefault().setDebugApp(this.mDebugApp, false, true);
        } catch (RemoteException e) {
        }
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
    public void writeHardwareUiOptions() {
        Log.m0d("DchaService", "writeHardwareUiOptions 0001");
        SystemProperties.set("persist.sys.ui.hw", "false");
        pokeSystemProperties();
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
    public void writeMsaaOptions() {
        Log.m0d("DchaService", "writeMsaaOptions 0001");
        SystemProperties.set("debug.egl.force_msaa", "false");
        pokeSystemProperties();
    }

    /* access modifiers changed from: protected */
    public void writeOverlayDisplayDevicesOptions(Object obj) {
        Log.m0d("DchaService", "writeOverlayDisplayDevicesOptions 0001");
        Settings.Global.putString(getContentResolver(), "overlay_display_devices", (String) obj);
    }

    /* access modifiers changed from: protected */
    public void writeShowHwLayersUpdatesOptions() {
        Log.m0d("DchaService", "writeShowHwLayersUpdatesOptions 0001");
        SystemProperties.set("debug.hwui.show_layers_updates", (String) null);
        pokeSystemProperties();
    }

    /* access modifiers changed from: protected */
    public void writeShowHwOverdrawOptions() {
        Log.m0d("DchaService", "writeShowHwOverdrawOptions 0001");
        SystemProperties.set("debug.hwui.overdraw", "0");
        pokeSystemProperties();
    }

    /* access modifiers changed from: protected */
    public void writeShowHwScreenUpdatesOptions() {
        Log.m0d("DchaService", "writeShowHwScreenUpdatesOptions 0001");
        SystemProperties.set("debug.hwui.show_dirty_regions", (String) null);
        pokeSystemProperties();
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
            this.mWindowManager.setStrictModeVisualIndicatorPreference("");
        } catch (RemoteException e) {
        }
    }

    /* access modifiers changed from: protected */
    public void writeTrackFrameTimeOptions() {
        Log.m0d("DchaService", "writeTrackFrameTimeOptions 0001");
        SystemProperties.set("debug.hwui.profile", "false");
        pokeSystemProperties();
    }
}
