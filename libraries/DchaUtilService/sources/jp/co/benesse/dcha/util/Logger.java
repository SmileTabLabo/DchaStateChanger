package jp.co.benesse.dcha.util;

import android.content.Context;
import android.util.Log;
/* loaded from: classes.dex */
public final class Logger {
    public static final int ALL = 2;
    public static final int NONE = 7;
    private static final int OUTPUT_LEVEL = 7;
    private static final int TARGET_INDEX = 7;
    private static final StringBuffer MSG_BUFFER = new StringBuffer();
    private static final StringBuffer META_BUFFER = new StringBuffer();

    private Logger() {
    }

    public static int v(Context context, Object... messages) {
        return 0;
    }

    public static int d(Context context, Object... messages) {
        return 0;
    }

    public static int i(Context context, Object... messages) {
        return 0;
    }

    public static int w(Context context, Object... messages) {
        return 0;
    }

    public static int e(Context context, Object... messages) {
        return 0;
    }

    public static final int println(int priority, Context context, Object... messages) {
        return 0;
    }

    private static final int writeLog(int priority, Context context, Object... messages) {
        String packageName = context.getPackageName();
        return write(priority, packageName, messages);
    }

    public static int v(String packageName, Object... messages) {
        return 0;
    }

    public static int d(String packageName, Object... messages) {
        return 0;
    }

    public static int i(String packageName, Object... messages) {
        return 0;
    }

    public static int w(String packageName, Object... messages) {
        return 0;
    }

    public static int e(String packageName, Object... messages) {
        return 0;
    }

    public static final int println(int priority, String packageName, Object... messages) {
        return 0;
    }

    private static final int writeLog(int priority, String packageName, Object... messages) {
        return write(priority, packageName, messages);
    }

    private static final synchronized int write(int priority, String packageName, Object... messages) {
        int ret;
        synchronized (Logger.class) {
            if (7 > priority) {
                ret = 0;
            } else {
                String msg = getMessage(messages);
                ret = Log.println(priority, packageName, msg);
                Object last = messages[messages.length - 1];
                if (last instanceof Throwable) {
                    Throwable tr = (Throwable) last;
                    int ret2 = Log.println(6, packageName, Log.getStackTraceString(tr));
                    ret += ret2;
                }
            }
        }
        return ret;
    }

    private static final String getMessage(Object[] messages) {
        MSG_BUFFER.setLength(0);
        MSG_BUFFER.append(getMetaInfo());
        for (Object message : messages) {
            if (!(message instanceof Throwable)) {
                MSG_BUFFER.append(" ");
                MSG_BUFFER.append(message);
            }
        }
        String msg = MSG_BUFFER.toString();
        MSG_BUFFER.setLength(0);
        return msg;
    }

    private static final String getMetaInfo() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        int len = stackTraceElements.length;
        int index = len <= 7 ? len - 1 : 7;
        StackTraceElement element = stackTraceElements[index];
        return getMetaInfo(element);
    }

    private static final String getMetaInfo(StackTraceElement element) {
        String fullClassName = element.getClassName();
        String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();
        META_BUFFER.setLength(0);
        META_BUFFER.append("[");
        META_BUFFER.append(simpleClassName);
        META_BUFFER.append("#");
        META_BUFFER.append(methodName);
        META_BUFFER.append(":");
        META_BUFFER.append(lineNumber);
        META_BUFFER.append("]");
        return META_BUFFER.toString();
    }
}
