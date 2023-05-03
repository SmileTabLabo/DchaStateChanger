package p000jp.p001co.benesse.dcha.dchautilservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.sts.tottori.extension.BenesseExtension;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import p000jp.p001co.benesse.dcha.dchautilservice.IDchaUtilService;
import p000jp.p001co.benesse.dcha.util.Logger;
import p000jp.p001co.benesse.dcha.util.ReflectionUtils;
import p000jp.p001co.benesse.dcha.util.StorageManagerAdapter;

/* renamed from: jp.co.benesse.dcha.dchautilservice.DchaUtilService */
public class DchaUtilService extends Service {
    public static final String TAG = DchaUtilService.class.getSimpleName();
    protected IDchaUtilService.Stub mDchaUtilServiceStub = new IDchaUtilService.Stub(this) {
        final DchaUtilService this$0;

        {
            this.this$0 = r1;
        }

        public void clearDefaultPreferredApp(String str) throws RemoteException {
            Logger.m4d(DchaUtilService.TAG, "clearDefaultPreferredApp 0001 packageName:", str);
            try {
                this.this$0.getPackageManager().clearPackagePreferredActivities(str);
            } catch (Exception e) {
                Logger.m6e(DchaUtilService.TAG, "clearDefaultPreferredApp 0002", e);
                throw new RemoteException(e.toString());
            }
        }

        /* access modifiers changed from: protected */
        public void close(Closeable closeable) {
            Logger.m4d(DchaUtilService.TAG, "close 0001");
            if (closeable == null) {
                Logger.m4d(DchaUtilService.TAG, "close 0002");
                return;
            }
            try {
                closeable.close();
            } catch (IOException e) {
                Logger.m4d(DchaUtilService.TAG, "close 0003");
            }
            Logger.m4d(DchaUtilService.TAG, "close 0004");
        }

        public boolean copyDirectory(String str, String str2, boolean z) {
            boolean z2;
            Logger.m4d(DchaUtilService.TAG, "copyDirectory 0001 srcDirPath:", str, " dstDirPath:", str2, " makeTopDir:", Boolean.valueOf(z));
            try {
                String canonicalExternalPath = getCanonicalExternalPath(System.getenv("SECONDARY_STORAGE"));
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                    Logger.m4d(DchaUtilService.TAG, "copyDirectory 0002");
                    throw new IllegalArgumentException();
                }
                String canonicalExternalPath2 = getCanonicalExternalPath(str);
                String canonicalExternalPath3 = getCanonicalExternalPath(str2);
                if (canonicalExternalPath2.startsWith(canonicalExternalPath) || canonicalExternalPath3.startsWith(canonicalExternalPath)) {
                    File file = new File(canonicalExternalPath2);
                    File file2 = new File(canonicalExternalPath3);
                    if (!file.isDirectory() || !file2.isDirectory()) {
                        Logger.m4d(DchaUtilService.TAG, "copyDirectory 0005");
                        throw new IllegalArgumentException();
                    }
                    Logger.m4d(DchaUtilService.TAG, "copyDirectory 0004");
                    z2 = directoryCopy(file, file2, z);
                    Logger.m4d(DchaUtilService.TAG, "copyDirectory 0007 result:", Boolean.valueOf(z2));
                    return z2;
                }
                Logger.m4d(DchaUtilService.TAG, "copyDirectory 0003");
                throw new SecurityException("The path is not a external storage.");
            } catch (Exception e) {
                Logger.m4d(DchaUtilService.TAG, "copyDirectory 0006", e);
                z2 = false;
            }
        }

        public boolean copyFile(String str, String str2) {
            boolean z;
            Logger.m4d(DchaUtilService.TAG, "copyFile 0001 srcFilePath:", str, " dstFilePath:", str2);
            try {
                String canonicalExternalPath = getCanonicalExternalPath(System.getenv("SECONDARY_STORAGE"));
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                    Logger.m4d(DchaUtilService.TAG, "copyFile 0002");
                    throw new IllegalArgumentException();
                }
                String canonicalExternalPath2 = getCanonicalExternalPath(str);
                String canonicalExternalPath3 = getCanonicalExternalPath(str2);
                if (canonicalExternalPath2.startsWith(canonicalExternalPath) || canonicalExternalPath3.startsWith(canonicalExternalPath)) {
                    File file = new File(canonicalExternalPath2);
                    File file2 = new File(canonicalExternalPath3);
                    if (!file.isFile() || (!file2.isDirectory() && !file2.isFile() && (file2.exists() || !file2.getParentFile().isDirectory()))) {
                        Logger.m4d(DchaUtilService.TAG, "copyFile 0005");
                        throw new IllegalArgumentException();
                    }
                    Logger.m4d(DchaUtilService.TAG, "copyFile 0004");
                    z = fileCopy(file, file2);
                    Logger.m4d(DchaUtilService.TAG, "copyFile 0007: ", Boolean.valueOf(z));
                    return z;
                }
                Logger.m4d(DchaUtilService.TAG, "copyFile 0003");
                throw new SecurityException("The path is not a external storage.");
            } catch (Exception e) {
                Logger.m4d(DchaUtilService.TAG, "copyFile 0006", e);
                z = false;
            }
        }

        public boolean deleteFile(String str) {
            boolean z;
            Logger.m4d(DchaUtilService.TAG, "deleteFile 0001 path:", str);
            try {
                String canonicalExternalPath = getCanonicalExternalPath(System.getenv("SECONDARY_STORAGE"));
                if (TextUtils.isEmpty(str)) {
                    Logger.m4d(DchaUtilService.TAG, "deleteFile 0002");
                    throw new IllegalArgumentException();
                }
                String canonicalExternalPath2 = getCanonicalExternalPath(str);
                if (!canonicalExternalPath2.startsWith(canonicalExternalPath)) {
                    Logger.m4d(DchaUtilService.TAG, "deleteFile 0003");
                    throw new SecurityException("The path is not a external storage.");
                }
                File file = new File(canonicalExternalPath2);
                if (file.isDirectory()) {
                    Logger.m4d(DchaUtilService.TAG, "deleteFile 0004");
                    File[] listFiles = file.listFiles();
                    if (listFiles == null) {
                        Logger.m4d(DchaUtilService.TAG, "deleteFile 0005");
                        return false;
                    }
                    for (File canonicalPath : listFiles) {
                        if (!deleteFile(canonicalPath.getCanonicalPath())) {
                            Logger.m4d(DchaUtilService.TAG, "deleteFile 0006");
                            return false;
                        }
                    }
                }
                z = file.delete();
                Logger.m4d(DchaUtilService.TAG, "deleteFile 0008 result:", Boolean.valueOf(z));
                return z;
            } catch (Exception e) {
                Logger.m4d(DchaUtilService.TAG, "deleteFile 0007", e);
                z = false;
            }
        }

        /* access modifiers changed from: protected */
        public boolean directoryCopy(File file, File file2, boolean z) {
            Logger.m4d(DchaUtilService.TAG, "directoryCopy 0001");
            if (!file.exists()) {
                Logger.m4d(DchaUtilService.TAG, "directoryCopy 0002");
                return false;
            }
            File[] listFiles = file.listFiles();
            if (z) {
                Logger.m4d(DchaUtilService.TAG, "directoryCopy 0003");
                file2 = new File(file2.getPath() + File.separator + file.getName());
            } else {
                Logger.m4d(DchaUtilService.TAG, "directoryCopy 0004");
            }
            if (!file2.exists()) {
                Logger.m4d(DchaUtilService.TAG, "directoryCopy 0005");
                if (!file2.mkdir()) {
                    Logger.m4d(DchaUtilService.TAG, "directoryCopy 0006");
                    return false;
                }
                file2.setReadable(true, false);
                file2.setWritable(true, false);
                file2.setExecutable(true, false);
            }
            if (listFiles != null) {
                Logger.m4d(DchaUtilService.TAG, "directoryCopy 0007");
                for (File file3 : listFiles) {
                    if (file3.isFile()) {
                        Logger.m4d(DchaUtilService.TAG, "directoryCopy 0008");
                        if (!fileCopy(file3, file2)) {
                            Logger.m4d(DchaUtilService.TAG, "directoryCopy 0009");
                            return false;
                        }
                    } else {
                        Logger.m4d(DchaUtilService.TAG, "directoryCopy 0010");
                        if (!directoryCopy(file3, file2, true)) {
                            Logger.m4d(DchaUtilService.TAG, "directoryCopy 0011");
                            return false;
                        }
                    }
                }
            }
            Logger.m4d(DchaUtilService.TAG, "directoryCopy 0012");
            return true;
        }

        public boolean existsFile(String str) {
            boolean z;
            Logger.m4d(DchaUtilService.TAG, "existsFile 0001 path:", str);
            try {
                String canonicalExternalPath = getCanonicalExternalPath(System.getenv("SECONDARY_STORAGE"));
                if (TextUtils.isEmpty(str)) {
                    Logger.m4d(DchaUtilService.TAG, "existsFile 0002");
                    throw new IllegalArgumentException();
                }
                String canonicalExternalPath2 = getCanonicalExternalPath(str);
                if (!canonicalExternalPath2.startsWith(canonicalExternalPath)) {
                    Logger.m4d(DchaUtilService.TAG, "existsFile 0003");
                    throw new SecurityException("The path is not a external storage.");
                }
                z = new File(canonicalExternalPath2).exists();
                Logger.m4d(DchaUtilService.TAG, "existsFile 0005 result:", Boolean.valueOf(z));
                return z;
            } catch (Exception e) {
                Logger.m4d(DchaUtilService.TAG, "existsFile 0004", e);
                z = false;
            }
        }

        /* access modifiers changed from: protected */
        public boolean fileCopy(File file, File file2) {
            FileInputStream fileInputStream;
            FileChannel fileChannel;
            FileChannel fileChannel2;
            FileOutputStream fileOutputStream;
            FileInputStream fileInputStream2;
            FileChannel fileChannel3;
            FileChannel channel;
            FileChannel fileChannel4 = null;
            Logger.m4d(DchaUtilService.TAG, "fileCopy 0001");
            if (file2.isDirectory()) {
                Logger.m4d(DchaUtilService.TAG, "fileCopy 0002");
                file2 = new File(file2.getPath() + File.separator + file.getName());
            } else {
                Logger.m4d(DchaUtilService.TAG, "fileCopy 0003");
            }
            try {
                file2.createNewFile();
                fileInputStream = new FileInputStream(file);
                try {
                    fileChannel2 = fileInputStream.getChannel();
                } catch (Exception e) {
                    e = e;
                    fileChannel2 = null;
                } catch (Throwable th) {
                    th = th;
                    fileChannel2 = null;
                    fileOutputStream = null;
                    fileChannel = null;
                    Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                    close(fileChannel2);
                    close(fileChannel);
                    close(fileInputStream);
                    close(fileOutputStream);
                    throw th;
                }
                try {
                    fileOutputStream = new FileOutputStream(file2);
                    try {
                        channel = fileOutputStream.getChannel();
                    } catch (Exception e2) {
                        e = e2;
                        fileChannel3 = fileChannel4;
                        fileInputStream2 = fileInputStream;
                        try {
                            Logger.m4d(DchaUtilService.TAG, "fileCopy 0005", e);
                            Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                            close(fileChannel2);
                            close(fileChannel3);
                            close(fileInputStream2);
                            close(fileOutputStream);
                            Logger.m4d(DchaUtilService.TAG, "fileCopy 0007");
                            return false;
                        } catch (Throwable th2) {
                            th = th2;
                            fileInputStream = fileInputStream2;
                            fileChannel = fileChannel3;
                            th = th;
                            Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                            close(fileChannel2);
                            close(fileChannel);
                            close(fileInputStream);
                            close(fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileChannel = fileChannel4;
                        Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                        close(fileChannel2);
                        close(fileChannel);
                        close(fileInputStream);
                        close(fileOutputStream);
                        throw th;
                    }
                    try {
                        fileChannel2.transferTo(0, fileChannel2.size(), channel);
                        file2.setLastModified(file.lastModified());
                        file2.setReadable(true, false);
                        file2.setWritable(true, false);
                        file2.setExecutable(true, false);
                        Logger.m4d(DchaUtilService.TAG, "fileCopy 0004");
                        Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                        close(fileChannel2);
                        close(channel);
                        close(fileInputStream);
                        close(fileOutputStream);
                        return true;
                    } catch (Exception e3) {
                        e = e3;
                        fileChannel4 = channel;
                        fileChannel3 = fileChannel4;
                        fileInputStream2 = fileInputStream;
                        Logger.m4d(DchaUtilService.TAG, "fileCopy 0005", e);
                        Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                        close(fileChannel2);
                        close(fileChannel3);
                        close(fileInputStream2);
                        close(fileOutputStream);
                        Logger.m4d(DchaUtilService.TAG, "fileCopy 0007");
                        return false;
                    } catch (Throwable th4) {
                        th = th4;
                        fileChannel4 = channel;
                        fileChannel = fileChannel4;
                        Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                        close(fileChannel2);
                        close(fileChannel);
                        close(fileInputStream);
                        close(fileOutputStream);
                        throw th;
                    }
                } catch (Exception e4) {
                    e = e4;
                    fileOutputStream = null;
                    fileChannel3 = null;
                    fileInputStream2 = fileInputStream;
                    Logger.m4d(DchaUtilService.TAG, "fileCopy 0005", e);
                    Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                    close(fileChannel2);
                    close(fileChannel3);
                    close(fileInputStream2);
                    close(fileOutputStream);
                    Logger.m4d(DchaUtilService.TAG, "fileCopy 0007");
                    return false;
                } catch (Throwable th5) {
                    th = th5;
                    fileOutputStream = null;
                    fileChannel = null;
                    Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                    close(fileChannel2);
                    close(fileChannel);
                    close(fileInputStream);
                    close(fileOutputStream);
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                fileChannel2 = null;
                fileOutputStream = null;
                fileChannel3 = null;
                fileInputStream2 = null;
                Logger.m4d(DchaUtilService.TAG, "fileCopy 0005", e);
                Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                close(fileChannel2);
                close(fileChannel3);
                close(fileInputStream2);
                close(fileOutputStream);
                Logger.m4d(DchaUtilService.TAG, "fileCopy 0007");
                return false;
            } catch (Throwable th6) {
                th = th6;
                fileOutputStream = null;
                fileInputStream = null;
                fileChannel = null;
                fileChannel2 = null;
                th = th;
                Logger.m4d(DchaUtilService.TAG, "fileCopy 0006");
                close(fileChannel2);
                close(fileChannel);
                close(fileInputStream);
                close(fileOutputStream);
                throw th;
            }
        }

        public String getCanonicalExternalPath(String str) throws RemoteException {
            Logger.m4d(DchaUtilService.TAG, "getCanonicalExternalPath 0001");
            if (TextUtils.isEmpty(str)) {
                Logger.m4d(DchaUtilService.TAG, "getCanonicalExternalPath 0002");
            } else {
                try {
                    str = new File(str).getCanonicalPath();
                } catch (Exception e) {
                    Logger.m4d(DchaUtilService.TAG, "getCanonicalExternalPath 0003", e);
                }
                String str2 = System.getenv("SECONDARY_STORAGE");
                if (!TextUtils.isEmpty(str) && str.startsWith(str2) && Build.VERSION.SDK_INT >= 28) {
                    Logger.m4d(DchaUtilService.TAG, "getCanonicalExternalPath 0004");
                    String externalStoragePath = this.this$0.getExternalStoragePath();
                    if (!TextUtils.isEmpty(externalStoragePath)) {
                        Logger.m4d(DchaUtilService.TAG, "getCanonicalExternalPath 0005");
                        str = str.replace(str2, externalStoragePath);
                    }
                }
                Logger.m4d(DchaUtilService.TAG, "getCanonicalExternalPath 0006 return:", str);
            }
            return str;
        }

        public int[] getDisplaySize() throws RemoteException {
            Logger.m4d(DchaUtilService.TAG, "getDisplaySize 0001");
            int[] iArr = {0, 0};
            try {
                Display defaultDisplay = ((WindowManager) this.this$0.getSystemService("window")).getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getRealSize(point);
                iArr[0] = point.x;
                iArr[1] = point.y;
                Logger.m4d(DchaUtilService.TAG, "getDisplaySize 0003 width:", Integer.valueOf(iArr[0]), " height:", Integer.valueOf(iArr[1]));
                return iArr;
            } catch (Exception e) {
                Logger.m4d(DchaUtilService.TAG, "getDisplaySize 0002", e);
                throw new RemoteException();
            }
        }

        public int[] getLcdSize() throws RemoteException {
            Logger.m4d(DchaUtilService.TAG, "getLcdSize 0001");
            int[] iArr = {0, 0};
            try {
                Point lcdSize = BenesseExtension.getLcdSize();
                iArr[0] = lcdSize.x;
                iArr[1] = lcdSize.y;
                Logger.m4d(DchaUtilService.TAG, "getLcdSize 0003 width:", Integer.valueOf(iArr[0]), " height:", Integer.valueOf(iArr[1]));
                return iArr;
            } catch (Exception e) {
                Logger.m4d(DchaUtilService.TAG, "getLcdSize 0002", e);
                throw new RemoteException();
            }
        }

        public int getUserCount() {
            Logger.m4d(DchaUtilService.TAG, "getUserCount 0001");
            int userCount = ((UserManager) this.this$0.getSystemService("user")).getUserCount();
            Logger.m4d(DchaUtilService.TAG, "getUserCount return:", Integer.valueOf(userCount));
            return userCount;
        }

        public void hideNavigationBar(boolean z) {
            Logger.m4d(DchaUtilService.TAG, "hideNavigationBar 0001 hide:", Boolean.valueOf(z));
            Settings.System.putInt(this.this$0.getContentResolver(), "hide_navigation_bar", z ? 1 : 0);
            Logger.m4d(DchaUtilService.TAG, "hideNavigationBar 0002");
        }

        public String[] listFiles(String str) {
            String[] strArr = null;
            Logger.m4d(DchaUtilService.TAG, "listFiles 0001 path:", str);
            try {
                String canonicalExternalPath = getCanonicalExternalPath(System.getenv("SECONDARY_STORAGE"));
                if (TextUtils.isEmpty(str)) {
                    Logger.m4d(DchaUtilService.TAG, "listFiles 0002");
                    throw new IllegalArgumentException();
                }
                String canonicalExternalPath2 = getCanonicalExternalPath(str);
                if (!canonicalExternalPath2.startsWith(canonicalExternalPath)) {
                    Logger.m4d(DchaUtilService.TAG, "listFiles 0003");
                    throw new SecurityException("The path is not a external storage.");
                }
                File[] listFiles = new File(canonicalExternalPath2).listFiles();
                if (listFiles != null) {
                    Logger.m4d(DchaUtilService.TAG, "listFiles 0004");
                    strArr = new String[listFiles.length];
                    int length = listFiles.length;
                    int i = 0;
                    int i2 = 0;
                    while (i2 < length) {
                        strArr[i] = listFiles[i2].getCanonicalPath();
                        i2++;
                        i++;
                    }
                }
                Logger.m4d(DchaUtilService.TAG, "listFiles 0006");
                return strArr;
            } catch (Exception e) {
                Logger.m4d(DchaUtilService.TAG, "listFiles 0005", e);
            }
        }

        public boolean makeDir(String str, String str2) {
            boolean z;
            Logger.m4d(DchaUtilService.TAG, "makeDir 0001 dirname:", str2);
            try {
                String canonicalExternalPath = getCanonicalExternalPath(System.getenv("SECONDARY_STORAGE"));
                if (TextUtils.isEmpty(str)) {
                    Logger.m4d(DchaUtilService.TAG, "makeDir 0002");
                    throw new IllegalArgumentException();
                }
                String canonicalExternalPath2 = getCanonicalExternalPath(str);
                if (!canonicalExternalPath2.startsWith(canonicalExternalPath)) {
                    Logger.m4d(DchaUtilService.TAG, "makeDir 0003");
                    throw new SecurityException("The path is not a external storage.");
                }
                File file = new File(canonicalExternalPath2);
                if (file.isDirectory()) {
                    Logger.m4d(DchaUtilService.TAG, "makeDir 0004");
                    File file2 = new File(file, str2);
                    z = file2.mkdir();
                    try {
                        file2.setReadable(true, false);
                        file2.setWritable(true, false);
                        file2.setExecutable(true, false);
                    } catch (Exception e) {
                        e = e;
                    }
                } else {
                    z = false;
                }
                Logger.m4d(DchaUtilService.TAG, "makeDir 0006 result:", Boolean.valueOf(z));
                return z;
            } catch (Exception e2) {
                e = e2;
                z = false;
                Logger.m4d(DchaUtilService.TAG, "makeDir 0005", e);
                Logger.m4d(DchaUtilService.TAG, "makeDir 0006 result:", Boolean.valueOf(z));
                return z;
            }
        }

        public void sdUnmount() throws RemoteException {
            Logger.m4d(DchaUtilService.TAG, "sdUnmount 0001");
            this.this$0.unmountExternalStorage();
            Logger.m4d(DchaUtilService.TAG, "sdUnmount 0002");
        }

        public void setDefaultPreferredHomeApp(String str) throws RemoteException {
            try {
                Logger.m4d(DchaUtilService.TAG, "setDefalutPreferredHomeApp 0001 packageName:", str);
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
                    Logger.m4d(DchaUtilService.TAG, "setDefalutPreferredHomeApp packName:" + str2);
                    Logger.m4d(DchaUtilService.TAG, "setDefalutPreferredHomeApp activityName:" + str3);
                    ComponentName componentName2 = new ComponentName(str2, str3);
                    arrayList.add(componentName2);
                    if (str2.equalsIgnoreCase(str)) {
                        Logger.m4d(DchaUtilService.TAG, "setDefalutPreferredHomeApp 0002");
                        Logger.m4d(DchaUtilService.TAG, "setDefalutPreferredHomeApp defaultHomeComponentName:" + componentName2);
                        componentName = componentName2;
                    }
                }
                Object obj = (ComponentName[]) arrayList.toArray(new ComponentName[arrayList.size()]);
                if (componentName != null) {
                    Logger.m4d(DchaUtilService.TAG, "setDefalutPreferredHomeApp 0003");
                    Method method = null;
                    try {
                        method = PackageManager.class.getDeclaredMethod("addPreferredActivity", new Class[]{IntentFilter.class, Integer.TYPE, ComponentName[].class, ComponentName.class, Integer.TYPE});
                    } catch (NoSuchMethodException e) {
                    }
                    if (method == null) {
                        method = PackageManager.class.getDeclaredMethod("addPreferredActivityAsUser", new Class[]{IntentFilter.class, Integer.TYPE, ComponentName[].class, ComponentName.class, Integer.TYPE});
                    }
                    method.setAccessible(true);
                    method.invoke(packageManager, new Object[]{intentFilter, 1081344, obj, componentName, 0});
                }
                Logger.m4d(DchaUtilService.TAG, "setDefalutPreferredHomeApp 0005");
            } catch (Exception e2) {
                Logger.m6e(DchaUtilService.TAG, "setDefalutPrefferredHomeApp 0004", e2);
                throw new RemoteException(e2.toString());
            }
        }

        public boolean setForcedDisplaySize(int i, int i2) {
            boolean z;
            Logger.m4d(DchaUtilService.TAG, "setForcedDisplaySize 0001 width:", Integer.valueOf(i), " height:", Integer.valueOf(i2));
            try {
                z = BenesseExtension.setForcedDisplaySize(i, i2);
            } catch (Exception e) {
                Logger.m4d(DchaUtilService.TAG, "setForcedDisplaySize 0002", e);
                z = false;
            }
            Logger.m4d(DchaUtilService.TAG, "setForcedDisplaySize 0003 result:", Boolean.valueOf(z));
            return z;
        }
    };

    public String getExternalStoragePath() {
        Logger.m4d(TAG, "getExternalStoragePath 0001");
        String str = "";
        if (Build.VERSION.SDK_INT >= 28) {
            Logger.m4d(TAG, "getExternalStoragePath 0002");
            str = StorageManagerAdapter.getPath(getApplicationContext());
        }
        Logger.m4d(TAG, "getExternalStoragePath 0003 return:", str);
        return str;
    }

    public IBinder onBind(Intent intent) {
        return this.mDchaUtilServiceStub;
    }

    public void unmountExternalStorage() throws RemoteException {
        Logger.m4d(TAG, "unmountExternalStorage 0001");
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                Logger.m4d(TAG, "unmountExternalStorage 0002");
                StorageManagerAdapter.unmount(getApplicationContext());
            } else {
                Logger.m4d(TAG, "unmountExternalStorage 0003");
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                String str = System.getenv("SECONDARY_STORAGE");
                Object invokeDeclaredMethod = ReflectionUtils.invokeDeclaredMethod(systemClassLoader.loadClass("android.os.ServiceManager"), (Object) null, "getService", new Class[]{String.class}, new Object[]{"mount"});
                Object invokeDeclaredMethod2 = ReflectionUtils.invokeDeclaredMethod(systemClassLoader.loadClass("android.os.storage.IMountService$Stub"), (Object) null, "asInterface", new Class[]{IBinder.class}, new Object[]{invokeDeclaredMethod});
                Class cls = Boolean.TYPE;
                Class cls2 = Boolean.TYPE;
                ReflectionUtils.invokeDeclaredMethod(systemClassLoader.loadClass("android.os.storage.IMountService"), invokeDeclaredMethod2, "unmountVolume", new Class[]{String.class, cls, cls2}, new Object[]{str, true, false});
            }
            Logger.m4d(TAG, "unmountExternalStorage 0005");
        } catch (Exception e) {
            Logger.m4d(TAG, "unmountExternalStorage 0004", e);
            throw new RemoteException();
        }
    }
}
