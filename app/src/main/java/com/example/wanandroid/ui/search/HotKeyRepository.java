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

    public HotKeyRepository() {
        dao = AppDatabase.getInstance(WanandroidApplication.getContext()).getHotKeyDao();
    }

    /**
     * 查询热搜词汇
     *
     * @return
     */
    public List<HotKeyBean> getAllHotKey() {
        return dao.getAllHotKey();
    }

    /**
     * 查询历史记录
     *
     * @return
     */
    public List<HotKeyBean> getAllHistory() {
        return dao.getAllHistory();
    }

    public void insert(HotKeyBean hotKeyBean) {
        dao.insert(hotKeyBean);
    }

    public void insertAll(List<HotKeyBean> dataList) {
        dao.insert(dataList);
    }

    /**
     * 清空热搜数据
     */
    public void deleteAllHotKey() {
        dao.deleteAll(dao.getAllHotKey());
    }

    /**
     * 清空热搜数据
     */
    public void deleteAllHistory() {
        dao.deleteAll(dao.getAllHistory());
    }

}
