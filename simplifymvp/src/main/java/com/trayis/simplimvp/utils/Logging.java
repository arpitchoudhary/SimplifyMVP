package com.trayis.simplimvp.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Mukund Desai on 2/17/17.
 */
public class Logging {

    private Logging() {
        throw new AssertionError("no instances");
    }

    /**
     * Simple logging interface for log messages from ThirtyInch
     *
     * @see #setLogger(Logger)
     */
    public interface Logger {

        /**
         * @param level one of {@link Log#VERBOSE}, {@link Log#DEBUG},{@link Log#INFO},
         *              {@link Log#WARN},{@link Log#ERROR}
         * @param tag   log tag, caller
         * @param msg   message to log
         */
        void log(final int level, final String tag, final String msg);

        /**
         * @param level one of {@link Log#VERBOSE}, {@link Log#DEBUG},{@link Log#INFO},
         *              {@link Log#WARN},{@link Log#ERROR}
         * @param tag   log tag, caller
         * @param msg   message to log
         * @param e     error to throw
         */
        void log(final int level, final String tag, final String msg, final Throwable e);
    }

    private static Logger logger;

    /**
     * Sets logger using {@link Log} to print into Logcat with tag "ThirtyInch"
     *
     * @see #setLogger(Logger)
     */
    public static void initLogger() {
        setLogger(new Logger() {
            @Override
            public void log(final int level, final String tag, final String msg) {
                Log.println(level, tag, msg);
            }

            @Override
            public void log(int level, String tag, String msg, Throwable e) {
                Log.println(level, tag, msg);
                String stackTraceString = Log.getStackTraceString(e);
                if (!TextUtils.isEmpty(stackTraceString)) {
                    Log.println(level, tag, stackTraceString);
                }
            }
        });
    }

    /**
     * set a custom logger, {@code null} to disable logging
     * <p>
     * <p>
     * Use the default logcat logger for Android:
     * <code>
     * <pre>
     * LBALogging.initLogger();
     * </pre>
     * </code>
     * <p>
     */
    public static void setLogger(@Nullable final Logger logger) {
        Logging.logger = logger;
    }

    public static void d(final String tag, final String msg) {
        if (logger != null) {
            logger.log(Log.DEBUG, tag, msg);
        }
    }

    public static void d(final String tag, final String msg, final Throwable e) {
        if (logger != null) {
            logger.log(Log.DEBUG, tag, msg, e);
        }
    }

    public static void e(final String tag, final String msg) {
        if (logger != null) {
            logger.log(Log.ERROR, tag, msg);
        }
    }

    public static void e(final String tag, final String msg, final Throwable e) {
        if (logger != null) {
            logger.log(Log.ERROR, tag, msg, e);
        }
    }

    public static void i(final String tag, final String msg) {
        if (logger != null) {
            logger.log(Log.INFO, tag, msg);
        }
    }

    public static void i(final String tag, final String msg, final Throwable e) {
        if (logger != null) {
            logger.log(Log.INFO, tag, msg, e);
        }
    }

    public static void v(final String tag, final String msg) {
        if (logger != null) {
            logger.log(Log.VERBOSE, tag, msg);
        }
    }

    public static void v(final String tag, final String msg, final Throwable e) {
        if (logger != null) {
            logger.log(Log.VERBOSE, tag, msg, e);
        }
    }

    public static void w(final String tag, final String msg) {
        if (logger != null) {
            logger.log(Log.WARN, tag, msg);
        }
    }

    public static void w(final String tag, final String msg, final Throwable e) {
        if (logger != null) {
            logger.log(Log.WARN, tag, msg, e);
        }
    }
}
