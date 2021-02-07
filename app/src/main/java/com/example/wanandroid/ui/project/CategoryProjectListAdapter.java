package com.example.wanandroid.ui.project;

import android.content.Context;

import com.example.toollibrary.base.BaseSimpleRvAdapter;
import com.example.toollibrary.base.BaseViewHolder;
import com.example.wanandroid.bean.ProjectBean;
import com.example.wanandroid.databinding.ItemProjectBinding;

public class CategoryProjectListAdapter extends BaseSimpleRvAdapter<ProjectBean, BaseViewHolder<ItemProjectBinding>> {


    public CategoryProjectListAdapter(Context context, int resId) {
        super(context, resId);
    }

    @Override
    public void convert(BaseViewHolder<ItemProjectBinding> holder, int position, ProjectBean projectBean) {
        holder.dataBinding.setProjectBean(projectBean);
    }

}
