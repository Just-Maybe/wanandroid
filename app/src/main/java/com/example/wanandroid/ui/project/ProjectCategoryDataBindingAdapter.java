package com.example.wanandroid.ui.project;

import android.content.Context;
import android.text.Html;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.toollibrary.base.BaseBindingRvAdapter;
import com.example.toollibrary.base.BaseBindingViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.databinding.ItemProjectCategoryBinding;

import java.util.Random;

/**
 * Created by Miracle on 2020/8/1
 * Email: zhaoqirong96@gmail.com
 * Describe: 横向分类的列表
 */
public class ProjectCategoryDataBindingAdapter extends
        BaseBindingRvAdapter<ProjectCategoryBean, ItemProjectCategoryBinding> {
    private int[] covers = new int[]{R.mipmap.ic_project_1, R.mipmap.ic_project_2, R.mipmap.ic_project_3, R.mipmap.ic_project_4, R.mipmap.ic_project_5
            , R.mipmap.ic_project_6, R.mipmap.ic_project_7, R.mipmap.ic_project_8, R.mipmap.ic_project_9, R.mipmap.ic_project_10};

    public ProjectCategoryDataBindingAdapter(Context context, int resId) {
        super(context, resId);
    }

    @Override
    public void convert(BaseBindingViewHolder<ItemProjectCategoryBinding> holder, int position,
                        ProjectCategoryBean bean) {
        holder.dataBinding.tvCategoryName.setText(Html.fromHtml(bean.getName()));
        Random random = new Random();
        Glide.with(mContext).load(covers[random.nextInt(10)]).into(holder.dataBinding.ivCover);
        holder.dataBinding.tvMore.setOnClickListener(v -> {
            if(listener!=null){
                listener.onItemClick(holder.dataBinding.tvMore,bean);
            }
        });
    }


}
