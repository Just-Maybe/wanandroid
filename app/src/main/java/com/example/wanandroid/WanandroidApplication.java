package com.example.wanandroid;

import android.app.Application;
import android.content.Context;

import com.example.wanandroid.database.AppDatabase;

public class WanandroidApplication extends Application {

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.getInstance(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.applicationContext = base;
    }

    public static Context getContext() {
        return applicationContext;
    }


}
