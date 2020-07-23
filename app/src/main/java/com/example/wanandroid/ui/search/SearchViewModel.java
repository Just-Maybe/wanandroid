package com.example.wanandroid.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.AppExecutors;
import com.example.wanandroid.utils.RxUtils;
import com.example.wanandroid.utils.SpUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchViewModel extends ViewModel {
    private static final String TAG = SearchViewModel.class.getSimpleName();
    public MutableLiveData<List<HotKeyBean>> hotKeyList;
    public MutableLiveData<List<ArticleBean>> searchResultList;
    private HotKeyRepository repository;

    public SearchViewModel() {
        repository = new HotKeyRepository();
        hotKeyList = new MutableLiveData<>();
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
                        repository.deleteAll();
                        repository.insertAll(hotKeyList.getValue());
                        SpUtils.putLong(SpUtils.update_hotKey_time, System.currentTimeMillis());
                    }
                });
            }
        });
    }

    private void getSearchResultList() {

    }
}
