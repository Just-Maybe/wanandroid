package com.example.wanandroid.ui.information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.bean.CoinBean;
import com.example.wanandroid.databinding.ActivityCoinBinding;
import com.example.wanandroid.listener.RvLoadMoreListener;
import com.example.wanandroid.utils.StatusBarUtil;

import java.util.List;

/**
 * Created by Miracle on 2020/8/5
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class CoinActivity extends BaseActivity implements View.OnClickListener {
    private InformationViewModel viewModel;
    private ActivityCoinBinding databinding;
    private CoinAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_coin);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, findViewById(R.id.layout_title));
        StatusBarUtil.setLightMode(this);
        viewModel = new ViewModelProvider(this).get(InformationViewModel.class);
        viewModel.getCoinListFromNetwork();
        initView();
        initListener();
        subscribeUI();
    }

    private void initView() {
        databinding.layoutTitle.ivBack.setOnClickListener(this);
        adapter = new CoinAdapter(this);
        databinding.rvCoin.setLayoutManager(new LinearLayoutManager(this));
        databinding.rvCoin.setAdapter(adapter);
        databinding.layoutTitle.tvTitle.setText("我的积分");
        databinding.layoutTitle.tvTitle.setTransitionName("myCoin");
    }

    private void initListener() {
        databinding.rvCoin.addOnScrollListener(new RvLoadMoreListener() {

            @Override
            public void loadMoreData() {
                viewModel.getCoinListFromNetwork();
            }

            @Override
            public int getItemCount() {
                return adapter.getItemCount();
            }
        });
    }

    private void subscribeUI() {
        viewModel.coinListLiveData.observe(this, coinBeans -> {
            if (viewModel.coinPage == 0) {
                adapter.setData(coinBeans);
            } else {
                adapter.addData(coinBeans);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, CoinActivity.class);
        context.startActivity(intent);
    }
}
