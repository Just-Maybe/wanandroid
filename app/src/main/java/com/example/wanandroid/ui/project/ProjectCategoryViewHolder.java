package com.example.wanandroid.ui.project;

import android.text.Html;

import com.bumptech.glide.Glide;
import com.example.toollibrary.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.databinding.ItemProjectCategoryBinding;

import java.util.Random;

public class ProjectCategoryViewHolder extends BaseViewHolder<ItemProjectCategoryBinding> {
    private int[] covers = new int[]{R.mipmap.ic_project_1, R.mipmap.ic_project_2, R.mipmap.ic_project_3, R.mipmap.ic_project_4, R.mipmap.ic_project_5
            , R.mipmap.ic_project_6, R.mipmap.ic_project_7, R.mipmap.ic_project_8, R.mipmap.ic_project_9, R.mipmap.ic_project_10};

    ItemProjectCategoryBinding databinding;

    public ProjectCategoryViewHolder(ItemProjectCategoryBinding databinding) {
        super(databinding);
        this.databinding = databinding;
    }

    public void initData(ProjectCategoryBean bean) {
        databinding.tvCategoryName.setText(Html.fromHtml(bean.getName()));
        Random random = new Random();
        Glide.with(WanandroidApplication.applicationContext).load(covers[random.nextInt(10)]).into(databinding.ivCover);
        databinding.tvMore.setOnClickListener(v -> {

        });
    }
}