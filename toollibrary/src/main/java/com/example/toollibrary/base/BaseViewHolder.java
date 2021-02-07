package com.example.toollibrary.base;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final SparseArray<View> views;
    public DB dataBinding;

    public BaseViewHolder(@NonNull DB binding) {
        super(binding.getRoot());
        this.views = new SparseArray<>();
        this.dataBinding = binding;
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
