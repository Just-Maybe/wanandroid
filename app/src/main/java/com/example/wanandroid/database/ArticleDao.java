package com.example.wanandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.wanandroid.bean.ArticleBean;

import java.util.List;

/**
 * Created by Miracle on 2020/7/18
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article_tb")
    LiveData<List<ArticleBean>> getAllArticle();
}
