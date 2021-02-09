package com.example.wanandroid.ui.project;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.example.toollibrary.base.BaseBindingRvAdapter;
import com.example.toollibrary.base.BaseBindingViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ProjectBean;
import com.example.wanandroid.databinding.ItemProjectBinding;
import com.example.wanandroid.databinding.LayoutCloseBinding;

/**
 * 分类下的项目列表
 */
public class CategoryProjectListAdapter extends BaseBindingRvAdapter<ProjectBean, ItemProjectBinding> {
    private onCloseListner closeListner;

    public interface onCloseListner {
        void onClickClose();
    }

    public void setCloseListner(onCloseListner closeListner) {
        this.closeListner = closeListner;
    }

    public CategoryProjectListAdapter(Context context, int resId) {
        super(context, resId);
    }

    @Override
    public void convert(BaseBindingViewHolder<ItemProjectBinding> holder, int position, ProjectBean projectBean) {
        holder.dataBinding.setProjectBean(projectBean);
        holder.dataBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
    }

    @Override
    protected int getFootViewResId() {
        return R.layout.layout_close;
    }

    @Override
    protected boolean hasFootView() {
        return true;
    }

    @Override
    protected BaseBindingViewHolder createFootViewHolder(ViewDataBinding dataBinding) {
        return new FootViewHolder((LayoutCloseBinding) dataBinding);
    }

    class FootViewHolder extends BaseBindingViewHolder<LayoutCloseBinding> {

        public FootViewHolder(@NonNull LayoutCloseBinding dataBinding) {
            super(dataBinding);
            dataBinding.ivClose.setOnClickListener(v -> {
                if(closeListner!=null){
                    closeListner.onClickClose();
                }
            });
        }
    }
}
