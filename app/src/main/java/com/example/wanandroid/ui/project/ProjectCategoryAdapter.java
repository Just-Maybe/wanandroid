package com.example.wanandroid.ui.project;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toollibrary.base.BaseSimpleRvAdapter;
import com.example.toollibrary.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.databinding.ItemProjectCategoryBinding;

import java.util.List;
import java.util.Random;

/**
 * Created by Miracle on 2020/8/1
 * Email: zhaoqirong96@gmail.com
 * Describe: 横向分类的列表
 */
public class ProjectCategoryAdapter extends BaseSimpleRvAdapter<ProjectCategoryBean, ItemProjectCategoryBinding,ProjectCategoryViewHolder> {

    public ProjectCategoryAdapter(Context context, int resId) {
        super(context, resId);
    }

    @Override
    protected ProjectCategoryViewHolder onCreateVH(ItemProjectCategoryBinding dataBinding) {
        return new ProjectCategoryViewHolder(dataBinding);
    }

    @Override
    public void convert(ProjectCategoryViewHolder holder, int position, ProjectCategoryBean projectCategoryBean) {
        holder.initData(projectCategoryBean);
    }


//    class ProjectCategoryViewHolder extends BaseViewHolder<ItemProjectCategoryBinding> {
//        ItemProjectCategoryBinding databinding;
//
//        public ProjectCategoryViewHolder(ItemProjectCategoryBinding databinding) {
//            super(databinding);
//            this.databinding = databinding;
//        }
//
//        public void initData(ProjectCategoryBean bean) {
//            databinding.tvCategoryName.setText(Html.fromHtml(bean.getName()));
//            Random random = new Random();
//            Glide.with(mContext).load(covers[random.nextInt(10)]).into(databinding.ivCover);
//            databinding.tvMore.setOnClickListener(v -> {
//
//
//            });
//        }
//    }
}
