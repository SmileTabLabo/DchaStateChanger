package jp.co.benesse.dcha.dchautilservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
/* loaded from: classes.dex */
public interface IDchaUtilService extends IInterface {
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

    /* loaded from: classes.dex */
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

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDchaUtilService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDchaUtilService)) {
                return (IDchaUtilService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    boolean _result = setForcedDisplaySize(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _result2 = getDisplaySize();
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case TRANSACTION_getLcdSize /* 3 */:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _result3 = getLcdSize();
                    reply.writeNoException();
                    reply.writeIntArray(_result3);
                    return true;
                case TRANSACTION_sdUnmount /* 4 */:
                    data.enforceInterface(DESCRIPTOR);
                    sdUnmount();
                    reply.writeNoException();
                    return true;
                case TRANSACTION_copyFile /* 5 */:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    boolean _result4 = copyFile(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result4 ? 1 : 0);
                    return true;
                case TRANSACTION_copyDirectory /* 6 */:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    String _arg13 = data.readString();
                    boolean _arg2 = data.readInt() != 0;
                    boolean _result5 = copyDirectory(_arg03, _arg13, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result5 ? 1 : 0);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg04 = data.readString();
                    boolean _result6 = deleteFile(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result6 ? 1 : 0);
                    return true;
                case TRANSACTION_makeDir /* 8 */:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg05 = data.readString();
                    String _arg14 = data.readString();
                    boolean _result7 = makeDir(_arg05, _arg14);
                    reply.writeNoException();
                    reply.writeInt(_result7 ? 1 : 0);
                    return true;
                case TRANSACTION_hideNavigationBar /* 9 */:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg06 = data.readInt() != 0;
                    hideNavigationBar(_arg06);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getUserCount /* 10 */:
                    data.enforceInterface(DESCRIPTOR);
                    int _result8 = getUserCount();
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case TRANSACTION_setDefaultPreferredHomeApp /* 11 */:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg07 = data.readString();
                    setDefaultPreferredHomeApp(_arg07);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_clearDefaultPreferredApp /* 12 */:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg08 = data.readString();
                    clearDefaultPreferredApp(_arg08);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getCanonicalExternalPath /* 13 */:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    String _result9 = getCanonicalExternalPath(_arg09);
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case TRANSACTION_listFiles /* 14 */:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg010 = data.readString();
                    String[] _result10 = listFiles(_arg010);
                    reply.writeNoException();
                    reply.writeStringArray(_result10);
                    return true;
                case TRANSACTION_existsFile /* 15 */:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg011 = data.readString();
                    boolean _result11 = existsFile(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result11 ? 1 : 0);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IDchaUtilService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public boolean setForcedDisplaySize(int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public int[] getDisplaySize() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public int[] getLcdSize() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getLcdSize, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public void sdUnmount() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_sdUnmount, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public boolean copyFile(String srcFilePath, String dstFilePath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(srcFilePath);
                    _data.writeString(dstFilePath);
                    this.mRemote.transact(Stub.TRANSACTION_copyFile, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public boolean copyDirectory(String srcDirPath, String dstDirPath, boolean makeTopDir) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(srcDirPath);
                    _data.writeString(dstDirPath);
                    _data.writeInt(makeTopDir ? 1 : 0);
                    this.mRemote.transact(Stub.TRANSACTION_copyDirectory, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public boolean deleteFile(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public boolean makeDir(String path, String dirname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeString(dirname);
                    this.mRemote.transact(Stub.TRANSACTION_makeDir, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public void hideNavigationBar(boolean hide) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hide ? 1 : 0);
                    this.mRemote.transact(Stub.TRANSACTION_hideNavigationBar, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public int getUserCount() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getUserCount, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public void setDefaultPreferredHomeApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(Stub.TRANSACTION_setDefaultPreferredHomeApp, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public void clearDefaultPreferredApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(Stub.TRANSACTION_clearDefaultPreferredApp, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public String getCanonicalExternalPath(String linkPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(linkPath);
                    this.mRemote.transact(Stub.TRANSACTION_getCanonicalExternalPath, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public String[] listFiles(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(Stub.TRANSACTION_listFiles, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // jp.co.benesse.dcha.dchautilservice.IDchaUtilService
            public boolean existsFile(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(Stub.TRANSACTION_existsFile, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
