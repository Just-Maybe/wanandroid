package com.example.wanandroid.ui.home;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.WanandroidApplication;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.viewmodel.ArticleViewModel;
import com.google.common.util.concurrent.JdkFutureAdapters;

import java.util.List;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class HomeFragment extends Fragment {

    private ArticleViewModel articleViewModel;
    private RecyclerView rvArticle;//文章列表
    private ArticleAdapter adapter;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initRecyclerView();
        initListener();
        subscribeUI();
        articleViewModel.getArticleListFromNetwork();
        return root;
    }

    /**
     * Adapter 观察 ViewModel 中数据是否有变化
     */
    private void subscribeUI() {
        articleViewModel.getData().observe(getViewLifecycleOwner(), articleBeans -> {
            adapter.submitList(articleBeans);
            adapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerView() {
        rvArticle = root.findViewById(R.id.rv_article);
        adapter = new ArticleAdapter(getActivity());
        rvArticle.setAdapter(adapter);
        rvArticle.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initListener() {
        rvArticle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 >= adapter.getItemCount()) {
                    //加载更多
                    articleViewModel.getArticleListFromNetwork();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    lastVisibleItem = manager.findLastVisibleItemPosition();
                }
            }
        });
    }
}