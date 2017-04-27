package com.lyf.bookreader.utils;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gl on 2017/4/27.
 */

public final  class PreferenceUtils {
    public static final String PREFERENCE_NAME = "com.lyf.bookreader.preference";
    public static final String KEY_LOCAL_HISTORY = "local_history_key";
    public static final void saveLocalSearchHistory2Preference(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(KEY_LOCAL_HISTORY,value);
        editor.commit();
    }

    public static final String getLocalSearchHistoryFormPreference(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOCAL_HISTORY,null);
    }
}
