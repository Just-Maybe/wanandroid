package com.example.wanandroid.base;

import android.widget.Toast;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.utils.StringUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class RxObserver<T> implements Observer<ResponseEntity<T>> {
    public static final int Throwable = 400;

    @Override
    public void onNext(ResponseEntity<T> response) {
        if (!StringUtils.isEmpty(response.getErrorMsg())) {
            Toast.makeText(WanandroidApplication.applicationContext, response.getErrorMsg(), Toast.LENGTH_SHORT).show();
            onFailure(response.getErrorMsg(), response.getErrorCode());
            return;
        }
        onSuccess(response.getData());
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e.getMessage(), Throwable);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(String errorMsg, int errorCode);
}
