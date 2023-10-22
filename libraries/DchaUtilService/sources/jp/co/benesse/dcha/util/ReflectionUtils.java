package jp.co.benesse.dcha.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/* loaded from: classes.dex */
public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static final Object invokeDeclaredMethod(Class<?> receiverClass, Object receiver, String name, Class<?>[] parameterTypes, Object[] args) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method method = receiverClass.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        Object actualObj = method.invoke(receiver, args);
        return actualObj;
    }

    public static final Object getDeclaredField(Class<?> receiverClass, Object receiver, String name) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = receiverClass.getDeclaredField(name);
        field.setAccessible(true);
        Object object = field.get(receiver);
        return object;
    }

    public static final void setDeclaredField(Class<?> receiverClass, Object receiver, String name, Object value) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = receiverClass.getDeclaredField(name);
        field.setAccessible(true);
        field.set(receiver, value);
    }
}
