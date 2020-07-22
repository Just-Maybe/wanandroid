package com.example.wanandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wanandroid.bean.HotKeyBean;

import java.util.List;

@Dao
public interface HotKeyDao {
    @Query("SELECT * FROM hot_key_tb")
    List<HotKeyBean> getAllHotKey();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HotKeyBean... hotKeyBeans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<HotKeyBean> dataList);

    @Delete()
    void deleteAll(List<HotKeyBean> hotKeyList);
}
