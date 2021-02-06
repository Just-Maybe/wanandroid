package com.example.toollibrary.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * item相同的baseAdapter
 *
 * @param <T>
 */
public abstract class BaseSimpleRvAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context mContext;
    private OnItemClickListener<T> listener;
    protected List<T> mDataList = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected int mResId;

    public BaseSimpleRvAdapter(Context context,@IdRes int resId) {
        this.mContext = context;
        this.mResId = resId;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(mResId,parent,false);
        return (VH) new BaseViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        convert(holder, position, mDataList.get(position));
    }


    public abstract void convert(VH holder, int position, T t);


    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public interface OnItemClickListener<T> {

        void onItemClick(View view, T t);
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
