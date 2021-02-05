package com.example.toollibrary.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * item相同的baseAdapter
 *
 * @param <T>
 */
public abstract class BaseSimpleRvAdapter<T> extends RecyclerView.Adapter {
    private Context mContext;
    private OnItemClickListener<T> listener;
    protected List<T> mDataList = new ArrayList<>();


    public BaseSimpleRvAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getItemLayoutResId(), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).setViewHolder(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setViewHolder(T t) {
            if (listener != null) {
                listener.onItemClick(itemView, t);
            }
            onBindData(this, t);
        }
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
            notifyItemInserted(mDataList.size() - dataList.size()-1);
        }
    }

    public void addData(T t) {
        if (t != null) {
            mDataList.add(t);
            notifyItemInserted(mDataList.size()-1);
        }
    }

    /**
     * 绑定数据
     *
     * @param baseViewHolder
     * @param t
     */
    public abstract void onBindData(BaseViewHolder baseViewHolder, T t);

    protected abstract int getItemLayoutResId();

}
