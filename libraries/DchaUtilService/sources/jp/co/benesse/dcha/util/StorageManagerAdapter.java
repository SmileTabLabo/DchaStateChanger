package jp.co.benesse.dcha.util;

import android.content.Context;
import android.os.RemoteException;
import android.os.storage.StorageManager;
import com.sts.tottori.extension.BuildConfig;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class StorageManagerAdapter {
    public static final String TAG = StorageManagerAdapter.class.getSimpleName();

    private StorageManagerAdapter() {
    }

    public static final void unmount(Context context) throws RemoteException {
        Logger.d(TAG, "sdUnmount 0001");
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            Class storageManagerClazz = loader.loadClass("android.os.storage.StorageManager");
            Object storageVolumes = ReflectionUtils.invokeDeclaredMethod(storageManagerClazz, storageManager, "getStorageVolumes", null, null);
            List volumes = (List) storageVolumes;
            for (Object storageVolume : volumes) {
                Class storageVolumeClazz = loader.loadClass("android.os.storage.StorageVolume");
                boolean isRemovable = ((Boolean) ReflectionUtils.invokeDeclaredMethod(storageVolumeClazz, storageVolume, "isRemovable", null, null)).booleanValue();
                if (isRemovable) {
                    Logger.d(TAG, "sdUnmount 0002");
                    String volumeId = (String) ReflectionUtils.invokeDeclaredMethod(storageVolumeClazz, storageVolume, "getId", null, null);
                    ReflectionUtils.invokeDeclaredMethod(storageManagerClazz, storageManager, "unmount", new Class[]{String.class}, new Object[]{volumeId});
                }
            }
            Logger.d(TAG, "sdUnmount 0004");
        } catch (Exception e) {
            Logger.d(TAG, "sdUnmount 0003", e);
            throw new RemoteException();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0053, code lost:
        jp.co.benesse.dcha.util.Logger.d(jp.co.benesse.dcha.util.StorageManagerAdapter.TAG, "getPath 0002");
        r5 = (java.lang.String) jp.co.benesse.dcha.util.ReflectionUtils.invokeDeclaredMethod(r9, r8, "getPath", null, null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final String getPath(Context context) {
        Logger.d(TAG, "getPath 0001");
        String path = BuildConfig.FLAVOR;
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            Class storageManagerClazz = loader.loadClass("android.os.storage.StorageManager");
            Object storageVolumes = ReflectionUtils.invokeDeclaredMethod(storageManagerClazz, storageManager, "getStorageVolumes", null, null);
            List volumes = (List) storageVolumes;
            Iterator i$ = volumes.iterator();
            while (true) {
                if (!i$.hasNext()) {
                    break;
                }
                Object storageVolume = i$.next();
                Class storageVolumeClazz = loader.loadClass("android.os.storage.StorageVolume");
                boolean isRemovable = ((Boolean) ReflectionUtils.invokeDeclaredMethod(storageVolumeClazz, storageVolume, "isRemovable", null, null)).booleanValue();
                if (isRemovable) {
                    break;
                }
            }
        } catch (Exception e) {
            Logger.d(TAG, "getPath 0003", e);
        }
        Logger.d(TAG, "getPath 0004 path:", path);
        return path;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0053, code lost:
        jp.co.benesse.dcha.util.Logger.d(jp.co.benesse.dcha.util.StorageManagerAdapter.TAG, "getInternalPath 0002");
        r5 = (java.lang.String) jp.co.benesse.dcha.util.ReflectionUtils.invokeDeclaredMethod(r9, r8, "getInternalPath", null, null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final String getInternalPath(Context context) {
        Logger.d(TAG, "getInternalPath 0001");
        String path = BuildConfig.FLAVOR;
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            Class storageManagerClazz = loader.loadClass("android.os.storage.StorageManager");
            Object storageVolumes = ReflectionUtils.invokeDeclaredMethod(storageManagerClazz, storageManager, "getStorageVolumes", null, null);
            List volumes = (List) storageVolumes;
            Iterator i$ = volumes.iterator();
            while (true) {
                if (!i$.hasNext()) {
                    break;
                }
                Object storageVolume = i$.next();
                Class storageVolumeClazz = loader.loadClass("android.os.storage.StorageVolume");
                boolean isRemovable = ((Boolean) ReflectionUtils.invokeDeclaredMethod(storageVolumeClazz, storageVolume, "isRemovable", null, null)).booleanValue();
                if (isRemovable) {
                    break;
                }
            }
        } catch (Exception e) {
            Logger.d(TAG, "getInternalPath 0003", e);
        }
        Logger.d(TAG, "getInternalPath 0004 path:", path);
        return path;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0053, code lost:
        jp.co.benesse.dcha.util.Logger.d(jp.co.benesse.dcha.util.StorageManagerAdapter.TAG, "getState 0002");
        r10 = (java.lang.String) jp.co.benesse.dcha.util.ReflectionUtils.invokeDeclaredMethod(r8, r7, "getState", null, null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final String getState(Context context) {
        Logger.d(TAG, "getState 0001");
        String volumeState = "unknown";
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            Class storageManagerClazz = loader.loadClass("android.os.storage.StorageManager");
            Object storageVolumes = ReflectionUtils.invokeDeclaredMethod(storageManagerClazz, storageManager, "getStorageVolumes", null, null);
            List volumes = (List) storageVolumes;
            Iterator i$ = volumes.iterator();
            while (true) {
                if (!i$.hasNext()) {
                    break;
                }
                Object storageVolume = i$.next();
                Class storageVolumeClazz = loader.loadClass("android.os.storage.StorageVolume");
                boolean isRemovable = ((Boolean) ReflectionUtils.invokeDeclaredMethod(storageVolumeClazz, storageVolume, "isRemovable", null, null)).booleanValue();
                if (isRemovable) {
                    break;
                }
            }
        } catch (Exception e) {
            Logger.d(TAG, "getState 0003", e);
        }
        Logger.d(TAG, "getState 0004 volumeState:", volumeState);
        return volumeState;
    }
}
