package com.example.wanandroid.ui.network;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.ui.utils.LogUtil;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Http {

    public static final String BASE_URL = "https://www.wanandroid.com/";

    static {
        initOkHttpClient();
    }

    private static OkHttpClient.Builder builder;
    private static OkHttpClient client;
    private static Retrofit retrofit;

    private static void initOkHttpClient() {
        File httpCacheDirectory = new File(WanandroidApplication.getContext().getCacheDir(), WanandroidApplication.getContext()
                .getApplicationContext().getPackageName());
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        builder = new OkHttpClient.Builder();
        builder.cache(cache);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);//错误重连
        builder.cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(WanandroidApplication.getContext())));

        if (LogUtil.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor());
        }
        client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T createApi(Class<T> tClass) {
        return retrofit.create(tClass);
    }

}
