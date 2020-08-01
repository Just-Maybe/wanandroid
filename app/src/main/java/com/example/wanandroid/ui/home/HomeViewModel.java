package com.example.wanandroid.ui.home;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.base.RxObserver;
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
    public MutableLiveData<List<ArticleBean>> articleList;
    public MutableLiveData<List<ArticleBean>> topArticleList;
    public MutableLiveData<List<BannerBean>> bannerList;
    public int page = 0;
    public MutableLiveData<Boolean> isLoadData;

    public HomeViewModel() {
        articleList = new MutableLiveData<>();
        topArticleList = new MutableLiveData<>();
        bannerList = new MutableLiveData<>();
        isLoadData = new MutableLiveData<>();
        isLoadData.setValue(false);
    }

    /**
     * 网络获取文章列表
     */
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
                                articleList.setValue(response.getData().getArticleList());
                            } else {
                                articleList.postValue(response.getData().getArticleList());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        page++;
                        isLoadData.setValue(false);
                    }
                });
    }

    /**
     * 网络获取Banner数据
     */
    public void getBannerFromNetwork() {
        Http.getApi().getBannerList()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<List<BannerBean>>() {
                    @Override
                    public void onSuccess(List<BannerBean> bannerBeans) {
                        if (bannerBeans != null && bannerBeans.size() > 0) {
                            bannerList.setValue(bannerBeans);
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }
                });
    }

    /**
     * 获取置顶文章
     */
    public void getTopArticleFromNetwork() {
        Http.getApi().getTopArticleList()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<List<ArticleBean>>() {
                    @Override
                    public void onSuccess(List<ArticleBean> articleBeans) {
                        if (articleBeans != null && articleBeans.size() > 0) {
                            topArticleList.setValue(articleBeans);
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }
                });
    }

}
