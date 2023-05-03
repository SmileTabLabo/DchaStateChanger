package android.p000os;

/* renamed from: android.os.IBenesseExtensionService */
public interface IBenesseExtensionService extends IInterface {

    /* renamed from: android.os.IBenesseExtensionService$Stub */
    public static abstract class Stub extends Binder implements IBenesseExtensionService {
        private static final String DESCRIPTOR = "android.os.IBenesseExtensionService";
        static final int TRANSACTION_checkPassword = 6;
        static final int TRANSACTION_getDchaState = 1;
        static final int TRANSACTION_getInt = 4;
        static final int TRANSACTION_getString = 3;
        static final int TRANSACTION_putInt = 5;
        static final int TRANSACTION_setDchaState = 2;

        /* renamed from: android.os.IBenesseExtensionService$Stub$Proxy */
        private static class Proxy implements IBenesseExtensionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public boolean checkPassword(String str) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_checkPassword, obtain, obtain2, 0);
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

            public int getDchaState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getDchaState, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getInt(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_getInt, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public String getString(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_getString, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean putInt(String str, int i) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_putInt, obtain, obtain2, 0);
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

            public void setDchaState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setDchaState, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBenesseExtensionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IBenesseExtensionService)) ? new Proxy(iBinder) : (IBenesseExtensionService) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case TRANSACTION_getDchaState /*1*/:
                        parcel.enforceInterface(DESCRIPTOR);
                        int dchaState = getDchaState();
                        parcel2.writeNoException();
                        parcel2.writeInt(dchaState);
                        return true;
                    case TRANSACTION_setDchaState /*2*/:
                        parcel.enforceInterface(DESCRIPTOR);
                        setDchaState(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case TRANSACTION_getString /*3*/:
                        parcel.enforceInterface(DESCRIPTOR);
                        String string = getString(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(string);
                        return true;
                    case TRANSACTION_getInt /*4*/:
                        parcel.enforceInterface(DESCRIPTOR);
                        int i3 = getInt(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(i3);
                        return true;
                    case TRANSACTION_putInt /*5*/:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean putInt = putInt(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(putInt ? 1 : 0);
                        return true;
                    case TRANSACTION_checkPassword /*6*/:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean checkPassword = checkPassword(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(checkPassword ? 1 : 0);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }
    }

    boolean checkPassword(String str) throws RemoteException;

    int getDchaState() throws RemoteException;

    int getInt(String str) throws RemoteException;

    String getString(String str) throws RemoteException;

    boolean putInt(String str, int i) throws RemoteException;

    void setDchaState(int i) throws RemoteException;
}
