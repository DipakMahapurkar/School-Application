package com.schoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String APP_PREF = "STUDENT_APP";

    public static long getLong(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(
                APP_PREF, Context.MODE_PRIVATE);
        return preference.getLong(key, 0l);
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(
                APP_PREF, Context.MODE_PRIVATE);
        return preference.getBoolean(key, false);
    }

    public static void setBoolean(Context context, String key,
                                  boolean value) {
        SharedPreferences preference = context
                .getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences preference = context.getSharedPreferences(
                APP_PREF, Context.MODE_PRIVATE);
        return preference.getInt(key, defaultValue);
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static void setInt(Context context, String key,
                              int value) {
        SharedPreferences preference = context
                .getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(
                APP_PREF, Context.MODE_PRIVATE);
        return preference.getString(key, "");
    }

    public static void setString(Context context, String key,
                                 String value) {
        SharedPreferences preference = context
                .getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void clearAll(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }
}
