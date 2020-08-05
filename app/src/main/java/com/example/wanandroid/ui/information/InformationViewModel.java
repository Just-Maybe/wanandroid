package com.example.wanandroid.ui.information;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.base.RxObserver;
import com.example.wanandroid.bean.CoinBean;
import com.example.wanandroid.bean.CoinListBean;
import com.example.wanandroid.bean.MyCoinBean;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;
import com.example.wanandroid.utils.SpUtils;

import java.util.List;

public class InformationViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLogin;
    public MutableLiveData<MyCoinBean> coinLiveData;
    public MutableLiveData<List<CoinBean>> coinListLiveData;
    public int coinPage = 0;

    public InformationViewModel() {
        isLogin = new MutableLiveData<>();
        isLogin.setValue(SpUtils.getBoolean(SpUtils.isLogin));
        coinLiveData = new MutableLiveData<>();
        coinListLiveData = new MutableLiveData<>();
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
                .subscribe(new RxObserver<MyCoinBean>() {
                    @Override
                    public void onSuccess(MyCoinBean coinBean) {
                        coinLiveData.postValue(coinBean);
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }
                });

    }

    /**
     * 获取积分列表
     */
    public void getCoinListFromNetwork() {
        Http.getApi().getCoinList(coinPage)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<CoinListBean>() {
                    @Override
                    public void onSuccess(CoinListBean coinListBean) {
                        coinListLiveData.postValue(coinListBean.getDatas());
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        coinPage++;
                    }
                });
    }
}