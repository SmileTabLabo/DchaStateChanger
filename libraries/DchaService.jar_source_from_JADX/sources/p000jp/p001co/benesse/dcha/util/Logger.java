package p000jp.p001co.benesse.dcha.util;

import android.content.Context;
import android.util.Log;

/* renamed from: jp.co.benesse.dcha.util.Logger */
public final class Logger {
    public static final int ALL = 2;
    private static final StringBuffer META_BUFFER = new StringBuffer();
    private static final StringBuffer MSG_BUFFER = new StringBuffer();
    public static final int NONE = 7;
    private static final int OUTPUT_LEVEL = 7;
    private static final int TARGET_INDEX = 7;

    private Logger() {
    }

    /* renamed from: d */
    public static int m3d(Context context, Object... objArr) {
        return 0;
    }

    /* renamed from: d */
    public static int m4d(String str, Object... objArr) {
        return 0;
    }

    /* renamed from: e */
    public static int m5e(Context context, Object... objArr) {
        return 0;
    }

    /* renamed from: e */
    public static int m6e(String str, Object... objArr) {
        return 0;
    }

    private static final String getMessage(Object[] objArr) {
        MSG_BUFFER.setLength(0);
        MSG_BUFFER.append(getMetaInfo());
        for (Object obj : objArr) {
            if (!(obj instanceof Throwable)) {
                MSG_BUFFER.append(" ");
                MSG_BUFFER.append(obj);
            }
        }
        String stringBuffer = MSG_BUFFER.toString();
        MSG_BUFFER.setLength(0);
        return stringBuffer;
    }

    private static final String getMetaInfo() {
        int i = 7;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int length = stackTrace.length;
        if (length <= 7) {
            i = length - 1;
        }
        return getMetaInfo(stackTrace[i]);
    }

    private static final String getMetaInfo(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        String substring = className.substring(className.lastIndexOf(".") + 1);
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();
        META_BUFFER.setLength(0);
        META_BUFFER.append("[");
        META_BUFFER.append(substring);
        META_BUFFER.append("#");
        META_BUFFER.append(methodName);
        META_BUFFER.append(":");
        META_BUFFER.append(lineNumber);
        META_BUFFER.append("]");
        return META_BUFFER.toString();
    }

    /* renamed from: i */
    public static int m7i(Context context, Object... objArr) {
        return 0;
    }

    /* renamed from: i */
    public static int m8i(String str, Object... objArr) {
        return 0;
    }

    public static final int println(int i, Context context, Object... objArr) {
        return 0;
    }

    public static final int println(int i, String str, Object... objArr) {
        return 0;
    }

    /* renamed from: v */
    public static int m9v(Context context, Object... objArr) {
        return 0;
    }

    /* renamed from: v */
    public static int m10v(String str, Object... objArr) {
        return 0;
    }

    /* renamed from: w */
    public static int m11w(Context context, Object... objArr) {
        return 0;
    }

    /* renamed from: w */
    public static int m12w(String str, Object... objArr) {
        return 0;
    }

    private static final int write(int i, String str, Object... objArr) {
        int println;
        synchronized (Logger.class) {
            if (7 > i) {
                println = 0;
            } else {
                try {
                    int println2 = Log.println(i, str, getMessage(objArr));
                    Throwable th = objArr[objArr.length - 1];
                    println = th instanceof Throwable ? Log.println(6, str, Log.getStackTraceString(th)) + println2 : println2;
                } catch (Throwable th2) {
                    Class<Logger> cls = Logger.class;
                    throw th2;
                }
            }
        }
        return println;
    }

    private static final int writeLog(int i, Context context, Object... objArr) {
        return write(i, context.getPackageName(), objArr);
    }

    private static final int writeLog(int i, String str, Object... objArr) {
        return write(i, str, objArr);
    }
}
