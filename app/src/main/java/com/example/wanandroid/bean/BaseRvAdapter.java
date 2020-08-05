package com.example.wanandroid.bean;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miracle on 2020/8/5
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter {

    public List<T> dataList = new ArrayList<>();
    protected Context context;

    public BaseRvAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }
}
