package com.example.toollibrary.base;

import android.util.SparseArray;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseBindingViewHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final SparseArray<View> views;
    public DB dataBinding;


    public BaseBindingViewHolder(@NonNull View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
        this.dataBinding = DataBindingUtil.getBinding(itemView);
    }


    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public void setImage(int viewId, String url) {

    }

    public void setViewClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
    }
}
