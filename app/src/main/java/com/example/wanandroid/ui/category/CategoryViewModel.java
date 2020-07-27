package com.example.wanandroid.ui.category;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.bean.CategoryTreeBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;
import com.example.wanandroid.utils.StringUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CategoryViewModel extends ViewModel {
    public MutableLiveData<List<CategoryTreeBean>> categoryList;

    public CategoryViewModel() {
        categoryList = new MutableLiveData<>();
    }

    public void getCategoryTreeListFromNetwork() {
        Http.getApi().getTreeList().compose(RxUtils.rxSchedulerHelper()).subscribe(new Observer<ResponseEntity<List<CategoryTreeBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseEntity<List<CategoryTreeBean>> response) {
                if (!StringUtils.isEmpty(response.getErrorMsg())) {
                    Toast.makeText(WanandroidApplication.applicationContext, response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getData() != null && response.getData().size() > 0) {
                    categoryList.setValue(response.getData());
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