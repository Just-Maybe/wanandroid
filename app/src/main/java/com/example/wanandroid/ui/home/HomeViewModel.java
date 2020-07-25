package com.example.wanandroid.ui.home;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;
import com.example.wanandroid.utils.StringUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Miracle on 2020/7/18
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */

public class HomeViewModel extends ViewModel {
    private static final String TAG = HomeViewModel.class.getSimpleName();
    private static final int START_PAGE = 0;
    public MutableLiveData<List<ArticleBean>> ArticleList;
    public MutableLiveData<List<BannerBean>> bannerList;
    public int page = 0;
    public MutableLiveData<Boolean> isLoadData;

    public HomeViewModel() {
        ArticleList = new MutableLiveData<>();
        bannerList = new MutableLiveData<>();
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
                                ArticleList.setValue(response.getData().getArticleList());
                            } else {
                                ArticleList.getValue().addAll(response.getData().getArticleList());
                                ArticleList.postValue(ArticleList.getValue());
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

    public void getBannerFromNetwork() {
        Http.getApi().getBannerList()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<ResponseEntity<List<BannerBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEntity<List<BannerBean>> response) {
                        if (!StringUtils.isEmpty(response.getErrorMsg())) {
                            Toast.makeText(WanandroidApplication.applicationContext, response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (response.getData() != null && response.getData().size() > 0) {
                            bannerList.setValue(response.getData());
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
