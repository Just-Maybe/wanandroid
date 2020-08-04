package com.example.wanandroid.ui.category;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.base.RxObserver;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;

import java.util.List;

public class CategoryViewModel extends ViewModel {

    public MutableLiveData<List<ArticleBean>> categoryArticleList;  // 分类下的文章列表
    public MutableLiveData<Boolean> isLoadData;
    public int page;
    private static final int START_PAGE = 0;

    public CategoryViewModel() {
        categoryArticleList = new MutableLiveData<>();
        isLoadData = new MutableLiveData<>();
        isLoadData.setValue(false);
    }

    /**
     * 从网络中获取分类下的文章列表
     *
     * @param cid
     */
    public void getCategoryArticleListFromNetwork(int cid) {
        isLoadData.setValue(page == 0);
        Http.getApi().getArticleListByCategoryId(page, cid)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<ArticleListBean>() {
                    @Override
                    public void onSuccess(ArticleListBean articleList) {
                        if (articleList.getArticleList() != null && articleList.getArticleList().size() > 0) {
                            if (page == START_PAGE) {
                                categoryArticleList.setValue(articleList.getArticleList());
                            } else {
                                categoryArticleList.setValue(articleList.getArticleList());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }

                    @Override
                    public void onComplete() {
                        isLoadData.setValue(false);
                        page++;
                    }
                });
    }
    /**
     * 收藏文章
     *
     * @param id
     */
    public void collectArticle(int id) {
        Http.getApi().collectArticleById(id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(WanandroidApplication.applicationContext, "收藏成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }
                });
    }

    /**
     * 取消收藏文章
     *
     * @param id
     */
    public void uncollectArticle(int id) {
        Http.getApi().uncollectFromArticlePage(id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(WanandroidApplication.applicationContext, "已取消收藏", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }
                });
    }
}
