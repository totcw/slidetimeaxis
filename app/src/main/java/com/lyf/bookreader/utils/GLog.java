package com.lyf.bookreader.utils;

import android.text.TextUtils;
import android.util.Log;

import com.lyf.bookreader.BuildConfig;


public class GLog {
    private static final String LOG_TAG = "BookReader";
    private static boolean DEBUG = BuildConfig.DEBUG;

    private GLog() {
    }

    public static void error(String log) {
        if (DEBUG ) Log.e(LOG_TAG, "" + log);
    }

    public static void log(String log) {
        if (DEBUG ) Log.i(LOG_TAG, log);
    }

    public static void log(String tag, String log) {
        if (DEBUG)  Log.i(tag, log);
    }

    public static void d(String tag, String log) {
        if (DEBUG) Log.d(tag, log);
    }

    public static void e(String tag, String log) {
        if (DEBUG) Log.e(tag, log);
    }

    public static void i(String tag, String log) {
        if (DEBUG ) Log.i(tag, log);
    }
}
