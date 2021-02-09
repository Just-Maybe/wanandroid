package com.example.toollibrary.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * item相同的baseAdapter
 *
 * @param <T>
 */
public abstract class BaseBindingRvAdapter<T, DB extends ViewDataBinding> extends RecyclerView.Adapter<BaseBindingViewHolder<DB>> {
    private static final int NORMAL = 882;
    private static final int FOOT = 382;
    protected Context mContext;
    protected OnItemClickListener<T> listener;
    protected List<T> mDataList = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected int mResId;
    private ViewDataBinding footDataBinding;

    public BaseBindingRvAdapter(Context context, @IdRes int resId) {
        this.mContext = context;
        this.mResId = resId;
    }

    @NonNull
    @Override
    public BaseBindingViewHolder<DB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        ViewDataBinding dataBinding;
        switch (viewType) {
            case NORMAL:
                dataBinding = DataBindingUtil.inflate(mInflater, mResId, parent, false);
                return new BaseBindingViewHolder(dataBinding);
            case FOOT:
                footDataBinding = DataBindingUtil.inflate(mInflater, getFootViewResId(), parent, false);
                return createFootViewHolder(footDataBinding);
        }
        dataBinding = DataBindingUtil.inflate(mInflater, mResId, parent, false);
        return new BaseBindingViewHolder(dataBinding);
    }

    protected BaseBindingViewHolder createFootViewHolder(ViewDataBinding dataBinding) {
        return null;
    }

    protected int getFootViewResId() {
        return -1;
    }

    protected boolean hasFootView() {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOT;
        }
        return NORMAL;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindingViewHolder<DB> holder, int position) {
        switch (getItemViewType(position)) {
            case NORMAL:
                convert(holder, position, mDataList.get(position));
                break;
        }
    }


    public abstract void convert(BaseBindingViewHolder<DB> holder, int position, T t);


    @Override
    public int getItemCount() {
        if (hasFootView()) {
            return mDataList.size() + 1;
        }
        return mDataList.size();
    }


    public interface OnItemClickListener<T> {

        void onItemClick(View view, T t);
    }

    public void setListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public void updateData(List<T> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.mDataList = dataList;
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            mDataList.addAll(dataList);
            notifyItemInserted(mDataList.size() - dataList.size() - 1);
        }
    }

    public void addData(T t) {
        if (t != null) {
            mDataList.add(t);
            notifyItemInserted(mDataList.size() - 1);
        }
    }


}
