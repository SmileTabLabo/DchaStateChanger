package com.sts.tottori.extension;

import android.graphics.Point;
import android.util.Log;
import java.lang.reflect.Method;
/* loaded from: classes.dex */
public class BenesseExtension {
    public static boolean setForcedDisplaySize(int width, int height) throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("android.os.BenesseExtension");
        Method method = clazz.getMethod("setForcedDisplaySize", Integer.TYPE, Integer.TYPE);
        Object ret = method.invoke(null, new Integer(width), new Integer(height));
        if (ret instanceof Boolean) {
            return ((Boolean) ret).booleanValue();
        }
        Log.e("Hoge", "return value is not Boolean");
        return false;
    }

    public static Point getLcdSize() throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("android.os.BenesseExtension");
        Method method = clazz.getMethod("getLcdSize", new Class[0]);
        Object ret = method.invoke(null, new Object[0]);
        if (ret instanceof Point) {
            return (Point) ret;
        }
        Log.e("Hoge", "return value is not Point");
        return null;
    }
}
