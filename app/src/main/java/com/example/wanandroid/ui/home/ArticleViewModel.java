package com.example.wanandroid.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
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
    public int page = 0;
    public MutableLiveData<Boolean> isLoadData;

    public ArticleViewModel() {
        dataList = new MutableLiveData<>();
        isLoadData = new MutableLiveData<>();
        isLoadData.setValue(false);
    }

    public void getArticleListFromNetwork() {
        isLoadData.setValue(page == 0);
        Http.getApi().getArticleList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<ResponseEntity<ArticleListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEntity<ArticleListBean> response) {
                        isLoadData.setValue(false);
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
                        isLoadData.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        page++;
                        isLoadData.setValue(false);
                    }
                });
    }

    public MutableLiveData<List<ArticleBean>> getData() {
        return dataList;
    }
}
