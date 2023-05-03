package p000jp.p001co.benesse.dcha.dchaservice.util;

/* renamed from: jp.co.benesse.dcha.dchaservice.util.Log */
public class Log {
    static final int LOGLEVEL = LogLevel.NONE.getLevel();

    /* renamed from: jp.co.benesse.dcha.dchaservice.util.Log$LogLevel */
    enum LogLevel {
        NONE(0),
        ERROR(1),
        INFO(2),
        DEBUG(3),
        VERBOSE(4);
        
        private static final LogLevel[] $VALUES = null;
        private int level;

        static {
            NONE = new LogLevel("NONE", 0, 0);
            ERROR = new LogLevel("ERROR", 1, 1);
            INFO = new LogLevel("INFO", 2, 2);
            DEBUG = new LogLevel("DEBUG", 3, 3);
            VERBOSE = new LogLevel("VERBOSE", 4, 4);
            $VALUES = new LogLevel[]{NONE, ERROR, INFO, DEBUG, VERBOSE};
        }

        private LogLevel(int i) {
            this.level = i;
        }

        public int getLevel() {
            return this.level;
        }
    }

    /* renamed from: d */
    public static void m0d(String str, String str2) {
        if (LOGLEVEL >= LogLevel.DEBUG.getLevel()) {
            android.util.Log.d(str, str2);
        }
    }

    /* renamed from: e */
    public static void m1e(String str, String str2, Throwable th) {
        if (LOGLEVEL >= LogLevel.ERROR.getLevel()) {
            android.util.Log.e(str, str2, th);
        }
    }

    /* renamed from: v */
    public static void m2v(String str, String str2) {
        if (LOGLEVEL >= LogLevel.VERBOSE.getLevel()) {
            android.util.Log.v(str, str2);
        }
    }
}
