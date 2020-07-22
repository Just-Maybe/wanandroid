package com.example.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;

import com.example.wanandroid.WanandroidApplication;

public class SpUtils {
    private static final String spFileName = "wanandroid";
    public static final String update_hotKey_time = "update_hotKey_time";

    public static String getString(String strKey) {
        return getSP().getString(strKey, "");
    }

    public static String getString(String strKey, String strDefault) {
        return getSP().getString(strKey, strDefault);
    }

    public static void putString(String strKey, String strData) {
        getEditor().putString(strKey, strData).commit();
    }

    public static Boolean getBoolean(String strKey) {
        return getSP().getBoolean(strKey, false);
    }

    public static Boolean getBoolean(String strKey,
                                     Boolean strDefault) {
        return getSP().getBoolean(strKey, strDefault);
    }


    public static void putBoolean(String strKey,
                                  Boolean strData) {
        getEditor().putBoolean(strKey, strData).commit();
    }

    public static int getInt(String strKey) {
        return getSP().getInt(strKey, -1);
    }

    public static int getInt(String strKey, int strDefault) {
        return getSP().getInt(strKey, strDefault);
    }

    public static void putInt(String strKey, int strData) {
        getEditor().putInt(strKey, strData).commit();
    }

    public static long getLong(String strKey) {
        return getSP().getLong(strKey, -1);
    }

    public static long getLong(String strKey, long strDefault) {
        return getSP().getLong(strKey, strDefault);
    }

    public static void putLong(String strKey, long strData) {
        getEditor().putLong(strKey, strData).commit();
    }

    public static SharedPreferences.Editor getEditor() {
        SharedPreferences sp = WanandroidApplication.applicationContext.getSharedPreferences(spFileName,
                Context.MODE_PRIVATE);
        return sp.edit();
    }

    public static SharedPreferences getSP() {
        SharedPreferences sp = WanandroidApplication.applicationContext.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        return sp;
    }
}
