package com.example.wanandroid.ui.network;

import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.bean.TreeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WanandroidApi {

    /**
     * 首页文章列表
     */
    @GET("article/list{page}/json")
    Observable<ResponseEntity<ArticleListBean>> getArticleList(@Path("page") int page);

    /**
     * 首页Banner
     */
    @GET("banner/json")
    Observable<ResponseEntity<List<BannerBean>>> getBannerList();

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    Observable<ResponseEntity<HotKeyBean>> getHotKeyList();

    /**
     * 置顶文章
     */
    @GET("top/json")
    Observable<ResponseEntity<ArticleBean>> getTopArticleList();

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<ResponseEntity<List<TreeBean>>> getTreeList();

    /**
     * 知识体系下的文章
     *
     * @param page
     * @param cid
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<ResponseEntity<ArticleListBean>> getArticleListByTreeId(@Path("page") int page, @Query("cid") String cid);

    /**
     * 按照作者昵称搜索文章
     * article/list/0/json?author=鸿洋
     */
    @GET("article/list/{page}/json")
    Observable<ResponseEntity<ArticleListBean>> getArticleListByAuthor(@Path("page") int page, @Query("author") String author);


}
