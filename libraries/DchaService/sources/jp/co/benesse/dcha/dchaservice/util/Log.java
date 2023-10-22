package jp.co.benesse.dcha.dchaservice.util;
/* loaded from: classes.dex */
public class Log {
    static final int LOGLEVEL = LogLevel.NONE.getLevel();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum LogLevel {
        NONE(0),
        ERROR(1),
        INFO(2),
        DEBUG(3),
        VERBOSE(4);
        
        private int level;

        LogLevel(int i) {
            this.level = i;
        }

        public int getLevel() {
            return this.level;
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (LOGLEVEL >= LogLevel.ERROR.getLevel()) {
            android.util.Log.e(str, str2, th);
        }
    }

    public static void d(String str, String str2) {
        if (LOGLEVEL >= LogLevel.DEBUG.getLevel()) {
            android.util.Log.d(str, str2);
        }
    }

    public static void v(String str, String str2) {
        if (LOGLEVEL >= LogLevel.VERBOSE.getLevel()) {
            android.util.Log.v(str, str2);
        }
    }
}
