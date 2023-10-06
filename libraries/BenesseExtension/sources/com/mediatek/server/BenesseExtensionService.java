package com.mediatek.server;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Point;
import android.hardware.usb.UsbManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BenesseExtension;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBenesseExtensionService;
import android.os.IBinder;
import android.os.IStsExtensionService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.IWindowManager;
import com.android.internal.app.ColorDisplayController;
import com.mediatek.server.BenesseExtensionService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
/* loaded from: BenesseExtension.jar:com/mediatek/server/BenesseExtensionService.class */
public class BenesseExtensionService extends IBenesseExtensionService.Stub {
    static final String ACTION_DT_FW_UPDATED = "com.panasonic.sanyo.ts.intent.action.DIGITIZER_FIRMWARE_UPDATED";
    static final String ACTION_TP_FW_UPDATED = "com.panasonic.sanyo.ts.intent.action.TOUCHPANEL_FIRMWARE_UPDATED";
    static final String BC_COMPATSCREEN = "bc:compatscreen";
    static final String BC_DT_FW_UPDATE = "bc:digitizer:fw_update";
    static final String BC_DT_FW_VERSION = "bc:digitizer:fw_version";
    static final String BC_FTS_PALM_SIZE = "bc:touchpanel:palmreject:size";
    static final String BC_FTS_PEN_BATTERY = "bc:pen:battery";
    static final String BC_FTS_TP_FW_UPDATE = "bc:touchpanel:fts:fw_update";
    static final String BC_FTS_TP_FW_VERSION = "bc:touchpanel:fts:fw_version";
    static final String BC_MAC_ADDRESS = "bc:mac_address";
    static final String BC_NIGHTCOLOR_CURRENT = "bc:nightcolor:current";
    static final String BC_NIGHTCOLOR_MAX = "bc:nightcolor:max";
    static final String BC_NIGHTCOLOR_MIN = "bc:nightcolor:min";
    static final String BC_NIGHTMODE_ACTIVE = "bc:nightmode:active";
    static final String BC_NVT_PALM_SIZE = "bc:touchpanel:palmreject:size";
    static final String BC_NVT_PEN_BATTERY = "bc:pen:battery";
    static final String BC_NVT_TP_FW_UPDATE = "bc:touchpanel:nvt:fw_update";
    static final String BC_NVT_TP_FW_VERSION = "bc:touchpanel:nvt:fw_version";
    static final String BC_PASSWORD_HIT_FLAG = "bc_password_hit";
    static final String BC_SERIAL_NO = "bc:serial_no";
    static final String BC_TP_FW_UPDATE = "bc:touchpanel:fw_update";
    static final String BC_TP_FW_VERSION = "bc:touchpanel:fw_version";
    static final String BC_TP_LCD_TYPE = "bc:touchpanel:lcd_type";
    static final String DCHA_HASH_FILEPATH = "/factory/dcha_hash";
    static final String DCHA_STATE = "dcha_state";
    static final String EXTRA_RESULT = "result";
    static final String HASH_ALGORITHM = "SHA-256";
    static final String JAPAN_LOCALE = "ja-JP";
    static final String PACKAGE_NAME_BROWSER = "com.android.browser";
    static final String PACKAGE_NAME_QSB = "com.android.quicksearchbox";
    static final String PACKAGE_NAME_TRACEUR = "com.android.traceur";
    static final String PROPERTY_DCHA_STATE = "persist.sys.bc.dcha_state";
    static final String PROPERTY_LOCALE = "persist.sys.locale";
    static final String TAG = "BenesseExtensionService";
    private ColorDisplayController mColorDisplayController;
    private Context mContext;
    private IWindowManager mWindowManager;
    private int tp_type;
    static final File SYSFILE_TP_VERSION = new File("/sys/devices/platform/soc/11007000.i2c/i2c-0/0-000a/tp_fwver");
    static final File SYSFILE_DT_VERSION = new File("/sys/devices/platform/soc/11009000.i2c/i2c-2/2-0009/digi_fwver");
    static final File SYSFILE_NVT_PARM_REJECT = new File("/sys/devices/platform/soc/1100f000.i2c/i2c-3/3-0062/tp_palm_reject");
    static final File SYSFILE_FTS_PARM_REJECT = new File("/sys/devices/platform/soc/1100f000.i2c/i2c-3/3-0038/fts_palm_reject");
    static final File PROC_NVT_TP_VERSION = new File("/proc/nvt_fw_version");
    static final File FTS_TP_VERSION = new File("/sys/class/i2c-dev/i2c-3/device/3-0038/fts_fw_version");
    private static final byte[] DEFAULT_HASH = "a1e3cf8aa7858a458972592ebb9438e967da30d196bd6191cc77606cc60af183".getBytes();
    static final int[][] mTable = {new int[]{240, 1920, 1200}, new int[]{160, 1024, 768}, new int[]{160, 1280, 800}};
    private boolean mIsUpdating = false;
    private IStsExtensionService mStsExtensionService = null;
    private final byte[] HEX_TABLE = "0123456789abcdef".getBytes();
    private Handler mHandler = new Handler(true);
    private ContentObserver mDchaStateObserver = new ContentObserver(this, this.mHandler) { // from class: com.mediatek.server.BenesseExtensionService.1
        final BenesseExtensionService this$0;

        {
            this.this$0 = this;
        }

        @Override // android.database.ContentObserver
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
    private ContentObserver mAdbObserver = new ContentObserver(this, this.mHandler) { // from class: com.mediatek.server.BenesseExtensionService.2
        final BenesseExtensionService this$0;

        {
            this.this$0 = this;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            synchronized (this.this$0.mLock) {
                Log.i(BenesseExtensionService.TAG, "getADBENABLE=" + this.this$0.getAdbEnabled());
                if (!this.this$0.changeAdbEnable()) {
                    this.this$0.updateBrowserEnabled();
                }
            }
        }
    };
    private BroadcastReceiver mLanguageReceiver = new BroadcastReceiver(this) { // from class: com.mediatek.server.BenesseExtensionService.3
        final BenesseExtensionService this$0;

        {
            this.this$0 = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            synchronized (this.this$0.mLock) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    this.this$0.updateBrowserEnabled();
                }
            }
        }
    };
    ServiceConnection mConn = new ServiceConnection(this) { // from class: com.mediatek.server.BenesseExtensionService.4
        final BenesseExtensionService this$0;

        {
            this.this$0 = this;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.this$0.mStsExtensionService = IStsExtensionService.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.this$0.mStsExtensionService = null;
        }
    };
    private Object mLock = new Object();

    /* loaded from: BenesseExtension.jar:com/mediatek/server/BenesseExtensionService$BootCompletedReceiver.class */
    private final class BootCompletedReceiver extends BroadcastReceiver {
        final BenesseExtensionService this$0;

        private BootCompletedReceiver(BenesseExtensionService benesseExtensionService) {
            this.this$0 = benesseExtensionService;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intent intent2 = new Intent("com.sts.tottori.stsextension.StsExtensionService");
            intent2.setPackage("com.sts.tottori.stsextension");
            context.bindServiceAsUser(intent2, this.this$0.mConn, 1, UserHandle.CURRENT);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: BenesseExtension.jar:com/mediatek/server/BenesseExtensionService$UpdateParams.class */
    public class UpdateParams {
        public String broadcast;
        public String[] cmd;
        final BenesseExtensionService this$0;

        private UpdateParams(BenesseExtensionService benesseExtensionService) {
            this.this$0 = benesseExtensionService;
        }
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th == null) {
            autoCloseable.close();
            return;
        }
        try {
            autoCloseable.close();
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
    }

    BenesseExtensionService(Context context) {
        this.tp_type = -1;
        this.mContext = context;
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
        this.mContext.registerReceiver(new BootCompletedReceiver(), new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        if (PROC_NVT_TP_VERSION.exists()) {
            Log.i(TAG, "----- TP:NVT -----");
            this.tp_type = 0;
        } else if (!FTS_TP_VERSION.exists()) {
            Log.e(TAG, "----- TP:Unkown -----");
        } else {
            Log.i(TAG, "----- TP:FTS -----");
            this.tp_type = 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean changeAdbEnable() {
        if (getAdbEnabled() == 0 || BenesseExtension.getDchaState() == 3 || !getDchaCompletedPast() || getInt(BC_PASSWORD_HIT_FLAG) != 0) {
            return false;
        }
        Settings.Global.putInt(this.mContext.getContentResolver(), "adb_enabled", 0);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDefaultUsbFunction(int i) {
        if (i > 0) {
            ((UsbManager) this.mContext.getSystemService(UsbManager.class)).setScreenUnlockedFunctions(0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDisallowInstallUnknownSource(boolean z) {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager != null) {
            userManager.setUserRestriction("no_install_unknown_sources", z, UserHandle.SYSTEM);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeSafemodeRestriction(int i) {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager != null) {
            userManager.setUserRestriction("no_safe_boot", i > 0, UserHandle.SYSTEM);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b1, code lost:
        android.util.Log.e(com.mediatek.server.BenesseExtensionService.TAG, "----- invalid data! -----");
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00bc, code lost:
        $closeResource(null, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00c3, code lost:
        $closeResource(null, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c8, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean checkHexFile(String str) {
        try {
            FileReader fileReader = new FileReader(str);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str2 = null;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    $closeResource(null, bufferedReader);
                    $closeResource(null, fileReader);
                    if (str2.charAt(7) == '0' && str2.charAt(8) == '1') {
                        return true;
                    }
                    Log.e(TAG, "----- last line is not end of file! -----");
                    return false;
                } else if (readLine.charAt(0) != ';') {
                    if (!readLine.matches(":[a-fA-F0-9]+") || readLine.length() % 2 == 0) {
                        break;
                    }
                    int i = 0;
                    for (int i2 = 1; i2 < readLine.length() - 1; i2 += 2) {
                        i += (Character.digit(readLine.charAt(i2), 16) << 4) + Character.digit(readLine.charAt(i2 + 1), 16);
                    }
                    if ((i & 255) != 0) {
                        Log.e(TAG, "----- wrong checksum! -----");
                        $closeResource(null, bufferedReader);
                        $closeResource(null, fileReader);
                        return false;
                    }
                    str2 = readLine;
                } else {
                    Log.w(TAG, "----- found comment line. -----");
                }
            }
        } catch (Throwable th) {
            Log.e(TAG, "----- Exception occurred!!! -----", th);
            return false;
        }
    }

    private boolean executeFwUpdate(final UpdateParams updateParams) {
        if (this.mIsUpdating) {
            Log.e(TAG, "----- FW update : already updating! -----");
            return false;
        }
        this.mIsUpdating = true;
        new Thread(new Runnable(this, updateParams) { // from class: com.mediatek.server._$$Lambda$BenesseExtensionService$DuLYMgReFex30dZ2dylIKOPJ6RA
            private final BenesseExtensionService f$0;
            private final BenesseExtensionService.UpdateParams f$1;

            {
                this.f$0 = this;
                this.f$1 = updateParams;
            }

            @Override // java.lang.Runnable
            public final void run() {
                BenesseExtensionService.lambda$executeFwUpdate$1(this.f$0, this.f$1);
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getDchaCompletedPast() {
        return !BenesseExtension.IGNORE_DCHA_COMPLETED_FILE.exists() && BenesseExtension.COUNT_DCHA_COMPLETED_FILE.exists();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDchaStateInternal() {
        return Settings.System.getInt(this.mContext.getContentResolver(), DCHA_STATE, 0);
    }

    private String getFirmwareVersion(File file) {
        if (this.mIsUpdating) {
            return null;
        }
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String readLine = bufferedReader.readLine();
                $closeResource(null, bufferedReader);
                $closeResource(null, fileReader);
                return readLine;
            } catch (Throwable th) {
                return "";
            }
        }
        return "";
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0015, code lost:
        if (r0.equals("") != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String getLanguage() {
        String str;
        String str2 = SystemProperties.get(PROPERTY_LOCALE, JAPAN_LOCALE);
        if (str2 != null) {
            str = str2;
        }
        str = JAPAN_LOCALE;
        return str;
    }

    private String getLcdType() {
        return (PROC_NVT_TP_VERSION.exists() || FTS_TP_VERSION.exists()) ? String.valueOf(this.tp_type) : "";
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
        	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:35)
        	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:25)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:202)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:45)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    private int getPalmrejectSize() {
        /*
            r4 = this;
            java.lang.String r0 = "3"
            r5 = r0
            r0 = r4
            int r0 = r0.tp_type
            r6 = r0
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            r0 = 0
            r9 = r0
            r0 = 0
            r10 = r0
            r0 = r6
            if (r0 != 0) goto Lc9
            r0 = r5
            r11 = r0
            java.io.FileReader r0 = new java.io.FileReader     // Catch: java.lang.Throwable -> Lbb
            r8 = r0
            r0 = r5
            r11 = r0
            r0 = r8
            java.io.File r1 = com.mediatek.server.BenesseExtensionService.SYSFILE_NVT_PARM_REJECT     // Catch: java.lang.Throwable -> Lbb
            r0.<init>(r1)     // Catch: java.lang.Throwable -> Lbb
            r0 = r5
            r12 = r0
            r0 = r10
            r11 = r0
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L9e
            r9 = r0
            r0 = r5
            r12 = r0
            r0 = r10
            r11 = r0
            r0 = r9
            r1 = r8
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L9e
            r0 = r9
            java.lang.String r0 = r0.readLine()     // Catch: java.lang.Throwable -> L79
            r11 = r0
            r0 = 0
            r1 = r9
            $closeResource(r0, r1)     // Catch: java.lang.Throwable -> L6a
            r0 = 0
            r1 = r8
            $closeResource(r0, r1)     // Catch: java.lang.Throwable -> L5b
            goto Lc6
        L5b:
            r5 = move-exception
            goto Lbc
        L5f:
            r13 = move-exception
            r0 = r11
            r12 = r0
            r0 = r7
            r5 = r0
            goto Laa
        L6a:
            r13 = move-exception
            r0 = r11
            r5 = r0
            goto La0
        L72:
            r7 = move-exception
            r0 = 0
            r13 = r0
            goto L7f
        L79:
            r13 = move-exception
            r0 = r13
            throw r0     // Catch: java.lang.Throwable -> L7e
        L7e:
            r7 = move-exception
        L7f:
            r0 = r5
            r12 = r0
            r0 = r10
            r11 = r0
            r0 = r13
            r1 = r9
            $closeResource(r0, r1)     // Catch: java.lang.Throwable -> L9e
            r0 = r5
            r12 = r0
            r0 = r10
            r11 = r0
            r0 = r7
            throw r0     // Catch: java.lang.Throwable -> L9e
        L96:
            r13 = move-exception
            r0 = r11
            r5 = r0
            goto Laa
        L9e:
            r13 = move-exception
        La0:
            r0 = r5
            r12 = r0
            r0 = r13
            r11 = r0
            r0 = r13
            throw r0     // Catch: java.lang.Throwable -> L96
        Laa:
            r0 = r12
            r11 = r0
            r0 = r5
            r1 = r8
            $closeResource(r0, r1)     // Catch: java.lang.Throwable -> Lbb
            r0 = r12
            r11 = r0
            r0 = r13
            throw r0     // Catch: java.lang.Throwable -> Lbb
        Lbb:
            r5 = move-exception
        Lbc:
            java.lang.String r0 = "BenesseExtensionService"
            java.lang.String r1 = "----- Exception occurred! -----"
            r2 = r5
            int r0 = android.util.Log.e(r0, r1, r2)
        Lc6:
            goto L177
        Lc9:
            r0 = r5
            r11 = r0
            java.io.FileReader r0 = new java.io.FileReader     // Catch: java.lang.Throwable -> L16c
            r10 = r0
            r0 = r5
            r11 = r0
            r0 = r10
            java.io.File r1 = com.mediatek.server.BenesseExtensionService.SYSFILE_FTS_PARM_REJECT     // Catch: java.lang.Throwable -> L16c
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L16c
            r0 = r5
            r12 = r0
            r0 = r8
            r11 = r0
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L14f
            r14 = r0
            r0 = r5
            r12 = r0
            r0 = r8
            r11 = r0
            r0 = r14
            r1 = r10
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L14f
            r0 = r14
            java.lang.String r0 = r0.readLine()     // Catch: java.lang.Throwable -> L12b
            r11 = r0
            r0 = 0
            r1 = r14
            $closeResource(r0, r1)     // Catch: java.lang.Throwable -> L11c
            r0 = 0
            r1 = r10
            $closeResource(r0, r1)     // Catch: java.lang.Throwable -> L10c
            goto L177
        L10c:
            r5 = move-exception
            goto L16d
        L110:
            r13 = move-exception
            r0 = r11
            r12 = r0
            r0 = r9
            r5 = r0
            goto L15b
        L11c:
            r13 = move-exception
            r0 = r11
            r5 = r0
            goto L151
        L124:
            r13 = move-exception
            r0 = 0
            r7 = r0
            goto L130
        L12b:
            r7 = move-exception
            r0 = r7
            throw r0     // Catch: java.lang.Throwable -> L12e
        L12e:
            r13 = move-exception
        L130:
            r0 = r5
            r12 = r0
            r0 = r8
            r11 = r0
            r0 = r7
            r1 = r14
            $closeResource(r0, r1)     // Catch: java.lang.Throwable -> L14f
            r0 = r5
            r12 = r0
            r0 = r8
            r11 = r0
            r0 = r13
            throw r0     // Catch: java.lang.Throwable -> L14f
        L147:
            r13 = move-exception
            r0 = r11
            r5 = r0
            goto L15b
        L14f:
            r13 = move-exception
        L151:
            r0 = r5
            r12 = r0
            r0 = r13
            r11 = r0
            r0 = r13
            throw r0     // Catch: java.lang.Throwable -> L147
        L15b:
            r0 = r12
            r11 = r0
            r0 = r5
            r1 = r10
            $closeResource(r0, r1)     // Catch: java.lang.Throwable -> L16c
            r0 = r12
            r11 = r0
            r0 = r13
            throw r0     // Catch: java.lang.Throwable -> L16c
        L16c:
            r5 = move-exception
        L16d:
            java.lang.String r0 = "BenesseExtensionService"
            java.lang.String r1 = "----- Exception occurred! -----"
            r2 = r5
            int r0 = android.util.Log.e(r0, r1, r2)
        L177:
            r0 = r11
            int r0 = java.lang.Integer.parseInt(r0)
            r15 = r0
            r0 = r15
            r6 = r0
            r0 = r15
            r1 = 4
            if (r0 != r1) goto L189
            r0 = 0
            r6 = r0
        L189:
            r0 = r6
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.server.BenesseExtensionService.getPalmrejectSize():int");
    }

    private int getPenBattery() {
        if (this.mStsExtensionService != null) {
            try {
                return this.mStsExtensionService.getPenBattery();
            } catch (Throwable th) {
                Log.e(TAG, "----- Exception occurred! -----", th);
                return 0;
            }
        }
        return 0;
    }

    private String getTouchpanelVersion() {
        if (this.mStsExtensionService != null) {
            try {
                return this.mStsExtensionService.getTouchpanelVersion();
            } catch (Throwable th) {
                Log.e(TAG, "----- Exception occurred! -----", th);
                return "";
            }
        }
        return "";
    }

    private UpdateParams getUpdateParams(String str, String str2) {
        boolean z;
        UpdateParams updateParams = new UpdateParams();
        int hashCode = str.hashCode();
        if (hashCode != 1247406799) {
            if (hashCode == 1964675707 && str.equals(BC_TP_FW_UPDATE)) {
                z = false;
            }
            z = true;
        } else {
            if (str.equals(BC_DT_FW_UPDATE)) {
                z = true;
            }
            z = true;
        }
        switch (z) {
            case false:
                updateParams.cmd = new String[]{"/system/bin/.wacom_flash", str2, "1", "i2c-0"};
                updateParams.broadcast = ACTION_TP_FW_UPDATED;
                return updateParams;
            case true:
                updateParams.cmd = new String[]{"/system/bin/.wac_flash", str2, "i2c-2"};
                updateParams.broadcast = ACTION_DT_FW_UPDATED;
                return updateParams;
            default:
                return null;
        }
    }

    public static /* synthetic */ void lambda$executeFwUpdate$0(BenesseExtensionService benesseExtensionService, UpdateParams updateParams, int i) {
        benesseExtensionService.mIsUpdating = false;
        benesseExtensionService.mContext.sendBroadcastAsUser(new Intent(updateParams.broadcast).putExtra(EXTRA_RESULT, i), UserHandle.ALL);
    }

    public static /* synthetic */ void lambda$executeFwUpdate$1(final BenesseExtensionService benesseExtensionService, final UpdateParams updateParams) {
        int i;
        try {
            i = Runtime.getRuntime().exec(updateParams.cmd).waitFor();
        } catch (Throwable th) {
            Log.e(TAG, "----- Exception occurred! -----", th);
            i = -1;
        }
        final int i2 = i;
        benesseExtensionService.mHandler.post(new Runnable(benesseExtensionService, updateParams, i2) { // from class: com.mediatek.server._$$Lambda$BenesseExtensionService$erbcCrbZOhYH_JEcBSKtqZ9g_84
            private final BenesseExtensionService f$0;
            private final BenesseExtensionService.UpdateParams f$1;
            private final int f$2;

            {
                this.f$0 = benesseExtensionService;
                this.f$1 = updateParams;
                this.f$2 = i2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                BenesseExtensionService.lambda$executeFwUpdate$0(this.f$0, this.f$1, this.f$2);
            }
        });
    }

    private boolean setCompatScreenMode(int i) {
        boolean z = false;
        if (i < 0 || i >= mTable.length) {
            return false;
        }
        try {
            this.mWindowManager.setForcedDisplayDensityForUser(0, mTable[i][0], -2);
            this.mWindowManager.setForcedDisplaySize(0, mTable[i][1], mTable[i][2]);
            if (getCompatScreenMode() == i) {
                z = true;
            }
            return z;
        } catch (RemoteException e) {
            Log.e(TAG, "----- setCompatScreenMode() : Exception occurred! -----", e);
            return false;
        }
    }

    private boolean setPalmrejectSize(int i) {
        boolean z = false;
        if (i < 0 || 3 < i) {
            return false;
        }
        int i2 = 4;
        if (this.tp_type == 0) {
            if (i != 0) {
                i2 = i;
            }
            SystemProperties.set("nvt.set_parm_rejection", String.valueOf(i2));
        } else {
            if (i != 0) {
                i2 = i;
            }
            SystemProperties.set("fts.set_parm_rejection", String.valueOf(i2));
        }
        if (i == getPalmrejectSize()) {
            z = true;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    private boolean updateTouchpanelFw(String str) {
        if (this.mStsExtensionService != null) {
            try {
                return this.mStsExtensionService.updateTouchpanelFw(str);
            } catch (Throwable th) {
                Log.e(TAG, "----- Exception occurred! -----", th);
                return false;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTraceurEnabled() {
        if (getDchaStateInternal() != 0) {
            return;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager.getApplicationEnabledSetting(PACKAGE_NAME_TRACEUR) != 0) {
            packageManager.setApplicationEnabledSetting(PACKAGE_NAME_TRACEUR, 0, 0);
        }
    }

    @Override // android.os.IBenesseExtensionService
    public boolean checkPassword(String str) {
        byte[] bArr;
        MessageDigest messageDigest;
        int i = 0;
        if (str == null) {
            return false;
        }
        byte[] bArr2 = new byte[64];
        try {
            FileInputStream fileInputStream = new FileInputStream(DCHA_HASH_FILEPATH);
            bArr = bArr2;
            if (fileInputStream.read(bArr2) != 64) {
                bArr = (byte[]) DEFAULT_HASH.clone();
            }
            $closeResource(null, fileInputStream);
        } catch (IOException e) {
            bArr = (byte[]) DEFAULT_HASH.clone();
        }
        try {
            messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e2) {
            messageDigest = null;
        }
        byte[] bArr3 = null;
        if (messageDigest != null) {
            messageDigest.reset();
            byte[] digest = messageDigest.digest(str.getBytes());
            byte[] bArr4 = new byte[64];
            while (true) {
                bArr3 = bArr4;
                if (i >= digest.length) {
                    break;
                }
                bArr3 = bArr4;
                if (i >= bArr4.length / 2) {
                    break;
                }
                int i2 = i * 2;
                bArr4[i2] = this.HEX_TABLE[(digest[i] >> 4) & 15];
                bArr4[i2 + 1] = this.HEX_TABLE[digest[i] & 15];
                i++;
            }
        }
        boolean equals = Arrays.equals(bArr, bArr3);
        Log.i(TAG, "password comparison = " + equals);
        if (equals) {
            putInt(BC_PASSWORD_HIT_FLAG, 1);
        }
        return equals;
    }

    @Override // android.os.IBenesseExtensionService
    public int getDchaState() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getDchaStateInternal();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.os.IBenesseExtensionService
    public int getInt(String str) {
        Object[] objArr;
        if (str == null) {
            return -1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            switch (str.hashCode()) {
                case -286987330:
                    if (str.equals(BC_NIGHTMODE_ACTIVE)) {
                        objArr = 1;
                        break;
                    }
                    objArr = -1;
                    break;
                case 141985806:
                    if (str.equals("bc:touchpanel:palmreject:size")) {
                        objArr = 7;
                        break;
                    }
                    objArr = -1;
                    break;
                case 367025166:
                    if (str.equals(BC_NIGHTCOLOR_MAX)) {
                        objArr = 2;
                        break;
                    }
                    objArr = -1;
                    break;
                case 367025404:
                    if (str.equals(BC_NIGHTCOLOR_MIN)) {
                        objArr = 3;
                        break;
                    }
                    objArr = -1;
                    break;
                case 562531059:
                    if (str.equals("bc:pen:battery")) {
                        objArr = 6;
                        break;
                    }
                    objArr = -1;
                    break;
                case 1209732899:
                    if (str.equals(BC_NIGHTCOLOR_CURRENT)) {
                        objArr = 4;
                        break;
                    }
                    objArr = -1;
                    break;
                case 1359997191:
                    if (str.equals(BC_COMPATSCREEN)) {
                        objArr = null;
                        break;
                    }
                    objArr = -1;
                    break;
                case 1664403245:
                    if (str.equals(BC_PASSWORD_HIT_FLAG)) {
                        objArr = 5;
                        break;
                    }
                    objArr = -1;
                    break;
                default:
                    objArr = -1;
                    break;
            }
            switch (objArr) {
                case null:
                    return getCompatScreenMode();
                case 1:
                    return this.mColorDisplayController.isActivated() ? 1 : 0;
                case 2:
                    return this.mColorDisplayController.getMaximumColorTemperature();
                case 3:
                    return this.mColorDisplayController.getMinimumColorTemperature();
                case 4:
                    return this.mColorDisplayController.getColorTemperature();
                case 5:
                    return Settings.System.getInt(this.mContext.getContentResolver(), BC_PASSWORD_HIT_FLAG, 0);
                case 6:
                    if (!"TAB-A05-BD".equals(Build.PRODUCT)) {
                        return getPenBattery();
                    }
                    break;
                case 7:
                    if (!"TAB-A05-BD".equals(Build.PRODUCT)) {
                        return getPalmrejectSize();
                    }
                    break;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.os.IBenesseExtensionService
    public String getString(String str) {
        if (str == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = true;
        try {
            switch (str.hashCode()) {
                case -1149331608:
                    if (str.equals(BC_TP_LCD_TYPE)) {
                        z = true;
                        break;
                    }
                    break;
                case -1125691405:
                    if (str.equals(BC_SERIAL_NO)) {
                        z = true;
                        break;
                    }
                    break;
                case 94655307:
                    if (str.equals(BC_MAC_ADDRESS)) {
                        z = false;
                        break;
                    }
                    break;
                case 600943506:
                    if (str.equals(BC_DT_FW_VERSION)) {
                        z = true;
                        break;
                    }
                    break;
                case 681159668:
                    if (str.equals(BC_NVT_TP_FW_VERSION)) {
                        z = true;
                        break;
                    }
                    break;
                case 888384667:
                    if (str.equals(BC_FTS_TP_FW_VERSION)) {
                        z = true;
                        break;
                    }
                    break;
                case 1361443174:
                    if (str.equals(BC_TP_FW_VERSION)) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
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
                    return connectionInfo.getMacAddress();
                case true:
                    return Build.getSerial();
                case true:
                    return !"TAB-A05-BD".equals(Build.PRODUCT) ? getTouchpanelVersion() : getFirmwareVersion(SYSFILE_TP_VERSION);
                case true:
                    if (!"TAB-A05-BD".equals(Build.PRODUCT)) {
                        break;
                    } else {
                        return getFirmwareVersion(SYSFILE_DT_VERSION);
                    }
                case true:
                    if (!"TAB-A05-BD".equals(Build.PRODUCT)) {
                        return getTouchpanelVersion();
                    }
                    break;
                case true:
                    if (!"TAB-A05-BD".equals(Build.PRODUCT)) {
                        return getTouchpanelVersion();
                    }
                    break;
                case true:
                    if (!"TAB-A05-BD".equals(Build.PRODUCT)) {
                        return getLcdType();
                    }
                    break;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.os.IBenesseExtensionService
    public boolean putInt(String str, int i) {
        boolean z;
        boolean z2 = false;
        if (str == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            switch (str.hashCode()) {
                case -286987330:
                    if (str.equals(BC_NIGHTMODE_ACTIVE)) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 141985806:
                    if (str.equals("bc:touchpanel:palmreject:size")) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 1209732899:
                    if (str.equals(BC_NIGHTCOLOR_CURRENT)) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 1359997191:
                    if (str.equals(BC_COMPATSCREEN)) {
                        z = false;
                        break;
                    }
                    z = true;
                    break;
                case 1664403245:
                    if (str.equals(BC_PASSWORD_HIT_FLAG)) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                default:
                    z = true;
                    break;
            }
            switch (z) {
                case false:
                    return setCompatScreenMode(i);
                case true:
                    if (i != 0 && i != 1) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    ColorDisplayController colorDisplayController = this.mColorDisplayController;
                    if (i == 1) {
                        z2 = true;
                    }
                    return colorDisplayController.setActivated(z2);
                case true:
                    return this.mColorDisplayController.setColorTemperature(i);
                case true:
                    Settings.System.putInt(this.mContext.getContentResolver(), BC_PASSWORD_HIT_FLAG, i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                case true:
                    if (!"TAB-A05-BD".equals(Build.PRODUCT)) {
                        return setPalmrejectSize(i);
                    }
                    break;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.os.IBenesseExtensionService
    public boolean putString(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = true;
        try {
            int hashCode = str.hashCode();
            if (hashCode != 1111447085) {
                if (hashCode != 1247406799) {
                    if (hashCode == 1964675707 && str.equals(BC_TP_FW_UPDATE)) {
                        z = false;
                    }
                } else if (str.equals(BC_DT_FW_UPDATE)) {
                    z = true;
                }
            } else if (str.equals(BC_NVT_TP_FW_UPDATE)) {
                z = true;
            }
            switch (z) {
                case false:
                case true:
                    if ("TAB-A05-BD".equals(Build.PRODUCT)) {
                        String replaceFirst = str2.replaceFirst("^/sdcard/", "/data/media/0/").replaceFirst("^/storage/emulated/0/", "/data/media/0/");
                        if (!new File(replaceFirst).isFile()) {
                            Log.e(TAG, "----- putString() : invalid file. name[" + str + "] value[" + str2 + "] -----");
                            break;
                        } else if (!checkHexFile(replaceFirst)) {
                            break;
                        } else {
                            return executeFwUpdate(getUpdateParams(str, replaceFirst));
                        }
                    } else if (BC_TP_FW_UPDATE.equals(str)) {
                        return updateTouchpanelFw(str2);
                    }
                    break;
                case true:
                    if (!"TAB-A05-BD".equals(Build.PRODUCT)) {
                        return updateTouchpanelFw(str2);
                    }
                    break;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.os.IBenesseExtensionService
    public void setDchaState(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), DCHA_STATE, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
