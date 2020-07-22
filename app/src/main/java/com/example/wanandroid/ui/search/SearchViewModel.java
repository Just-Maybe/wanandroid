package com.example.wanandroid.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.bean.ResponseEntity;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.AppExecutors;
import com.example.wanandroid.utils.RxUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchViewModel extends ViewModel {
    private static final String TAG = SearchViewModel.class.getSimpleName();
    public MutableLiveData<List<HotKeyBean>> hotKeyList;
    private HotKeyRepository repository;

    public SearchViewModel() {
        repository = new HotKeyRepository();
        hotKeyList = new MutableLiveData<>();
    }


    public void getAllHotKey() {
        LiveData<List<HotKeyBean>> localHotKey = repository.getAllHotKey();

        if (localHotKey.getValue() != null && localHotKey.getValue().size() > 0) {
            hotKeyList.setValue(localHotKey.getValue());
            return;
        }

        if (localHotKey.getValue() == null || localHotKey.getValue().size() == 0) {
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
                            repository.insertAll(hotKeyList.getValue());
                        }
                    });
                }
            });
        }
    }
}
