package jp.co.benesse.dcha.dchaservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
/* loaded from: DchaService.jar:jp/co/benesse/dcha/dchaservice/IDchaService.class */
public interface IDchaService extends IInterface {

    /* loaded from: DchaService.jar:jp/co/benesse/dcha/dchaservice/IDchaService$Stub.class */
    public static abstract class Stub extends Binder implements IDchaService {
        private static final String DESCRIPTOR = "jp.co.benesse.dcha.dchaservice.IDchaService";
        static final int TRANSACTION_cancelSetup = 10;
        static final int TRANSACTION_checkPadRooted = 7;
        static final int TRANSACTION_clearDefaultPreferredApp = 5;
        static final int TRANSACTION_copyFile = 18;
        static final int TRANSACTION_copyUpdateImage = 2;
        static final int TRANSACTION_deleteFile = 19;
        static final int TRANSACTION_disableADB = 6;
        static final int TRANSACTION_getForegroundPackageName = 17;
        static final int TRANSACTION_getSetupStatus = 12;
        static final int TRANSACTION_getUserCount = 20;
        static final int TRANSACTION_hideNavigationBar = 22;
        static final int TRANSACTION_installApp = 8;
        static final int TRANSACTION_isDeviceEncryptionEnabled = 21;
        static final int TRANSACTION_rebootPad = 3;
        static final int TRANSACTION_removeTask = 14;
        static final int TRANSACTION_sdUnmount = 15;
        static final int TRANSACTION_setDefaultParam = 16;
        static final int TRANSACTION_setDefaultPreferredHomeApp = 4;
        static final int TRANSACTION_setPermissionEnforced = 23;
        static final int TRANSACTION_setSetupStatus = 11;
        static final int TRANSACTION_setSystemTime = 13;
        static final int TRANSACTION_uninstallApp = 9;
        static final int TRANSACTION_verifyUpdateImage = 1;

        /* loaded from: DchaService.jar:jp/co/benesse/dcha/dchaservice/IDchaService$Stub$Proxy.class */
        private static class Proxy implements IDchaService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void cancelSetup() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_cancelSetup, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public boolean checkPadRooted() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_checkPadRooted, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void clearDefaultPreferredApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_clearDefaultPreferredApp, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public boolean copyFile(String str, String str2) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(Stub.TRANSACTION_copyFile, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public boolean copyUpdateImage(String str, String str2) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(Stub.TRANSACTION_copyUpdateImage, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public boolean deleteFile(String str) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_deleteFile, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void disableADB() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_disableADB, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public String getForegroundPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getForegroundPackageName, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public int getSetupStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getSetupStatus, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public int getUserCount() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getUserCount, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void hideNavigationBar(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (z) {
                        i = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_hideNavigationBar, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public boolean installApp(String str, int i) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_installApp, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public boolean isDeviceEncryptionEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isDeviceEncryptionEnabled, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void rebootPad(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_rebootPad, obtain, null, Stub.TRANSACTION_verifyUpdateImage);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void removeTask(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_removeTask, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void sdUnmount() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_sdUnmount, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void setDefaultParam() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_setDefaultParam, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void setDefaultPreferredHomeApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_setDefaultPreferredHomeApp, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void setPermissionEnforced(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (z) {
                        i = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setPermissionEnforced, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void setSetupStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setSetupStatus, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public void setSystemTime(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(Stub.TRANSACTION_setSystemTime, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public boolean uninstallApp(String str, int i) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_uninstallApp, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = Stub.TRANSACTION_verifyUpdateImage;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // jp.co.benesse.dcha.dchaservice.IDchaService
            public boolean verifyUpdateImage(String str) throws RemoteException {
                boolean z = Stub.TRANSACTION_verifyUpdateImage;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_verifyUpdateImage, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v8, types: [jp.co.benesse.dcha.dchaservice.IDchaService] */
        public static IDchaService asInterface(IBinder iBinder) {
            Proxy proxy;
            if (iBinder == null) {
                proxy = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
                proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IDchaService)) ? new Proxy(iBinder) : (IDchaService) queryLocalInterface;
            }
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z;
            switch (i) {
                case TRANSACTION_verifyUpdateImage /* 1 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean verifyUpdateImage = verifyUpdateImage(parcel.readString());
                    parcel2.writeNoException();
                    int i3 = 0;
                    if (verifyUpdateImage) {
                        i3 = TRANSACTION_verifyUpdateImage;
                    }
                    parcel2.writeInt(i3);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_copyUpdateImage /* 2 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean copyUpdateImage = copyUpdateImage(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    int i4 = 0;
                    if (copyUpdateImage) {
                        i4 = TRANSACTION_verifyUpdateImage;
                    }
                    parcel2.writeInt(i4);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_rebootPad /* 3 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    rebootPad(parcel.readInt(), parcel.readString());
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_setDefaultPreferredHomeApp /* 4 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    setDefaultPreferredHomeApp(parcel.readString());
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_clearDefaultPreferredApp /* 5 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    clearDefaultPreferredApp(parcel.readString());
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_disableADB /* 6 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    disableADB();
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_checkPadRooted /* 7 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean checkPadRooted = checkPadRooted();
                    parcel2.writeNoException();
                    int i5 = 0;
                    if (checkPadRooted) {
                        i5 = TRANSACTION_verifyUpdateImage;
                    }
                    parcel2.writeInt(i5);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_installApp /* 8 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean installApp = installApp(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    int i6 = 0;
                    if (installApp) {
                        i6 = TRANSACTION_verifyUpdateImage;
                    }
                    parcel2.writeInt(i6);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_uninstallApp /* 9 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean uninstallApp = uninstallApp(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    int i7 = 0;
                    if (uninstallApp) {
                        i7 = TRANSACTION_verifyUpdateImage;
                    }
                    parcel2.writeInt(i7);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_cancelSetup /* 10 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    cancelSetup();
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_setSetupStatus /* 11 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    setSetupStatus(parcel.readInt());
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_getSetupStatus /* 12 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    int setupStatus = getSetupStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(setupStatus);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_setSystemTime /* 13 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    setSystemTime(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_removeTask /* 14 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    removeTask(parcel.readString());
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_sdUnmount /* 15 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    sdUnmount();
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_setDefaultParam /* 16 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    setDefaultParam();
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_getForegroundPackageName /* 17 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    String foregroundPackageName = getForegroundPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(foregroundPackageName);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_copyFile /* 18 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean copyFile = copyFile(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    int i8 = 0;
                    if (copyFile) {
                        i8 = TRANSACTION_verifyUpdateImage;
                    }
                    parcel2.writeInt(i8);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_deleteFile /* 19 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean deleteFile = deleteFile(parcel.readString());
                    parcel2.writeNoException();
                    int i9 = 0;
                    if (deleteFile) {
                        i9 = TRANSACTION_verifyUpdateImage;
                    }
                    parcel2.writeInt(i9);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_getUserCount /* 20 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    int userCount = getUserCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(userCount);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_isDeviceEncryptionEnabled /* 21 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isDeviceEncryptionEnabled = isDeviceEncryptionEnabled();
                    parcel2.writeNoException();
                    int i10 = 0;
                    if (isDeviceEncryptionEnabled) {
                        i10 = TRANSACTION_verifyUpdateImage;
                    }
                    parcel2.writeInt(i10);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_hideNavigationBar /* 22 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    hideNavigationBar(parcel.readInt() != 0 ? TRANSACTION_verifyUpdateImage : false);
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case TRANSACTION_setPermissionEnforced /* 23 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    setPermissionEnforced(parcel.readInt() != 0 ? TRANSACTION_verifyUpdateImage : false);
                    parcel2.writeNoException();
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    z = TRANSACTION_verifyUpdateImage;
                    break;
                default:
                    z = super.onTransact(i, parcel, parcel2, i2);
                    break;
            }
            return z;
        }
    }

    void cancelSetup() throws RemoteException;

    boolean checkPadRooted() throws RemoteException;

    void clearDefaultPreferredApp(String str) throws RemoteException;

    boolean copyFile(String str, String str2) throws RemoteException;

    boolean copyUpdateImage(String str, String str2) throws RemoteException;

    boolean deleteFile(String str) throws RemoteException;

    void disableADB() throws RemoteException;

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
