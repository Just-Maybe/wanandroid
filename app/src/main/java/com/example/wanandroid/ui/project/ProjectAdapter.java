package com.example.wanandroid.ui.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toollibrary.base.BaseBindingRvAdapter;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ProjectBean;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.bean.ProjectListBean;
import com.example.wanandroid.databinding.ItemProjectBinding;
import com.example.wanandroid.databinding.ItemProjectCategoryBinding;
import com.example.wanandroid.databinding.ItemRvProjectCategoryBinding;
import com.example.wanandroid.ui.article_detail.ArticleWebViewActivity;
import com.example.wanandroid.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miracle on 2020/7/31
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class ProjectAdapter extends RecyclerView.Adapter {
    private static final int TYPE_CATEGORY = 432; //项目分类
    private static final int TYPE_NORMAL = 539; // 普通样式
    private Context context;
    private List<ProjectCategoryBean> projectCategoryList;
    private List<ProjectBean> projectListList;
    protected OnItemClickListener listener;

    public interface OnItemClickListener {

        void onItemClick(View view, ProjectCategoryBean projectCategoryBean);

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public ProjectAdapter(Context context) {
        this.context = context;
        projectListList = new ArrayList<>();
        projectCategoryList = new ArrayList<>();
    }

    public void setCategoryList(List<ProjectCategoryBean> dataList) {
        this.projectCategoryList = dataList;
        notifyItemChanged(0);
    }

    public void setData(List<ProjectBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            projectListList = dataList;
            notifyItemRangeChanged(1, dataList.size());
        }
    }

    public void addData(List<ProjectBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            projectListList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CATEGORY:
                ItemRvProjectCategoryBinding categoryBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_rv_project_category, parent, false);
                return new ProjectCategoryViewHolder(categoryBinding);
        }
        ItemProjectBinding normalBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_project, parent, false);
        return new NormalProjectViewHolder(normalBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_CATEGORY:
                ProjectCategoryViewHolder categoryViewHolder = (ProjectCategoryViewHolder) holder;
                categoryViewHolder.initData();
                break;
            default:
                NormalProjectViewHolder normalProjectViewHolder = (NormalProjectViewHolder) holder;
                normalProjectViewHolder.initData(projectListList.get(position - 1));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_CATEGORY;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return 1 + projectListList.size();
    }

    class ProjectCategoryViewHolder extends RecyclerView.ViewHolder {
        ItemRvProjectCategoryBinding databinding;
        ProjectCategoryDataBindingAdapter adapter;

        public ProjectCategoryViewHolder(ItemRvProjectCategoryBinding databinding) {
            super(databinding.getRoot());
            this.databinding = databinding;
        }

        public void initData() {
            adapter = new ProjectCategoryDataBindingAdapter(context, R.layout.item_project_category);
            adapter.updateData(projectCategoryList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            databinding.rvProjectCategory.setLayoutManager(layoutManager);
            databinding.rvProjectCategory.setAdapter(adapter);
            adapter.setListener((View view, ProjectCategoryBean projectCategoryBean) -> {
                if (listener != null) {
                    listener.onItemClick(view, projectCategoryBean);
                }
            });
        }
    }

    class NormalProjectViewHolder extends RecyclerView.ViewHolder {
        private ItemProjectBinding databinding;

        public NormalProjectViewHolder(ItemProjectBinding databinding) {
            super(databinding.getRoot());
            this.databinding = databinding;
        }

        public void initData(ProjectBean projectBean) {
            databinding.setProjectBean(projectBean);
            databinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArticleWebViewActivity.launch(context, projectBean.getTitle(), projectBean.getLink());
                }
            });
        }
    }
}
