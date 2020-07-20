package com.example.wanandroid.database;

import androidx.room.TypeConverter;

import com.example.wanandroid.bean.ArticleBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Miracle on 2020/7/20
 * Email: zhaoqirong96@gmail.com
 * Describe: 文章便签
 */
public class ArticleTagConverter {
    @TypeConverter
    public static List<ArticleBean.TagBean> revert(String dataList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ArticleBean.TagBean>>() {
        }.getType();
        return gson.fromJson(dataList.toString(), type);
    }

    @TypeConverter
    public static String converter(List<ArticleBean.TagBean> dataList) {
        return new Gson().toJson(dataList);
    }
}
