package p000jp.p001co.benesse.dcha.dchaservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import p000jp.p001co.benesse.dcha.util.Logger;

/* renamed from: jp.co.benesse.dcha.dchaservice.IDchaService */
public interface IDchaService extends IInterface {

    /* renamed from: jp.co.benesse.dcha.dchaservice.IDchaService$Stub */
    public static abstract class Stub extends Binder implements IDchaService {
        public Stub() {
            attachInterface(this, "jp.co.benesse.dcha.dchaservice.IDchaService");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z = false;
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        boolean verifyUpdateImage = verifyUpdateImage(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(verifyUpdateImage ? 1 : 0);
                        return true;
                    case Logger.ALL /*2*/:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        boolean copyUpdateImage = copyUpdateImage(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(copyUpdateImage ? 1 : 0);
                        return true;
                    case 3:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        rebootPad(parcel.readInt(), parcel.readString());
                        return true;
                    case 4:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        setDefaultPreferredHomeApp(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        clearDefaultPreferredApp(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        disableADB();
                        parcel2.writeNoException();
                        return true;
                    case Logger.NONE /*7*/:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        boolean checkPadRooted = checkPadRooted();
                        parcel2.writeNoException();
                        parcel2.writeInt(checkPadRooted ? 1 : 0);
                        return true;
                    case 8:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        boolean installApp = installApp(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(installApp ? 1 : 0);
                        return true;
                    case 9:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        boolean uninstallApp = uninstallApp(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(uninstallApp ? 1 : 0);
                        return true;
                    case 10:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        cancelSetup();
                        parcel2.writeNoException();
                        return true;
                    case 11:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        setSetupStatus(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 12:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        int setupStatus = getSetupStatus();
                        parcel2.writeNoException();
                        parcel2.writeInt(setupStatus);
                        return true;
                    case 13:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        setSystemTime(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 14:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        removeTask(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 15:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        sdUnmount();
                        parcel2.writeNoException();
                        return true;
                    case 16:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        setDefaultParam();
                        parcel2.writeNoException();
                        return true;
                    case 17:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        String foregroundPackageName = getForegroundPackageName();
                        parcel2.writeNoException();
                        parcel2.writeString(foregroundPackageName);
                        return true;
                    case 18:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        boolean copyFile = copyFile(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(copyFile ? 1 : 0);
                        return true;
                    case 19:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        boolean deleteFile = deleteFile(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(deleteFile ? 1 : 0);
                        return true;
                    case 20:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        int userCount = getUserCount();
                        parcel2.writeNoException();
                        parcel2.writeInt(userCount);
                        return true;
                    case 21:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        boolean isDeviceEncryptionEnabled = isDeviceEncryptionEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isDeviceEncryptionEnabled ? 1 : 0);
                        return true;
                    case 22:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        hideNavigationBar(z);
                        parcel2.writeNoException();
                        return true;
                    case 23:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setPermissionEnforced(z);
                        parcel2.writeNoException();
                        return true;
                    case 24:
                        parcel.enforceInterface("jp.co.benesse.dcha.dchaservice.IDchaService");
                        String canonicalExternalPath = getCanonicalExternalPath(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(canonicalExternalPath);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("jp.co.benesse.dcha.dchaservice.IDchaService");
                return true;
            }
        }
    }

    void cancelSetup() throws RemoteException;

    boolean checkPadRooted() throws RemoteException;

    void clearDefaultPreferredApp(String str) throws RemoteException;

    boolean copyFile(String str, String str2) throws RemoteException;

    boolean copyUpdateImage(String str, String str2) throws RemoteException;

    boolean deleteFile(String str) throws RemoteException;

    void disableADB() throws RemoteException;

    String getCanonicalExternalPath(String str) throws RemoteException;

    String getForegroundPackageName() throws RemoteException;

    int getSetupStatus() throws RemoteException;

    int getUserCount() throws RemoteException;

    void hideNavigationBar(boolean z) throws RemoteException;

    boolean installApp(String str, int i) throws RemoteException;

    boolean isDeviceEncryptionEnabled() throws RemoteException;

    void rebootPad(int i, String str) throws RemoteException;

    void removeTask(String str) throws RemoteException;

    void sdUnmount() throws RemoteException;

    void setDefaultParam() throws RemoteException;

    void setDefaultPreferredHomeApp(String str) throws RemoteException;

    void setPermissionEnforced(boolean z) throws RemoteException;

    void setSetupStatus(int i) throws RemoteException;

    void setSystemTime(String str, String str2) throws RemoteException;

    boolean uninstallApp(String str, int i) throws RemoteException;

    boolean verifyUpdateImage(String str) throws RemoteException;
}
