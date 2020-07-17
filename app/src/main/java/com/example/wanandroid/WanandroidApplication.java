package com.example.wanandroid;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;

public class WanandroidApplication extends Application {

    public static Context applicationContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.applicationContext = base;
    }

    public static Context getContext() {
        return applicationContext;
    }
}
