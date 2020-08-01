package com.example.wanandroid.ui.project;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.databinding.ItemProjectCategoryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Miracle on 2020/8/1
 * Email: zhaoqirong96@gmail.com
 * Describe: 项目不同分类的列表
 */
public class ProjectCategoryAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ProjectCategoryBean> projectCategoryList;
    private int[] covers = new int[]{R.mipmap.ic_project_1, R.mipmap.ic_project_2, R.mipmap.ic_project_3, R.mipmap.ic_project_4, R.mipmap.ic_project_5
            , R.mipmap.ic_project_6, R.mipmap.ic_project_7, R.mipmap.ic_project_8, R.mipmap.ic_project_9, R.mipmap.ic_project_10};

    public ProjectCategoryAdapter(Context context, List<ProjectCategoryBean> projectCategoryList) {
        this.context = context;
        this.projectCategoryList = projectCategoryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProjectCategoryBinding databinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_project_category, parent, false);
        return new ProjectCategoryViewHolder(databinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProjectCategoryViewHolder viewHolder = (ProjectCategoryViewHolder) holder;
        viewHolder.initData(projectCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return projectCategoryList.size();
    }

    class ProjectCategoryViewHolder extends RecyclerView.ViewHolder {
        ItemProjectCategoryBinding databinding;

        public ProjectCategoryViewHolder(ItemProjectCategoryBinding databinding) {
            super(databinding.getRoot());
            this.databinding = databinding;
        }

        public void initData(ProjectCategoryBean bean) {
            databinding.tvCategoryName.setText(Html.fromHtml(bean.getName()));
            Random random = new Random();
            Glide.with(context).load(covers[random.nextInt(10)]).into(databinding.ivCover);
        }
    }
}
