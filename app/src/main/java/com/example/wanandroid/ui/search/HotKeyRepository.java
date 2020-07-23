package com.example.wanandroid.ui.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.database.AppDatabase;
import com.example.wanandroid.database.HotKeyDao;
import com.example.wanandroid.utils.AppExecutors;

import java.util.List;

public class HotKeyRepository {
    private static final String TAG = HotKeyRepository.class.getSimpleName();
    private HotKeyDao dao;
    private List<HotKeyBean> dataList;

    public HotKeyRepository() {
        dao = AppDatabase.getInstance(WanandroidApplication.getContext()).getHotKeyDao();
        dataList = dao.getAllHotKey();
    }

    public List<HotKeyBean> getAllHotKey() {
        return dataList;
    }

    public void insertAll(List<HotKeyBean> dataList) {
        dao.insert(dataList);
    }

    /**
     * 清空数据
     */
    public void deleteAll() {
        dao.deleteAll(dao.getAllHotKey());
    }

}
