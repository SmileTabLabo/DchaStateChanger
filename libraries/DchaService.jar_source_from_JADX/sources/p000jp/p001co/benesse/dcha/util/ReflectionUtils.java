package p000jp.p001co.benesse.dcha.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: jp.co.benesse.dcha.util.ReflectionUtils */
public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static final Object getDeclaredField(Class<?> cls, Object obj, String str) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static final Object invokeDeclaredMethod(Class<?> cls, Object obj, String str, Class<?>[] clsArr, Object[] objArr) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(obj, objArr);
    }

    public static final void setDeclaredField(Class<?> cls, Object obj, String str, Object obj2) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }
}
