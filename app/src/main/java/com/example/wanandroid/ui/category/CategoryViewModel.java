package com.example.wanandroid.ui.category;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.base.RxObserver;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;

import java.util.List;

public class CategoryViewModel extends ViewModel {

    public MutableLiveData<List<ArticleBean>> categoryArticleList;  // 分类下的文章列表
    public int page;
    private static final int START_PAGE = 0;

    public CategoryViewModel() {
        categoryArticleList = new MutableLiveData<>();
    }

    /**
     * 从网络中获取分类下的文章列表
     *
     * @param cid
     */
    public void getCategoryArticleListFromNetwork(int cid) {
        Http.getApi().getArticleListByCategoryId(page, cid)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<ArticleListBean>() {
                    @Override
                    public void onSuccess(ArticleListBean articleList) {
                        if (articleList.getArticleList() != null && articleList.getArticleList().size() > 0) {
                            if (page == START_PAGE) {
                                categoryArticleList.setValue(articleList.getArticleList());
                            } else {
                                categoryArticleList.postValue(articleList.getArticleList());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }

                    @Override
                    public void onComplete() {
                        page++;
                    }
                });
    }
}
