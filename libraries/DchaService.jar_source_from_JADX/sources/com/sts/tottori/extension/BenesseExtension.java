package com.sts.tottori.extension;

import android.graphics.Point;
import android.util.Log;

public class BenesseExtension {
    public static Point getLcdSize() throws ReflectiveOperationException {
        Object invoke = Class.forName("android.os.BenesseExtension").getMethod("getLcdSize", new Class[0]).invoke((Object) null, new Object[0]);
        if (invoke instanceof Point) {
            return (Point) invoke;
        }
        Log.e("Hoge", "return value is not Point");
        return null;
    }

    public static boolean setForcedDisplaySize(int i, int i2) throws ReflectiveOperationException {
        Object invoke = Class.forName("android.os.BenesseExtension").getMethod("setForcedDisplaySize", new Class[]{Integer.TYPE, Integer.TYPE}).invoke((Object) null, new Object[]{new Integer(i), new Integer(i2)});
        if (invoke instanceof Boolean) {
            return ((Boolean) invoke).booleanValue();
        }
        Log.e("Hoge", "return value is not Boolean");
        return false;
    }
}
