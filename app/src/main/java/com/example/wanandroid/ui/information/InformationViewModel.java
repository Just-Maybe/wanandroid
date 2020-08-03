package com.example.wanandroid.ui.information;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.utils.SpUtils;

public class InformationViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLogin;

    public InformationViewModel() {
        isLogin = new MutableLiveData<>();
        isLogin.setValue(SpUtils.getBoolean(SpUtils.isLogin));
    }

    public void getLoginStatus(){
        isLogin.setValue(SpUtils.getBoolean(SpUtils.isLogin));
    }
}