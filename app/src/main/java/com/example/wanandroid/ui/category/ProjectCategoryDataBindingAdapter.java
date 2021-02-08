package com.example.wanandroid.ui.category;

import android.content.Context;
import com.example.toollibrary.base.BaseBindingRvAdapter;
import com.example.toollibrary.base.BaseBindingViewHolder;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.databinding.ItemProjectCategoryBinding;

/**
 * Created by Miracle on 2020/8/1
 * Email: zhaoqirong96@gmail.com
 * Describe: 横向分类的列表
 */
public class ProjectCategoryDataBindingAdapter extends
    BaseBindingRvAdapter<ProjectCategoryBean, ItemProjectCategoryBinding> {

    public ProjectCategoryDataBindingAdapter(Context context, int resId) {
        super(context, resId);
    }

    @Override
    public void convert(BaseBindingViewHolder<ItemProjectCategoryBinding> holder, int position,
        ProjectCategoryBean projectCategoryBean) {

    }


}
