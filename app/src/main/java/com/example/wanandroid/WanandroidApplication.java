package com.example.wanandroid;

import android.app.Application;
import android.content.Context;

import com.example.wanandroid.database.AppDatabase;
import com.example.wanandroid.utils.AppExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

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
