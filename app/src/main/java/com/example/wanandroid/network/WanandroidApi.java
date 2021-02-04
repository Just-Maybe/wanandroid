package com.example.wanandroid.network;

import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.CoinBean;
import com.example.wanandroid.bean.CoinListBean;
import com.example.wanandroid.bean.MyCoinBean;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.bean.LoginBean;
import com.example.wanandroid.bean.ProjectListBean;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.bean.CategoryTreeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WanandroidApi {

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
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
    Observable<ResponseEntity<List<HotKeyBean>>> getHotKeyList();

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Observable<ResponseEntity<ArticleListBean>> getSearchArticleList(@Path("page") int page, @Field("k") String keyword);

    /**
     * 置顶文章
     */
    @GET("article/top/json")
    Observable<ResponseEntity<List<ArticleBean>>> getTopArticleList();

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<ResponseEntity<List<CategoryTreeBean>>> getTreeList();

    /**
     * 知识体系下的文章
     *
     * @param page
     * @param cid
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<ResponseEntity<ArticleListBean>> getArticleListByCategoryId(@Path("page") int page, @Query("cid") int cid);

    /**
     * 按照作者昵称搜索文章
     * article/list/0/json?author=鸿洋
     */
    @GET("article/list/{page}/json")
    Observable<ResponseEntity<ArticleListBean>> getArticleListByAuthor(@Path("page") int page, @Query("author") String author);

    /**
     * 导航数据
     *
     * @return
     */
    @GET("navi/json")
    Observable<ResponseEntity<NavBean>> getNavList();

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    Observable<ResponseEntity<List<ProjectCategoryBean>>> getProjectCategoryList();

    /**
     * 项目列表数据
     */
    @GET("project/list/{page}/json")
    Observable<ResponseEntity<ProjectListBean>> getProjectListById(@Path("page") int page, @Query("cid") String cid);

    /**
     * 获取热门项目列表
     *
     * @param page
     * @return
     */
    @GET("article/listproject/{page}/json")
    Observable<ResponseEntity<ProjectListBean>> getHotProjectList(@Path("page") int page);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<ResponseEntity<LoginBean>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Observable<ResponseEntity<LoginBean>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);


    /**
     * 登出
     *
     * @return
     */
    @GET("user/logout/json")
    Observable<ResponseEntity<Void>> logout();


    /**
     * 获取个人积分
     */
    @GET("lg/coin/userinfo/json")
    Observable<ResponseEntity<MyCoinBean>> getUserCoin();

    /**
     * 获取积分列表
     * @return
     */
    @GET("lg/coin/list/{page}/json")
    Observable<ResponseEntity<CoinListBean>> getCoinList(@Path("page")int page);

    /**
     * 收藏文章列表
     */
    @GET("collect/list/{page}/json")
    Observable<ResponseEntity<ArticleListBean>> getCollectedArticle(@Path("page") int page);

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    Observable<ResponseEntity<String>> collectArticleById(@Path("id") int id);

    /**
     * 收藏站外文章
     */
    @FormUrlEncoded
    @POST("lg/collect/add/json")
    Observable<ResponseEntity<ArticleListBean>> collectOutsideArticle(@Field("title") String title,
                                                                      @Field("author") String author,
                                                                      @Field("link") String link);

    /**
     * 文章列表-取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<ResponseEntity<String>> uncollectFromArticlePage(@Path("id") int id);

    /**
     * 我的收藏页面（该页面包含自己录入的内容）-取消收藏
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<ResponseEntity<ArticleListBean>> uncollectFromMyCollection(@Path("id") int id);


}
