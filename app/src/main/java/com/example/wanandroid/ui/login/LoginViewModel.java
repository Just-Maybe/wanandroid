package com.example.wanandroid.ui.login;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.bean.LoginBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;
import com.example.wanandroid.utils.SpUtils;
import com.example.wanandroid.utils.StringUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Miracle on 2020/7/25
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class LoginViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLogin;

    public LoginViewModel() {
        isLogin = new MutableLiveData<>();
    }

    public void login(String username, String password) {
        Http.getApi().login(username, password).compose(RxUtils.rxSchedulerHelper()).subscribe(new Observer<ResponseEntity<LoginBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseEntity<LoginBean> response) {
                if (!StringUtils.isEmpty(response.getErrorMsg())) {
                    Toast.makeText(WanandroidApplication.applicationContext, response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getData() != null) {
                    isLogin.setValue(true);
                    SpUtils.putString(SpUtils.username, response.getData().getUsername());
                    SpUtils.putInt(SpUtils.userId, response.getData().getUserId());
                    SpUtils.putBoolean(SpUtils.isLogin,true);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void register(String username, String password, String repassword) {
        Http.getApi().register(username, password, repassword)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<ResponseEntity<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEntity<LoginBean> response) {
                        if (!StringUtils.isEmpty(response.getErrorMsg())) {
                            Toast.makeText(WanandroidApplication.applicationContext, response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (response.getData() != null) {
                            isLogin.setValue(true);
                            SpUtils.putString(SpUtils.username, response.getData().getUsername());
                            SpUtils.putInt(SpUtils.userId, response.getData().getUserId());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
