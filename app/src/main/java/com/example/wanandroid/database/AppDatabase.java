package com.example.wanandroid.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.wanandroid.bean.ArticleBean;

/**
 * Created by Miracle on 2020/7/18
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */

@Database(entities = {ArticleBean.class}, version = 1, exportSchema = false)
@TypeConverters({ArticleTagConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "wanandroid-db";
    private static volatile AppDatabase mAppDatabase;

    public abstract ArticleDao getArticleDao();

    public static AppDatabase getInstance(Context context) {
        if (mAppDatabase == null) {
            synchronized (AppDatabase.class) {
                if (mAppDatabase == null) {
                    mAppDatabase = buildDatabase(context);
                }
            }
        }
        return mAppDatabase;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }
}
