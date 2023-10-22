package jp.co.benesse.dcha.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Display;
import android.view.WindowManager;
import com.sts.tottori.extension.BenesseExtension;
import java.lang.reflect.InvocationTargetException;
/* loaded from: classes.dex */
public class WindowManagerAdapter {
    public static final int RESOLUTION_WXGA_HEIGHT = 800;
    public static final int RESOLUTION_WXGA_WIDTH = 1280;
    public static final int RESOLUTION_XGA_HEIGHT = 768;
    public static final int RESOLUTION_XGA_WIDTH = 1024;
    public static final String TAG = WindowManagerAdapter.class.getSimpleName();

    private WindowManagerAdapter() {
    }

    public static final boolean setForcedDisplaySize(Context context, int width, int height) {
        Logger.d(TAG, "setForcedDisplaySize 0001 width:", Integer.valueOf(width), " height:", Integer.valueOf(height));
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 28) {
            if (1024 == width && 768 == height) {
                Settings.System.putInt(context.getContentResolver(), "bc:compatscreen", 1);
            } else if (1280 == width && 800 == height) {
                Settings.System.putInt(context.getContentResolver(), "bc:compatscreen", 2);
            } else {
                Settings.System.putInt(context.getContentResolver(), "bc:compatscreen", 0);
            }
            result = true;
        } else {
            try {
                result = BenesseExtension.setForcedDisplaySize(width, height);
            } catch (Exception e) {
                Logger.d(TAG, "setForcedDisplaySize 0002", e);
            }
        }
        Logger.d(TAG, "setForcedDisplaySize 0003 result:", Boolean.valueOf(result));
        return result;
    }

    public static final int[] getDisplaySize(Context context) {
        Logger.d(TAG, "getDisplaySize 0001");
        int[] size = {0, 0};
        try {
            Point point = new Point();
            getRealSize(context, point);
            size[0] = point.x;
            size[1] = point.y;
        } catch (Exception e) {
            Logger.d(TAG, "getDisplaySize 0002", e);
        }
        Logger.d(TAG, "getDisplaySize 0003 width:", Integer.valueOf(size[0]), " height:", Integer.valueOf(size[1]));
        return size;
    }

    public static final int[] getLcdSize(Context context) {
        Logger.d(TAG, "getLcdSize 0001");
        int[] size = {0, 0};
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                Point point = new Point();
                getInitialDisplaySize(context, point);
                size[0] = point.x;
                size[1] = point.y;
            } catch (Exception e) {
                Logger.d(TAG, "getLcdSize 0002", e);
            }
        } else {
            try {
                Point point2 = BenesseExtension.getLcdSize();
                size[0] = point2.x;
                size[1] = point2.y;
            } catch (Exception e2) {
                Logger.d(TAG, "getLcdSize 0003", e2);
            }
        }
        Logger.d(TAG, "getLcdSize 0004 width:", Integer.valueOf(size[0]), " height:", Integer.valueOf(size[1]));
        return size;
    }

    private static final void getRealSize(Context context, Point outSize) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        Display display = wm.getDefaultDisplay();
        display.getRealSize(outSize);
    }

    private static final void getInitialDisplaySize(Context context, Point outSize) throws ClassNotFoundException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class serviceManagerClazz = loader.loadClass("android.os.ServiceManager");
        Object serviceManager = ReflectionUtils.invokeDeclaredMethod(serviceManagerClazz, null, "getService", new Class[]{String.class}, new Object[]{"window"});
        Class iwindowManagerStubClazz = loader.loadClass("android.view.IWindowManager$Stub");
        Object iwindowManager = ReflectionUtils.invokeDeclaredMethod(iwindowManagerStubClazz, null, "asInterface", new Class[]{IBinder.class}, new Object[]{serviceManager});
        WindowManager wm = (WindowManager) context.getSystemService("window");
        Display display = wm.getDefaultDisplay();
        Class iwindowManagerClazz = loader.loadClass("android.view.IWindowManager");
        ReflectionUtils.invokeDeclaredMethod(iwindowManagerClazz, iwindowManager, "getInitialDisplaySize", new Class[]{Integer.TYPE, Point.class}, new Object[]{Integer.valueOf(display.getDisplayId()), outSize});
    }
}
