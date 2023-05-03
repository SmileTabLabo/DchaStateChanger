package p000jp.p001co.benesse.dcha.util;

import android.content.Context;
import android.os.RemoteException;
import android.os.storage.StorageManager;
import java.util.Iterator;
import java.util.List;

/* renamed from: jp.co.benesse.dcha.util.StorageManagerAdapter */
public class StorageManagerAdapter {
    public static final String TAG = StorageManagerAdapter.class.getSimpleName();

    private StorageManagerAdapter() {
    }

    public static final String getPath(Context context) {
        String str;
        Logger.m4d(TAG, "getPath 0001");
        try {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            Iterator it = ((List) ReflectionUtils.invokeDeclaredMethod(systemClassLoader.loadClass("android.os.storage.StorageManager"), (StorageManager) context.getSystemService("storage"), "getStorageVolumes", (Class<?>[]) null, (Object[]) null)).iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = "";
                    break;
                }
                Object next = it.next();
                Class<?> loadClass = systemClassLoader.loadClass("android.os.storage.StorageVolume");
                if (((Boolean) ReflectionUtils.invokeDeclaredMethod(loadClass, next, "isRemovable", (Class<?>[]) null, (Object[]) null)).booleanValue()) {
                    Logger.m4d(TAG, "getPath 0002");
                    str = (String) ReflectionUtils.invokeDeclaredMethod(loadClass, next, "getInternalPath", (Class<?>[]) null, (Object[]) null);
                    break;
                }
            }
        } catch (Exception e) {
            Logger.m4d(TAG, "getPath 0003", e);
            str = "";
        }
        Logger.m4d(TAG, "getPath 0004 path:", str);
        return str;
    }

    public static final String getState(Context context) {
        String str;
        Logger.m4d(TAG, "getState 0001");
        try {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            Iterator it = ((List) ReflectionUtils.invokeDeclaredMethod(systemClassLoader.loadClass("android.os.storage.StorageManager"), (StorageManager) context.getSystemService("storage"), "getStorageVolumes", (Class<?>[]) null, (Object[]) null)).iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = "unknown";
                    break;
                }
                Object next = it.next();
                Class<?> loadClass = systemClassLoader.loadClass("android.os.storage.StorageVolume");
                if (((Boolean) ReflectionUtils.invokeDeclaredMethod(loadClass, next, "isRemovable", (Class<?>[]) null, (Object[]) null)).booleanValue()) {
                    Logger.m4d(TAG, "getState 0002");
                    str = (String) ReflectionUtils.invokeDeclaredMethod(loadClass, next, "getState", (Class<?>[]) null, (Object[]) null);
                    break;
                }
            }
        } catch (Exception e) {
            Logger.m4d(TAG, "getState 0003", e);
            str = "unknown";
        }
        Logger.m4d(TAG, "getState 0004 volumeState:", str);
        return str;
    }

    public static final void unmount(Context context) throws RemoteException {
        Logger.m4d(TAG, "sdUnmount 0001");
        try {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            Class<?> loadClass = systemClassLoader.loadClass("android.os.storage.StorageManager");
            for (Object next : (List) ReflectionUtils.invokeDeclaredMethod(loadClass, storageManager, "getStorageVolumes", (Class<?>[]) null, (Object[]) null)) {
                Class<?> loadClass2 = systemClassLoader.loadClass("android.os.storage.StorageVolume");
                if (((Boolean) ReflectionUtils.invokeDeclaredMethod(loadClass2, next, "isRemovable", (Class<?>[]) null, (Object[]) null)).booleanValue()) {
                    Logger.m4d(TAG, "sdUnmount 0002");
                    ReflectionUtils.invokeDeclaredMethod(loadClass, storageManager, "unmount", new Class[]{String.class}, new Object[]{(String) ReflectionUtils.invokeDeclaredMethod(loadClass2, next, "getId", (Class<?>[]) null, (Object[]) null)});
                }
            }
            Logger.m4d(TAG, "sdUnmount 0004");
        } catch (Exception e) {
            Logger.m4d(TAG, "sdUnmount 0003", e);
            throw new RemoteException();
        }
    }
}
