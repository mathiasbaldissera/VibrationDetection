package org.gama.applications.vibrationdetectorapp;

import android.content.Context;
import android.util.Log;

public class PreferencesUtils {
    private Context context;
    private final String appname;

    public PreferencesUtils(Context c) {
        context = c;
        this.appname = context.getString(R.string.app_name);
    }

    public void saveAnyInt(String key, int value) {
        context.getSharedPreferences(appname, Context.MODE_PRIVATE).edit()
                .putInt(key, value).apply();
    }

    public int getAnyInt(String key, int defaultValue) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    public void saveAnyBoolean(String key, boolean value) {
        context.getSharedPreferences(appname, Context.MODE_PRIVATE).edit()
                .putBoolean(key, value).apply();
    }

    public boolean getAnyBoolean(String key, boolean defaultValue) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    public void saveAnyString(String key, String value) {
        context.getSharedPreferences(appname, Context.MODE_PRIVATE).edit()
                .putString(key, value).apply();
    }

    public String getAnyString(String key, String defaultValue) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public void saveAnyFloat(String key, float value) {
        context.getSharedPreferences(appname, Context.MODE_PRIVATE).edit()
                .putFloat(key, value).apply();
    }

    public float getAnyFloat(String key, float defaultValue) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getFloat(key, defaultValue);
    }

    public void saveAnyLong(String key, long value) {
        context.getSharedPreferences(appname, Context.MODE_PRIVATE).edit()
                .putLong(key, value).apply();
    }

    public long getAnyLong(String key, long defaultValue) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getLong(key, defaultValue);
    }

    public String getSavedGlove() {
        return "LUVAMOUSE";
    }

    public boolean isSavingAccEnabled() {
        return getAnyBoolean("isSavingAccEnabled", false);
    }
    public void setIsSavingAccEnabled(boolean b) {
        saveAnyBoolean("isSavingAccEnabled", b);

    }
    public boolean isSavingGyroEnabled() {
        return getAnyBoolean("isSavingGyroEnabled", false);
    }
    public void setIsSavingGyroEnabled(boolean b) {
        saveAnyBoolean("isSavingGyroEnabled", b);


    }
    public int getGraphScaleIndex() {
        return getAnyInt("graphScaleIndex", 0);
    }

    public float getGraphScaleValue() {
        return getAnyFloat("graphScaleValue", 0f);
    }

    public void setGraphScaleIndex(int value) {
        saveAnyInt("graphScaleIndex", value);
        float aFloat = Float.parseFloat((context.getResources().getStringArray(R.array.scale_in_g))[value].split(", ")[0]);
        saveAnyFloat("graphScaleValue", aFloat);
    }

    public boolean isGraphViewScaleStaringFromZero() {
        return getAnyBoolean("graphViewScaleStarFromZero", false);
    }

    public void setGraphViewScaleStaringFromZero(boolean b) {
        saveAnyBoolean("graphViewScaleStarFromZero", b);
    }
    public int getTimeIntervalIndex() {
       return getAnyInt("timeIntervalIndex", 0);
    }
    public int getTimeIntervalValue() {
        int anInt = Integer.parseInt((context.getResources().getStringArray(R.array.save_interval))[0]);
        return getAnyInt("timeIntervalValue", anInt);
    }

    public void setTimeIntervalIndex(int value) {
        saveAnyInt("timeIntervalIndex", value);
        int anInt = Integer.parseInt((context.getResources().getStringArray(R.array.save_interval))[0]);
        saveAnyInt("timeIntervalValue", anInt);
    }



}
