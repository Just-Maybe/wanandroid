package com.example.wanandroid.ui.information;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.base.RxObserver;
import com.example.wanandroid.bean.CoinBean;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;
import com.example.wanandroid.utils.SpUtils;

public class InformationViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLogin;
    public MutableLiveData<CoinBean> coinLiveData;

    public InformationViewModel() {
        isLogin = new MutableLiveData<>();
        coinLiveData = new MutableLiveData<>();
        isLogin.setValue(SpUtils.getBoolean(SpUtils.isLogin));
    }

    public void getLoginStatus() {
        isLogin.setValue(SpUtils.getBoolean(SpUtils.isLogin));
    }

    /**
     * 获取个人积分信息
     */
    public void getUserCoinFromNetwork() {
        Http.getApi().getUserCoin()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<CoinBean>() {
                    @Override
                    public void onSuccess(CoinBean coinBean) {
                        coinLiveData.postValue(coinBean);
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }
                });

    }
}