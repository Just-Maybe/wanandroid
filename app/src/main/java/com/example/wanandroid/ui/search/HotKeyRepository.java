package com.example.wanandroid.ui.search;

import androidx.lifecycle.LiveData;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.database.AppDatabase;
import com.example.wanandroid.database.HotKeyDao;

import java.util.List;

public class HotKeyRepository {
    private HotKeyDao dao;
    private LiveData<List<HotKeyBean>> dataList;

    public HotKeyRepository() {
        dao = AppDatabase.getInstance(WanandroidApplication.getContext()).getHotKeyDao();
        dataList = dao.getAllHotKey();
    }

    public LiveData<List<HotKeyBean>> getAllHotKey() {
        return dataList;
    }

    public void insertAll(List<HotKeyBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            dao.insert(dataList);
        }
    }

}
