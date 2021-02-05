package com.example.wanandroid.ui.project;

import android.content.Context;

import com.example.toollibrary.base.BaseSimpleRvAdapter;
import com.example.wanandroid.bean.ProjectBean;

public class CategoryProjectListAdapter extends BaseSimpleRvAdapter<ProjectBean> {

    public CategoryProjectListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindData(BaseSimpleRvAdapter<ProjectBean>.BaseViewHolder baseViewHolder, ProjectBean projectBean) {

    }

    @Override
    protected int getItemLayoutResId() {
        return 0;
    }
}
