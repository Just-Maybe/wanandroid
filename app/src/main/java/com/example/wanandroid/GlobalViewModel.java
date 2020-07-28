package com.example.wanandroid;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.base.RxObserver;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.bean.CategoryTreeBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;
import com.example.wanandroid.utils.StringUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GlobalViewModel extends ViewModel {
    public MutableLiveData<List<CategoryTreeBean>> categoryList;

    public GlobalViewModel() {
        categoryList = new MutableLiveData<>();
    }

    /**
     * 获取分类树
     */
    public void getCategoryTreeListFromNetwork() {
        Http.getApi().getTreeList()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<List<CategoryTreeBean>>() {
                    @Override
                    public void onSuccess(List<CategoryTreeBean> categoryTreeBeans) {
                        if (categoryTreeBeans != null && categoryTreeBeans.size() > 0) {
                            categoryList.setValue(categoryTreeBeans);
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }
                });
    }


}