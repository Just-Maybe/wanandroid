package com.example.wanandroid.ui.information;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.databinding.FragmentInformationBinding;
import com.example.wanandroid.ui.arithmetic.ArithmeticDetailActivity;
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
        dataBinding.layoutOffer.setOnClickListener(this);
        dataBinding.tvLogout.setOnClickListener(this);
    }

    private void initData() {

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
            dataBinding.ivHead.setClickable(!isLogin);
            Glide.with(getActivity()).load(isLogin ? R.drawable.ic_user_login : R.drawable.ic_user_logout).into(dataBinding.ivHead);
            if (!isLogin) {
                dataBinding.ivHead.setOnClickListener(v -> LoginActivity.launch(getActivity()));
                dataBinding.layoutCoin.setOnClickListener(v -> LoginActivity.launch(getActivity()));
            } else {
                infoViewModel.getUserCoinFromNetwork();
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
                if (!infoViewModel.isLogin.getValue()) {
                    Toast.makeText(WanandroidApplication.getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), CoinActivity.class);
                View sharedView = dataBinding.tvCoin;
                String transitionName = "myCoin";
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);
                startActivity(intent, transitionActivityOptions.toBundle());
                break;
            case R.id.layout_offer:
                Intent arithmetic = new Intent(getActivity(), ArithmeticDetailActivity.class);
                startActivity(arithmetic);
                break;
            case R.id.tv_logout:
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setMessage("确认退出登录吗?").setTitle("退出登录")
                        .setNegativeButton("取消", (dialog12, which) -> dialog12.dismiss()).setPositiveButton("确认", (dialog1, which) -> {
                            infoViewModel.logout();
                            dialog1.dismiss();
                        }).create();
                dialog.show();
                break;
        }
    }
}