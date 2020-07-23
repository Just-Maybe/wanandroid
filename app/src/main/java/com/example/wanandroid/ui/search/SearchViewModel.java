package com.example.wanandroid.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleListBean;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.AppExecutors;
import com.example.wanandroid.utils.RxUtils;
import com.example.wanandroid.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchViewModel extends ViewModel {
    private static final String TAG = SearchViewModel.class.getSimpleName();
    private static final int START_PAGE = 0;

    public MutableLiveData<List<HotKeyBean>> hotKeyList;
    public MutableLiveData<List<HotKeyBean>> historyList;
    public MutableLiveData<List<ArticleBean>> searchResultList;
    private HotKeyRepository repository;
    public int searchPage = 0;

    public SearchViewModel() {
        repository = new HotKeyRepository();
        hotKeyList = new MutableLiveData<>();
        historyList = new MutableLiveData<>();
        searchResultList = new MutableLiveData<>();
    }


    public void getAllHotKey() {
        List<HotKeyBean> localHotKey = repository.getAllHotKey();
        long updateHotkeyTime = SpUtils.getLong(SpUtils.update_hotKey_time);

        if (System.currentTimeMillis() - updateHotkeyTime > 1000 * 3600 * 24) {  //超过一天时间重新请求
            getHotKeyFromNetwork();
            return;
        }

        if (localHotKey != null && localHotKey.size() > 0) {
            hotKeyList.setValue(localHotKey);
            return;
        }
        getHotKeyFromNetwork();
    }

    /**
     * 获取本地历史记录
     */
    public void getHistoryFormLocal() {
        List<HotKeyBean> localHotKey = repository.getAllHistory();
        historyList.setValue(localHotKey);
    }

    private void getHotKeyFromNetwork() {
        // 网络请求
        Http.getApi().getHotKeyList().compose(RxUtils.rxSchedulerHelper()).subscribe(new Observer<ResponseEntity<List<HotKeyBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseEntity<List<HotKeyBean>> response) {
                if (response.getData() != null && response.getData().size() > 0) {
                    hotKeyList.setValue(response.getData());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        repository.deleteAllHotKey();
                        repository.insertAll(hotKeyList.getValue());
                        SpUtils.putLong(SpUtils.update_hotKey_time, System.currentTimeMillis());
                    }
                });
            }
        });
    }

    /**
     * 搜索
     *
     * @param keyword
     */
    public void getSearchResultFromNetwork(String keyword) {
        Http.getApi().getSearchArticleList(searchPage, keyword)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<ResponseEntity<ArticleListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEntity<ArticleListBean> response) {
                        if (searchPage == START_PAGE) {
                            searchResultList.setValue(response.getData().getArticleList());
                        } else {
                            searchResultList.getValue().addAll(response.getData().getArticleList());
                            searchResultList.postValue(searchResultList.getValue());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        searchPage++;
//                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
                        HotKeyBean hotKeyBean = new HotKeyBean();
                        hotKeyBean.setName(keyword);
                        hotKeyBean.setHistory(true);
                        if (!repository.getAllHistory().contains(hotKeyBean)) {
                            repository.insert(hotKeyBean);
                        }
                        getHistoryFormLocal();
                    }
                });
    }


    public void clearHistory() {
        repository.deleteAllHistory();
        historyList.postValue(new ArrayList<>());
    }
}
