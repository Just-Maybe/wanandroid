package com.example.wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.LogUtils;
import com.example.wanandroid.utils.RxUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Miracle on 2020/7/18
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */

public class ArticleViewModel extends ViewModel {
    private static final String TAG = ArticleViewModel.class.getSimpleName();
    private static final int START_PAGE = 0;
    private MutableLiveData<List<ArticleBean>> dataList;
    private int page = 0;

    public ArticleViewModel() {
        dataList = new MutableLiveData<>();
    }

    public void getArticleListFromNetwork() {
        Http.getApi().getArticleList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<ResponseEntity<ArticleListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEntity<ArticleListBean> response) {
                        if (response.getData() != null && response.getData().getSize() > 0) {
                            if (page == START_PAGE) {
                                dataList.setValue(response.getData().getArticleList());
                            } else {
                                dataList.getValue().addAll(response.getData().getArticleList());
                                dataList.postValue(dataList.getValue());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        page++;
                    }
                });
    }

    public MutableLiveData<List<ArticleBean>> getData() {
        return dataList;
    }
}
