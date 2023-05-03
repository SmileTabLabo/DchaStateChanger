package p000jp.p001co.benesse.dcha.dchautilservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: jp.co.benesse.dcha.dchautilservice.IDchaUtilService */
public interface IDchaUtilService extends IInterface {

    /* renamed from: jp.co.benesse.dcha.dchautilservice.IDchaUtilService$Stub */
    public static abstract class Stub extends Binder implements IDchaUtilService {
        private static final String DESCRIPTOR = "jp.co.benesse.dcha.dchautilservice.IDchaUtilService";
        static final int TRANSACTION_clearDefaultPreferredApp = 12;
        static final int TRANSACTION_copyDirectory = 6;
        static final int TRANSACTION_copyFile = 5;
        static final int TRANSACTION_deleteFile = 7;
        static final int TRANSACTION_existsFile = 15;
        static final int TRANSACTION_getCanonicalExternalPath = 13;
        static final int TRANSACTION_getDisplaySize = 2;
        static final int TRANSACTION_getLcdSize = 3;
        static final int TRANSACTION_getUserCount = 10;
        static final int TRANSACTION_hideNavigationBar = 9;
        static final int TRANSACTION_listFiles = 14;
        static final int TRANSACTION_makeDir = 8;
        static final int TRANSACTION_sdUnmount = 4;
        static final int TRANSACTION_setDefaultPreferredHomeApp = 11;
        static final int TRANSACTION_setForcedDisplaySize = 1;

        /* renamed from: jp.co.benesse.dcha.dchautilservice.IDchaUtilService$Stub$Proxy */
        private static class Proxy implements IDchaUtilService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

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

            public boolean copyDirectory(String str, String str2, boolean z) throws RemoteException {
                boolean z2 = true;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(z ? Stub.TRANSACTION_setForcedDisplaySize : 0);
                    this.mRemote.transact(Stub.TRANSACTION_copyDirectory, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z2 = false;
                    }
                    return z2;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

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
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean deleteFile(String str) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean existsFile(String str) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_existsFile, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getCanonicalExternalPath(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_getCanonicalExternalPath, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int[] getDisplaySize() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public int[] getLcdSize() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getLcdSize, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

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

            public void hideNavigationBar(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (z) {
                        i = Stub.TRANSACTION_setForcedDisplaySize;
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_hideNavigationBar, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String[] listFiles(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_listFiles, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean makeDir(String str, String str2) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(Stub.TRANSACTION_makeDir, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

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

            public boolean setForcedDisplaySize(int i, int i2) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(Stub.TRANSACTION_setForcedDisplaySize, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = Stub.TRANSACTION_setForcedDisplaySize;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDchaUtilService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDchaUtilService)) ? new Proxy(iBinder) : (IDchaUtilService) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z = false;
            switch (i) {
                case TRANSACTION_setForcedDisplaySize /*1*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean forcedDisplaySize = setForcedDisplaySize(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (forcedDisplaySize) {
                        z = TRANSACTION_setForcedDisplaySize;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int[] displaySize = getDisplaySize();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displaySize);
                    return true;
                case TRANSACTION_getLcdSize /*3*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    int[] lcdSize = getLcdSize();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(lcdSize);
                    return true;
                case TRANSACTION_sdUnmount /*4*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    sdUnmount();
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_copyFile /*5*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean copyFile = copyFile(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    if (copyFile) {
                        z = TRANSACTION_setForcedDisplaySize;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case TRANSACTION_copyDirectory /*6*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean copyDirectory = copyDirectory(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? TRANSACTION_setForcedDisplaySize : false);
                    parcel2.writeNoException();
                    if (copyDirectory) {
                        z = TRANSACTION_setForcedDisplaySize;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean deleteFile = deleteFile(parcel.readString());
                    parcel2.writeNoException();
                    if (deleteFile) {
                        z = TRANSACTION_setForcedDisplaySize;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case TRANSACTION_makeDir /*8*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean makeDir = makeDir(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    if (makeDir) {
                        z = TRANSACTION_setForcedDisplaySize;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case TRANSACTION_hideNavigationBar /*9*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    if (parcel.readInt() != 0) {
                        z = TRANSACTION_setForcedDisplaySize;
                    }
                    hideNavigationBar(z);
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_getUserCount /*10*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    int userCount = getUserCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(userCount);
                    return true;
                case TRANSACTION_setDefaultPreferredHomeApp /*11*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    setDefaultPreferredHomeApp(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_clearDefaultPreferredApp /*12*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    clearDefaultPreferredApp(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_getCanonicalExternalPath /*13*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    String canonicalExternalPath = getCanonicalExternalPath(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(canonicalExternalPath);
                    return true;
                case TRANSACTION_listFiles /*14*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    String[] listFiles = listFiles(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listFiles);
                    return true;
                case TRANSACTION_existsFile /*15*/:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean existsFile = existsFile(parcel.readString());
                    parcel2.writeNoException();
                    if (existsFile) {
                        z = TRANSACTION_setForcedDisplaySize;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void clearDefaultPreferredApp(String str) throws RemoteException;

    boolean copyDirectory(String str, String str2, boolean z) throws RemoteException;

    boolean copyFile(String str, String str2) throws RemoteException;

    boolean deleteFile(String str) throws RemoteException;

    boolean existsFile(String str) throws RemoteException;

    String getCanonicalExternalPath(String str) throws RemoteException;

    int[] getDisplaySize() throws RemoteException;

    int[] getLcdSize() throws RemoteException;

    int getUserCount() throws RemoteException;

    void hideNavigationBar(boolean z) throws RemoteException;

    String[] listFiles(String str) throws RemoteException;

    boolean makeDir(String str, String str2) throws RemoteException;

    void sdUnmount() throws RemoteException;

    void setDefaultPreferredHomeApp(String str) throws RemoteException;

    boolean setForcedDisplaySize(int i, int i2) throws RemoteException;
}
