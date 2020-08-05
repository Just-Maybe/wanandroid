package com.example.wanandroid.ui.information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.BaseRvAdapter;
import com.example.wanandroid.bean.CoinBean;

/**
 * Created by Miracle on 2020/8/5
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class CoinAdapter extends BaseRvAdapter<CoinBean> {

    public CoinAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_coin, parent, false);
        return new CoinViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CoinBean coinBean = dataList.get(position);
        CoinViewHolder viewHolder = (CoinViewHolder) holder;
        viewHolder.tvDesc.setText(coinBean.getDesc());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class CoinViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDesc;

        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
