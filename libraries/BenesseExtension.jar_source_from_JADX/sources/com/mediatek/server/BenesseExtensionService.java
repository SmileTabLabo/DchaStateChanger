package com.mediatek.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Point;
import android.hardware.usb.UsbManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.p000os.BenesseExtension;
import android.p000os.IBenesseExtensionService;
import android.provider.Settings;
import android.util.Log;
import android.view.IWindowManager;
import com.android.internal.app.ColorDisplayController;
import java.io.File;

public class BenesseExtensionService extends IBenesseExtensionService.Stub {
    static final String ACTION_DT_FW_UPDATED = "com.panasonic.sanyo.ts.intent.action.DIGITIZER_FIRMWARE_UPDATED";
    static final String ACTION_TP_FW_UPDATED = "com.panasonic.sanyo.ts.intent.action.TOUCHPANEL_FIRMWARE_UPDATED";
    static final String BC_COMPATSCREEN = "bc:compatscreen";
    static final String BC_DT_FW_UPDATE = "bc:digitizer:fw_update";
    static final String BC_DT_FW_VERSION = "bc:digitizer:fw_version";
    static final String BC_MAC_ADDRESS = "bc:mac_address";
    static final String BC_NIGHTCOLOR_CURRENT = "bc:nightcolor:current";
    static final String BC_NIGHTCOLOR_MAX = "bc:nightcolor:max";
    static final String BC_NIGHTCOLOR_MIN = "bc:nightcolor:min";
    static final String BC_NIGHTMODE_ACTIVE = "bc:nightmode:active";
    static final String BC_PASSWORD_HIT_FLAG = "bc_password_hit";
    static final String BC_SERIAL_NO = "bc:serial_no";
    static final String BC_TP_FW_UPDATE = "bc:touchpanel:fw_update";
    static final String BC_TP_FW_VERSION = "bc:touchpanel:fw_version";
    static final String DCHA_HASH_FILEPATH = "/factory/dcha_hash";
    static final String DCHA_STATE = "dcha_state";
    private static final byte[] DEFAULT_HASH = "a1e3cf8aa7858a458972592ebb9438e967da30d196bd6191cc77606cc60af183".getBytes();
    static final String EXTRA_RESULT = "result";
    static final String HASH_ALGORITHM = "SHA-256";
    static final String JAPAN_LOCALE = "ja-JP";
    static final String PACKAGE_NAME_BROWSER = "com.android.browser";
    static final String PACKAGE_NAME_QSB = "com.android.quicksearchbox";
    static final String PACKAGE_NAME_TRACEUR = "com.android.traceur";
    static final String PROPERTY_DCHA_STATE = "persist.sys.bc.dcha_state";
    static final String PROPERTY_LOCALE = "persist.sys.locale";
    static final File SYSFILE_DT_VERSION = new File("/sys/devices/platform/soc/11009000.i2c/i2c-2/2-0009/digi_fwver");
    static final File SYSFILE_TP_VERSION = new File("/sys/devices/platform/soc/11007000.i2c/i2c-0/0-000a/tp_fwver");
    static final String TAG = "BenesseExtensionService";
    static final int[][] mTable = {new int[]{240, 1920, 1200}, new int[]{160, 1024, 768}, new int[]{160, 1280, 800}};
    private final byte[] HEX_TABLE = "0123456789abcdef".getBytes();
    private ContentObserver mAdbObserver = new ContentObserver(this, this.mHandler) {
        final BenesseExtensionService this$0;

        {
            this.this$0 = r1;
        }

        public void onChange(boolean z) {
            synchronized (this.this$0.mLock) {
                Log.i(BenesseExtensionService.TAG, "getADBENABLE=" + this.this$0.getAdbEnabled());
                if (!this.this$0.changeAdbEnable()) {
                    this.this$0.updateBrowserEnabled();
                }
            }
        }
    };
    private ColorDisplayController mColorDisplayController;
    private Context mContext;
    private ContentObserver mDchaStateObserver = new ContentObserver(this, this.mHandler) {
        final BenesseExtensionService this$0;

        {
            this.this$0 = r1;
        }

        public void onChange(boolean z) {
            synchronized (this.this$0.mLock) {
                SystemProperties.set(BenesseExtensionService.PROPERTY_DCHA_STATE, String.valueOf(this.this$0.getDchaStateInternal()));
                this.this$0.changeSafemodeRestriction(this.this$0.getDchaStateInternal());
                this.this$0.updateBrowserEnabled();
                this.this$0.changeDefaultUsbFunction(this.this$0.getDchaStateInternal());
                this.this$0.changeDisallowInstallUnknownSource(this.this$0.getDchaCompletedPast());
                this.this$0.updateTraceurEnabled();
            }
        }
    };
    private Handler mHandler;
    private boolean mIsUpdating = false;
    private BroadcastReceiver mLanguageReceiver = new BroadcastReceiver(this) {
        final BenesseExtensionService this$0;

        {
            this.this$0 = r1;
        }

        public void onReceive(Context context, Intent intent) {
            synchronized (this.this$0.mLock) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    this.this$0.updateBrowserEnabled();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public Object mLock;
    private IWindowManager mWindowManager;

    private class UpdateParams {
        public String broadcast;
        public String[] cmd;
        final BenesseExtensionService this$0;

        private UpdateParams(BenesseExtensionService benesseExtensionService) {
            this.this$0 = benesseExtensionService;
        }
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    BenesseExtensionService(Context context) {
        this.mContext = context;
        this.mLock = new Object();
        this.mHandler = new Handler(true);
        synchronized (this.mLock) {
            this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(DCHA_STATE), false, this.mDchaStateObserver, -1);
            this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("adb_enabled"), false, this.mAdbObserver, -1);
            this.mContext.registerReceiver(this.mLanguageReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
            changeSafemodeRestriction(getDchaStateInternal());
            updateBrowserEnabled();
            changeDefaultUsbFunction(getDchaStateInternal());
            changeDisallowInstallUnknownSource(getDchaCompletedPast());
            updateTraceurEnabled();
        }
        this.mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.checkService("window"));
        this.mColorDisplayController = new ColorDisplayController(context);
    }

    /* access modifiers changed from: private */
    public boolean changeAdbEnable() {
        if (getAdbEnabled() == 0 || BenesseExtension.getDchaState() == 3 || !getDchaCompletedPast() || getInt(BC_PASSWORD_HIT_FLAG) != 0) {
            return false;
        }
        Settings.Global.putInt(this.mContext.getContentResolver(), "adb_enabled", 0);
        return true;
    }

    /* access modifiers changed from: private */
    public void changeDefaultUsbFunction(int i) {
        if (i > 0) {
            ((UsbManager) this.mContext.getSystemService(UsbManager.class)).setScreenUnlockedFunctions(0);
        }
    }

    /* access modifiers changed from: private */
    public void changeDisallowInstallUnknownSource(boolean z) {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager != null) {
            userManager.setUserRestriction("no_install_unknown_sources", z, UserHandle.SYSTEM);
        }
    }

    /* access modifiers changed from: private */
    public void changeSafemodeRestriction(int i) {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager != null) {
            userManager.setUserRestriction("no_safe_boot", i > 0, UserHandle.SYSTEM);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        $closeResource(r1, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0032, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        $closeResource((java.lang.Throwable) null, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        $closeResource((java.lang.Throwable) null, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00ac, code lost:
        if (r2.charAt(7) != '0') goto L_0x00b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00b6, code lost:
        if (r2.charAt(8) == '1') goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00b8, code lost:
        android.util.Log.e(TAG, "----- last line is not end of file! -----");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00c2, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00c3, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean checkHexFile(java.lang.String r12) {
        /*
            r11 = this;
            r0 = 1
            r3 = 0
            r1 = 0
            java.io.FileReader r6 = new java.io.FileReader     // Catch:{ Throwable -> 0x0033 }
            r6.<init>(r12)     // Catch:{ Throwable -> 0x0033 }
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x002c }
            r7.<init>(r6)     // Catch:{ Throwable -> 0x002c }
            r2 = r1
        L_0x000e:
            java.lang.String r5 = r7.readLine()     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            if (r5 == 0) goto L_0x009d
            r4 = 0
            char r4 = r5.charAt(r4)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            r8 = 59
            if (r4 != r8) goto L_0x003d
            java.lang.String r4 = "BenesseExtensionService"
            java.lang.String r5 = "----- found comment line. -----"
            android.util.Log.w(r4, r5)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            goto L_0x000e
        L_0x0025:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0027 }
        L_0x0027:
            r2 = move-exception
        L_0x0028:
            $closeResource(r0, r7)     // Catch:{ Throwable -> 0x002c }
            throw r2     // Catch:{ Throwable -> 0x002c }
        L_0x002c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002e }
        L_0x002e:
            r0 = move-exception
            $closeResource(r1, r6)     // Catch:{ Throwable -> 0x0033 }
            throw r0     // Catch:{ Throwable -> 0x0033 }
        L_0x0033:
            r0 = move-exception
            java.lang.String r1 = "BenesseExtensionService"
            java.lang.String r2 = "----- Exception occurred!!! -----"
            android.util.Log.e(r1, r2, r0)
            r0 = r3
        L_0x003c:
            return r0
        L_0x003d:
            java.lang.String r2 = ":[a-fA-F0-9]+"
            boolean r2 = r5.matches(r2)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            if (r2 == 0) goto L_0x004d
            int r2 = r5.length()     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            int r2 = r2 % 2
            if (r2 != 0) goto L_0x005e
        L_0x004d:
            java.lang.String r0 = "BenesseExtensionService"
            java.lang.String r2 = "----- invalid data! -----"
            android.util.Log.e(r0, r2)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            r0 = 0
            $closeResource(r0, r7)     // Catch:{ Throwable -> 0x002c }
            r0 = 0
            $closeResource(r0, r6)     // Catch:{ Throwable -> 0x0033 }
            r0 = r3
            goto L_0x003c
        L_0x005e:
            r2 = r0
            r4 = r3
        L_0x0060:
            int r8 = r5.length()     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            int r8 = r8 + -1
            if (r2 >= r8) goto L_0x0085
            char r8 = r5.charAt(r2)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            r9 = 16
            int r8 = java.lang.Character.digit(r8, r9)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            int r8 = r8 << 4
            int r9 = r2 + 1
            char r9 = r5.charAt(r9)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            r10 = 16
            int r9 = java.lang.Character.digit(r9, r10)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            int r8 = r8 + r9
            int r4 = r4 + r8
            int r2 = r2 + 2
            goto L_0x0060
        L_0x0085:
            r2 = r4 & 255(0xff, float:3.57E-43)
            if (r2 == 0) goto L_0x009a
            java.lang.String r0 = "BenesseExtensionService"
            java.lang.String r2 = "----- wrong checksum! -----"
            android.util.Log.e(r0, r2)     // Catch:{ Throwable -> 0x0025, all -> 0x00c2 }
            r0 = 0
            $closeResource(r0, r7)     // Catch:{ Throwable -> 0x002c }
            r0 = 0
            $closeResource(r0, r6)     // Catch:{ Throwable -> 0x0033 }
            r0 = r3
            goto L_0x003c
        L_0x009a:
            r2 = r5
            goto L_0x000e
        L_0x009d:
            r4 = 0
            $closeResource(r4, r7)     // Catch:{ Throwable -> 0x002c }
            r1 = 0
            $closeResource(r1, r6)     // Catch:{ Throwable -> 0x0033 }
            r1 = 7
            char r1 = r2.charAt(r1)
            r4 = 48
            if (r1 != r4) goto L_0x00b8
            r1 = 8
            char r1 = r2.charAt(r1)
            r2 = 49
            if (r1 == r2) goto L_0x003c
        L_0x00b8:
            java.lang.String r0 = "BenesseExtensionService"
            java.lang.String r1 = "----- last line is not end of file! -----"
            android.util.Log.e(r0, r1)
            r0 = r3
            goto L_0x003c
        L_0x00c2:
            r2 = move-exception
            r0 = r1
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.server.BenesseExtensionService.checkHexFile(java.lang.String):boolean");
    }

    private boolean executeFwUpdate(UpdateParams updateParams) {
        if (this.mIsUpdating) {
            Log.e(TAG, "----- FW update : already updating! -----");
            return false;
        }
        this.mIsUpdating = true;
        new Thread(new _$$Lambda$BenesseExtensionService$DuLYMgReFex30dZ2dylIKOPJ6RA(this, updateParams)).start();
        return true;
    }

    /* access modifiers changed from: private */
    public int getAdbEnabled() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "adb_enabled", 0);
    }

    private int getCompatScreenMode() {
        Point point = new Point();
        try {
            int baseDisplayDensity = this.mWindowManager.getBaseDisplayDensity(0);
            this.mWindowManager.getBaseDisplaySize(0, point);
            for (int i = 0; i < mTable.length; i++) {
                if (baseDisplayDensity == mTable[i][0] && point.x == mTable[i][1] && point.y == mTable[i][2]) {
                    return i;
                }
            }
            return -1;
        } catch (RemoteException e) {
            Log.e(TAG, "----- getCompatScreenMode() : Exception occurred! -----", e);
            return -1;
        }
    }

    /* access modifiers changed from: private */
    public boolean getDchaCompletedPast() {
        return !BenesseExtension.IGNORE_DCHA_COMPLETED_FILE.exists() && BenesseExtension.COUNT_DCHA_COMPLETED_FILE.exists();
    }

    /* access modifiers changed from: private */
    public int getDchaStateInternal() {
        return Settings.System.getInt(this.mContext.getContentResolver(), DCHA_STATE, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0027, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0028, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x002f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        $closeResource(r1, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0033, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x003a, code lost:
        r2 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getFirmwareVersion(java.io.File r6) {
        /*
            r5 = this;
            r1 = 0
            boolean r0 = r5.mIsUpdating
            if (r0 == 0) goto L_0x0006
        L_0x0005:
            return r1
        L_0x0006:
            boolean r0 = r6.exists()
            if (r0 != 0) goto L_0x000f
            java.lang.String r1 = ""
            goto L_0x0005
        L_0x000f:
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x0034 }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0034 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x002d }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x002d }
            java.lang.String r0 = r4.readLine()     // Catch:{ Throwable -> 0x0038, all -> 0x0027 }
            r2 = 0
            $closeResource(r2, r4)     // Catch:{ Throwable -> 0x002d }
            r1 = 0
            $closeResource(r1, r3)     // Catch:{ Throwable -> 0x0034 }
            r1 = r0
            goto L_0x0005
        L_0x0027:
            r2 = move-exception
            r0 = r1
        L_0x0029:
            $closeResource(r0, r4)     // Catch:{ Throwable -> 0x002d }
            throw r2     // Catch:{ Throwable -> 0x002d }
        L_0x002d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002f }
        L_0x002f:
            r0 = move-exception
            $closeResource(r1, r3)     // Catch:{ Throwable -> 0x0034 }
            throw r0     // Catch:{ Throwable -> 0x0034 }
        L_0x0034:
            r0 = move-exception
            java.lang.String r1 = ""
            goto L_0x0005
        L_0x0038:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x003a }
        L_0x003a:
            r2 = move-exception
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.server.BenesseExtensionService.getFirmwareVersion(java.io.File):java.lang.String");
    }

    private String getLanguage() {
        String str = SystemProperties.get(PROPERTY_LOCALE, JAPAN_LOCALE);
        return (str == null || str.equals("")) ? JAPAN_LOCALE : str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.mediatek.server.BenesseExtensionService.UpdateParams getUpdateParams(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            r0 = 0
            r7 = 3
            r6 = 2
            r4 = 1
            r3 = 0
            com.mediatek.server.BenesseExtensionService$UpdateParams r1 = new com.mediatek.server.BenesseExtensionService$UpdateParams
            r1.<init>()
            int r2 = r9.hashCode()
            r5 = 1247406799(0x4a59eacf, float:3570355.8)
            if (r2 == r5) goto L_0x0027
            r5 = 1964675707(0x751a927b, float:1.9594353E32)
            if (r2 == r5) goto L_0x001d
        L_0x0018:
            r2 = -1
        L_0x0019:
            switch(r2) {
                case 0: goto L_0x0045;
                case 1: goto L_0x0031;
                default: goto L_0x001c;
            }
        L_0x001c:
            return r0
        L_0x001d:
            java.lang.String r2 = "bc:touchpanel:fw_update"
            boolean r2 = r9.equals(r2)
            if (r2 == 0) goto L_0x0018
            r2 = r3
            goto L_0x0019
        L_0x0027:
            java.lang.String r2 = "bc:digitizer:fw_update"
            boolean r2 = r9.equals(r2)
            if (r2 == 0) goto L_0x0018
            r2 = r4
            goto L_0x0019
        L_0x0031:
            java.lang.String[] r0 = new java.lang.String[r7]
            java.lang.String r2 = "/system/bin/.wac_flash"
            r0[r3] = r2
            r0[r4] = r10
            java.lang.String r2 = "i2c-2"
            r0[r6] = r2
            r1.cmd = r0
            java.lang.String r0 = "com.panasonic.sanyo.ts.intent.action.DIGITIZER_FIRMWARE_UPDATED"
            r1.broadcast = r0
            r0 = r1
            goto L_0x001c
        L_0x0045:
            r0 = 4
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.String r2 = "/system/bin/.wacom_flash"
            r0[r3] = r2
            r0[r4] = r10
            java.lang.String r2 = "1"
            r0[r6] = r2
            java.lang.String r2 = "i2c-0"
            r0[r7] = r2
            r1.cmd = r0
            java.lang.String r0 = "com.panasonic.sanyo.ts.intent.action.TOUCHPANEL_FIRMWARE_UPDATED"
            r1.broadcast = r0
            r0 = r1
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.server.BenesseExtensionService.getUpdateParams(java.lang.String, java.lang.String):com.mediatek.server.BenesseExtensionService$UpdateParams");
    }

    public static /* synthetic */ void lambda$executeFwUpdate$0(BenesseExtensionService benesseExtensionService, UpdateParams updateParams, int i) {
        benesseExtensionService.mIsUpdating = false;
        benesseExtensionService.mContext.sendBroadcastAsUser(new Intent(updateParams.broadcast).putExtra(EXTRA_RESULT, i), UserHandle.ALL);
    }

    public static /* synthetic */ void lambda$executeFwUpdate$1(BenesseExtensionService benesseExtensionService, UpdateParams updateParams) {
        int i;
        try {
            i = Runtime.getRuntime().exec(updateParams.cmd).waitFor();
        } catch (Throwable th) {
            Log.e(TAG, "----- Exception occurred! -----", th);
            i = -1;
        }
        benesseExtensionService.mHandler.post(new _$$Lambda$BenesseExtensionService$erbcCrbZOhYH_JEcBSKtqZ9g_84(benesseExtensionService, updateParams, i));
    }

    private boolean setCompatScreenMode(int i) {
        boolean z = true;
        if (i < 0 || i >= mTable.length) {
            return false;
        }
        try {
            this.mWindowManager.setForcedDisplayDensityForUser(0, mTable[i][0], -2);
            this.mWindowManager.setForcedDisplaySize(0, mTable[i][1], mTable[i][2]);
            if (getCompatScreenMode() != i) {
                z = false;
            }
            return z;
        } catch (RemoteException e) {
            Log.e(TAG, "----- setCompatScreenMode() : Exception occurred! -----", e);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void updateBrowserEnabled() {
        int i = 2;
        if (!getDchaCompletedPast() && (getDchaStateInternal() == 0 || getAdbEnabled() != 0 || !JAPAN_LOCALE.equals(getLanguage()))) {
            i = 0;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        int applicationEnabledSetting = packageManager.getApplicationEnabledSetting(PACKAGE_NAME_BROWSER);
        int applicationEnabledSetting2 = packageManager.getApplicationEnabledSetting(PACKAGE_NAME_QSB);
        if (i != applicationEnabledSetting) {
            packageManager.setApplicationEnabledSetting(PACKAGE_NAME_BROWSER, i, 0);
        }
        if (i != applicationEnabledSetting2) {
            packageManager.setApplicationEnabledSetting(PACKAGE_NAME_QSB, i, 0);
        }
    }

    /* access modifiers changed from: private */
    public void updateTraceurEnabled() {
        if (getDchaStateInternal() == 0) {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager.getApplicationEnabledSetting(PACKAGE_NAME_TRACEUR) != 0) {
                packageManager.setApplicationEnabledSetting(PACKAGE_NAME_TRACEUR, 0, 0);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
        r3 = null;
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0072, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0073, code lost:
        r3 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkPassword(java.lang.String r8) {
        /*
            r7 = this;
            r1 = 0
            r6 = 64
            r2 = 0
            if (r8 != 0) goto L_0x0007
        L_0x0006:
            return r1
        L_0x0007:
            byte[] r0 = new byte[r6]
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0065 }
            java.lang.String r3 = "/factory/dcha_hash"
            r5.<init>(r3)     // Catch:{ IOException -> 0x0065 }
            int r3 = r5.read(r0)     // Catch:{ Throwable -> 0x0070, all -> 0x005e }
            if (r3 == r6) goto L_0x001e
            byte[] r0 = DEFAULT_HASH     // Catch:{ Throwable -> 0x0070, all -> 0x005e }
            java.lang.Object r0 = r0.clone()     // Catch:{ Throwable -> 0x0070, all -> 0x005e }
            byte[] r0 = (byte[]) r0     // Catch:{ Throwable -> 0x0070, all -> 0x005e }
        L_0x001e:
            r3 = 0
            $closeResource(r3, r5)     // Catch:{ IOException -> 0x0065 }
            r3 = r0
        L_0x0023:
            java.lang.String r0 = "SHA-256"
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0)     // Catch:{ NoSuchAlgorithmException -> 0x0075 }
        L_0x0029:
            if (r0 == 0) goto L_0x009d
            r0.reset()
            byte[] r2 = r8.getBytes()
            byte[] r4 = r0.digest(r2)
            byte[] r2 = new byte[r6]
            r0 = r1
        L_0x0039:
            int r1 = r4.length
            if (r0 >= r1) goto L_0x0078
            int r1 = r2.length
            int r1 = r1 / 2
            if (r0 >= r1) goto L_0x0078
            int r1 = r0 * 2
            byte[] r5 = r7.HEX_TABLE
            byte r6 = r4[r0]
            int r6 = r6 >> 4
            r6 = r6 & 15
            byte r5 = r5[r6]
            r2[r1] = r5
            int r1 = r1 + 1
            byte[] r5 = r7.HEX_TABLE
            byte r6 = r4[r0]
            r6 = r6 & 15
            byte r5 = r5[r6]
            r2[r1] = r5
            int r0 = r0 + 1
            goto L_0x0039
        L_0x005e:
            r0 = move-exception
            r3 = r2
            r4 = r0
        L_0x0061:
            $closeResource(r3, r5)     // Catch:{ IOException -> 0x0065 }
            throw r4     // Catch:{ IOException -> 0x0065 }
        L_0x0065:
            r0 = move-exception
            byte[] r0 = DEFAULT_HASH
            java.lang.Object r0 = r0.clone()
            byte[] r0 = (byte[]) r0
            r3 = r0
            goto L_0x0023
        L_0x0070:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0072 }
        L_0x0072:
            r4 = move-exception
            r3 = r0
            goto L_0x0061
        L_0x0075:
            r0 = move-exception
            r0 = r2
            goto L_0x0029
        L_0x0078:
            r0 = r2
        L_0x0079:
            boolean r1 = java.util.Arrays.equals(r3, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "password comparison = "
            r0.append(r2)
            r0.append(r1)
            java.lang.String r2 = "BenesseExtensionService"
            java.lang.String r0 = r0.toString()
            android.util.Log.i(r2, r0)
            if (r1 == 0) goto L_0x0006
            java.lang.String r0 = "bc_password_hit"
            r2 = 1
            r7.putInt(r0, r2)
            goto L_0x0006
        L_0x009d:
            r0 = r2
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.server.BenesseExtensionService.checkPassword(java.lang.String):boolean");
    }

    public int getDchaState() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getDchaStateInternal();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0053, code lost:
        r0 = android.provider.Settings.System.getInt(r5.mContext.getContentResolver(), BC_PASSWORD_HIT_FLAG, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005f, code lost:
        android.os.Binder.restoreCallingIdentity(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r0 = r5.mColorDisplayController.getColorTemperature();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0069, code lost:
        android.os.Binder.restoreCallingIdentity(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r0 = r5.mColorDisplayController.getMinimumColorTemperature();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0073, code lost:
        android.os.Binder.restoreCallingIdentity(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r0 = r5.mColorDisplayController.getMaximumColorTemperature();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x007d, code lost:
        android.os.Binder.restoreCallingIdentity(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r0 = r5.mColorDisplayController.isActivated();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0087, code lost:
        android.os.Binder.restoreCallingIdentity(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r0 = getCompatScreenMode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0090, code lost:
        android.os.Binder.restoreCallingIdentity(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0011, code lost:
        switch(r1) {
            case 0: goto L_0x008d;
            case 1: goto L_0x0082;
            case 2: goto L_0x0078;
            case 3: goto L_0x006e;
            case 4: goto L_0x0064;
            case 5: goto L_0x0053;
            default: goto L_0x0014;
        };
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getInt(java.lang.String r6) {
        /*
            r5 = this;
            r1 = 0
            r0 = -1
            if (r6 != 0) goto L_0x0005
        L_0x0004:
            return r0
        L_0x0005:
            long r2 = android.os.Binder.clearCallingIdentity()
            int r4 = r6.hashCode()     // Catch:{ all -> 0x0096 }
            switch(r4) {
                case -286987330: goto L_0x0049;
                case 367025166: goto L_0x003f;
                case 367025404: goto L_0x0035;
                case 1209732899: goto L_0x002b;
                case 1359997191: goto L_0x0022;
                case 1664403245: goto L_0x0018;
                default: goto L_0x0010;
            }
        L_0x0010:
            r1 = r0
        L_0x0011:
            switch(r1) {
                case 0: goto L_0x008d;
                case 1: goto L_0x0082;
                case 2: goto L_0x0078;
                case 3: goto L_0x006e;
                case 4: goto L_0x0064;
                case 5: goto L_0x0053;
                default: goto L_0x0014;
            }
        L_0x0014:
            android.os.Binder.restoreCallingIdentity(r2)
            goto L_0x0004
        L_0x0018:
            java.lang.String r1 = "bc_password_hit"
            boolean r1 = r6.equals(r1)     // Catch:{ all -> 0x0096 }
            if (r1 == 0) goto L_0x0010
            r1 = 5
            goto L_0x0011
        L_0x0022:
            java.lang.String r4 = "bc:compatscreen"
            boolean r4 = r6.equals(r4)     // Catch:{ all -> 0x0096 }
            if (r4 == 0) goto L_0x0010
            goto L_0x0011
        L_0x002b:
            java.lang.String r1 = "bc:nightcolor:current"
            boolean r1 = r6.equals(r1)     // Catch:{ all -> 0x0096 }
            if (r1 == 0) goto L_0x0010
            r1 = 4
            goto L_0x0011
        L_0x0035:
            java.lang.String r1 = "bc:nightcolor:min"
            boolean r1 = r6.equals(r1)     // Catch:{ all -> 0x0096 }
            if (r1 == 0) goto L_0x0010
            r1 = 3
            goto L_0x0011
        L_0x003f:
            java.lang.String r1 = "bc:nightcolor:max"
            boolean r1 = r6.equals(r1)     // Catch:{ all -> 0x0096 }
            if (r1 == 0) goto L_0x0010
            r1 = 2
            goto L_0x0011
        L_0x0049:
            java.lang.String r1 = "bc:nightmode:active"
            boolean r1 = r6.equals(r1)     // Catch:{ all -> 0x0096 }
            if (r1 == 0) goto L_0x0010
            r1 = 1
            goto L_0x0011
        L_0x0053:
            android.content.Context r0 = r5.mContext     // Catch:{ all -> 0x0096 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = "bc_password_hit"
            r4 = 0
            int r0 = android.provider.Settings.System.getInt(r0, r1, r4)     // Catch:{ all -> 0x0096 }
            android.os.Binder.restoreCallingIdentity(r2)
            goto L_0x0004
        L_0x0064:
            com.android.internal.app.ColorDisplayController r0 = r5.mColorDisplayController     // Catch:{ all -> 0x0096 }
            int r0 = r0.getColorTemperature()     // Catch:{ all -> 0x0096 }
            android.os.Binder.restoreCallingIdentity(r2)
            goto L_0x0004
        L_0x006e:
            com.android.internal.app.ColorDisplayController r0 = r5.mColorDisplayController     // Catch:{ all -> 0x0096 }
            int r0 = r0.getMinimumColorTemperature()     // Catch:{ all -> 0x0096 }
            android.os.Binder.restoreCallingIdentity(r2)
            goto L_0x0004
        L_0x0078:
            com.android.internal.app.ColorDisplayController r0 = r5.mColorDisplayController     // Catch:{ all -> 0x0096 }
            int r0 = r0.getMaximumColorTemperature()     // Catch:{ all -> 0x0096 }
            android.os.Binder.restoreCallingIdentity(r2)
            goto L_0x0004
        L_0x0082:
            com.android.internal.app.ColorDisplayController r0 = r5.mColorDisplayController     // Catch:{ all -> 0x0096 }
            boolean r0 = r0.isActivated()     // Catch:{ all -> 0x0096 }
            android.os.Binder.restoreCallingIdentity(r2)
            goto L_0x0004
        L_0x008d:
            int r0 = r5.getCompatScreenMode()     // Catch:{ all -> 0x0096 }
            android.os.Binder.restoreCallingIdentity(r2)
            goto L_0x0004
        L_0x0096:
            r0 = move-exception
            android.os.Binder.restoreCallingIdentity(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.server.BenesseExtensionService.getInt(java.lang.String):int");
    }

    /* JADX INFO: finally extract failed */
    public String getString(String str) {
        if (str == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        char c = 65535;
        try {
            int hashCode = str.hashCode();
            if (hashCode != -1125691405) {
                if (hashCode != 94655307) {
                    if (hashCode != 600943506) {
                        if (hashCode == 1361443174) {
                            if (str.equals(BC_TP_FW_VERSION)) {
                                c = 2;
                            }
                        }
                    } else if (str.equals(BC_DT_FW_VERSION)) {
                        c = 3;
                    }
                } else if (str.equals(BC_MAC_ADDRESS)) {
                    c = 0;
                }
            } else if (str.equals(BC_SERIAL_NO)) {
                c = 1;
            }
            switch (c) {
                case 0:
                    WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
                    if (wifiManager == null) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return null;
                    }
                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (connectionInfo == null) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return null;
                    }
                    String macAddress = connectionInfo.getMacAddress();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return macAddress;
                case 1:
                    String serial = Build.getSerial();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return serial;
                case 2:
                    String firmwareVersion = getFirmwareVersion(SYSFILE_TP_VERSION);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return firmwareVersion;
                case 3:
                    String firmwareVersion2 = getFirmwareVersion(SYSFILE_DT_VERSION);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return firmwareVersion2;
                default:
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0025 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0051 A[Catch:{ all -> 0x0088 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0061 A[SYNTHETIC, Splitter:B:32:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x007f A[SYNTHETIC, Splitter:B:43:0x007f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean putInt(java.lang.String r7, int r8) {
        /*
            r6 = this;
            r1 = 1
            r0 = 0
            if (r7 != 0) goto L_0x0005
        L_0x0004:
            return r0
        L_0x0005:
            long r4 = android.os.Binder.clearCallingIdentity()
            int r2 = r7.hashCode()     // Catch:{ all -> 0x0088 }
            r3 = -286987330(0xffffffffeee4ebbe, float:-3.5423789E28)
            if (r2 == r3) goto L_0x0047
            r3 = 1209732899(0x481b0f23, float:158780.55)
            if (r2 == r3) goto L_0x003d
            r3 = 1359997191(0x510fe907, float:3.8630617E10)
            if (r2 == r3) goto L_0x0033
            r3 = 1664403245(0x6334c72d, float:3.334766E21)
            if (r2 == r3) goto L_0x0029
        L_0x0021:
            r2 = -1
        L_0x0022:
            switch(r2) {
                case 0: goto L_0x007f;
                case 1: goto L_0x006b;
                case 2: goto L_0x0061;
                case 3: goto L_0x0051;
                default: goto L_0x0025;
            }
        L_0x0025:
            android.os.Binder.restoreCallingIdentity(r4)
            goto L_0x0004
        L_0x0029:
            java.lang.String r2 = "bc_password_hit"
            boolean r2 = r7.equals(r2)     // Catch:{ all -> 0x0088 }
            if (r2 == 0) goto L_0x0021
            r2 = 3
            goto L_0x0022
        L_0x0033:
            java.lang.String r2 = "bc:compatscreen"
            boolean r2 = r7.equals(r2)     // Catch:{ all -> 0x0088 }
            if (r2 == 0) goto L_0x0021
            r2 = r0
            goto L_0x0022
        L_0x003d:
            java.lang.String r2 = "bc:nightcolor:current"
            boolean r2 = r7.equals(r2)     // Catch:{ all -> 0x0088 }
            if (r2 == 0) goto L_0x0021
            r2 = 2
            goto L_0x0022
        L_0x0047:
            java.lang.String r2 = "bc:nightmode:active"
            boolean r2 = r7.equals(r2)     // Catch:{ all -> 0x0088 }
            if (r2 == 0) goto L_0x0021
            r2 = r1
            goto L_0x0022
        L_0x0051:
            android.content.Context r0 = r6.mContext     // Catch:{ all -> 0x0088 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ all -> 0x0088 }
            java.lang.String r2 = "bc_password_hit"
            android.provider.Settings.System.putInt(r0, r2, r8)     // Catch:{ all -> 0x0088 }
            android.os.Binder.restoreCallingIdentity(r4)
            r0 = r1
            goto L_0x0004
        L_0x0061:
            com.android.internal.app.ColorDisplayController r0 = r6.mColorDisplayController     // Catch:{ all -> 0x0088 }
            boolean r0 = r0.setColorTemperature(r8)     // Catch:{ all -> 0x0088 }
            android.os.Binder.restoreCallingIdentity(r4)
            goto L_0x0004
        L_0x006b:
            if (r8 == 0) goto L_0x0073
            if (r8 == r1) goto L_0x0073
            android.os.Binder.restoreCallingIdentity(r4)
            goto L_0x0004
        L_0x0073:
            com.android.internal.app.ColorDisplayController r2 = r6.mColorDisplayController     // Catch:{ all -> 0x0088 }
            if (r8 != r1) goto L_0x008d
        L_0x0077:
            boolean r0 = r2.setActivated(r1)     // Catch:{ all -> 0x0088 }
            android.os.Binder.restoreCallingIdentity(r4)
            goto L_0x0004
        L_0x007f:
            boolean r0 = r6.setCompatScreenMode(r8)     // Catch:{ all -> 0x0088 }
            android.os.Binder.restoreCallingIdentity(r4)
            goto L_0x0004
        L_0x0088:
            r0 = move-exception
            android.os.Binder.restoreCallingIdentity(r4)
            throw r0
        L_0x008d:
            r1 = r0
            goto L_0x0077
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.server.BenesseExtensionService.putInt(java.lang.String, int):boolean");
    }

    public boolean putString(String str, String str2) {
        boolean z = false;
        if (!(str == null || str2 == null)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            char c = 65535;
            try {
                int hashCode = str.hashCode();
                if (hashCode != 1247406799) {
                    if (hashCode == 1964675707) {
                        if (str.equals(BC_TP_FW_UPDATE)) {
                            c = 0;
                        }
                    }
                } else if (str.equals(BC_DT_FW_UPDATE)) {
                    c = 1;
                }
                switch (c) {
                    case 0:
                    case 1:
                        String replaceFirst = str2.replaceFirst("^/sdcard/", "/data/media/0/");
                        if (new File(replaceFirst).isFile()) {
                            if (checkHexFile(replaceFirst)) {
                                z = executeFwUpdate(getUpdateParams(str, replaceFirst));
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                break;
                            }
                        } else {
                            Log.e(TAG, "----- putString() : invalid file. name[" + str + "] value[" + str2 + "] -----");
                            break;
                        }
                        break;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return z;
    }

    public void setDchaState(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), DCHA_STATE, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
