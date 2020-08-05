package com.example.wanandroid.ui.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.databinding.FragmentInformationBinding;
import com.example.wanandroid.ui.login.LoginActivity;
import com.example.wanandroid.utils.SpUtils;

public class InformationFragment extends Fragment implements View.OnClickListener {

    private InformationViewModel infoViewModel;
    private FragmentInformationBinding dataBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoViewModel = new ViewModelProvider(this).get(InformationViewModel.class);
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_information, container, false);
        initView();
        initData();
        subscribeUI();
        return dataBinding.getRoot();
    }

    private void initView() {
        dataBinding.layoutCoin.setOnClickListener(this);
    }

    private void initData() {
        infoViewModel.getUserCoinFromNetwork();
    }

    @Override
    public void onResume() {
        super.onResume();
        infoViewModel.getLoginStatus();
    }

    private void subscribeUI() {
        infoViewModel.isLogin.observe(getViewLifecycleOwner(), isLogin -> {
            dataBinding.setIsLogin(isLogin);
            dataBinding.setUsername(SpUtils.getString(SpUtils.username));
            Glide.with(getActivity()).load(isLogin ? R.drawable.ic_user_login : R.drawable.ic_user_logout).into(dataBinding.ivHead);
            if (!isLogin) {
                dataBinding.ivHead.setOnClickListener(v -> LoginActivity.launch(getActivity()));
            }
        });
        infoViewModel.coinLiveData.observe(getViewLifecycleOwner(), coinBean -> {
            dataBinding.setCoin(coinBean);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_coin:
                CoinActivity.launch(getActivity());
                break;
        }
    }
}