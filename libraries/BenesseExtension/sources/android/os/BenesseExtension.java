package android.os;

import android.os.IBenesseExtensionService;
import java.io.File;
/* loaded from: BenesseExtension.jar:android/os/BenesseExtension.class */
public class BenesseExtension {
    static IBenesseExtensionService mBenesseExtensionService;
    public static final File IGNORE_DCHA_COMPLETED_FILE = new File("/factory/ignore_dcha_completed");
    public static final File COUNT_DCHA_COMPLETED_FILE = new File("/factory/count_dcha_completed");

    BenesseExtension() {
    }

    public static boolean checkPassword(String str) {
        if (getBenesseExtensionService() == null) {
            return false;
        }
        try {
            return mBenesseExtensionService.checkPassword(str);
        } catch (RemoteException e) {
            return false;
        }
    }

    static IBenesseExtensionService getBenesseExtensionService() {
        if (mBenesseExtensionService == null) {
            mBenesseExtensionService = IBenesseExtensionService.Stub.asInterface(ServiceManager.getService("benesse_extension"));
        }
        return mBenesseExtensionService;
    }

    public static int getDchaState() {
        if (getBenesseExtensionService() == null) {
            return 0;
        }
        try {
            return mBenesseExtensionService.getDchaState();
        } catch (RemoteException e) {
            return 0;
        }
    }

    public static int getInt(String str) {
        if (getBenesseExtensionService() == null) {
            return -1;
        }
        try {
            return mBenesseExtensionService.getInt(str);
        } catch (RemoteException e) {
            return -1;
        }
    }

    public static String getString(String str) {
        if (getBenesseExtensionService() == null) {
            return null;
        }
        try {
            return mBenesseExtensionService.getString(str);
        } catch (RemoteException e) {
            return null;
        }
    }

    public static boolean putInt(String str, int i) {
        if (getBenesseExtensionService() == null) {
            return false;
        }
        try {
            return mBenesseExtensionService.putInt(str, i);
        } catch (RemoteException e) {
            return false;
        }
    }

    public static boolean putString(String str, String str2) {
        if (getBenesseExtensionService() == null) {
            return false;
        }
        try {
            return mBenesseExtensionService.putString(str, str2);
        } catch (RemoteException e) {
            return false;
        }
    }

    public static void setDchaState(int i) {
        if (getBenesseExtensionService() == null) {
            return;
        }
        try {
            mBenesseExtensionService.setDchaState(i);
        } catch (RemoteException e) {
        }
    }
}
