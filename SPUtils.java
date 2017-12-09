package cn.we.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private static SharedPreferences sp;

    public static void putString(Context context, String key, String value) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static String getString(Context context, String key) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void putBoolean(Context context, String key, boolean value) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void putFloat(Context context, String key, float value) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }

    public static float getLong(Context context, String key, long defaultValue) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static void remove(Context context, String key) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    public static void clear(Context context) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }


}
