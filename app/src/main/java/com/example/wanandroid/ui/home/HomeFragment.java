package com.example.wanandroid.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.ui.search.SearchActivity;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class HomeFragment extends Fragment {

    private HomeViewModel articleViewModel;
    private View layoutSearch;
    private RecyclerView rvArticle;//文章列表
    private SwipeRefreshLayout layoutRefresh;
    private ArticleAdapter adapter;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        articleViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initRecyclerView();
        initSearchView();
        initListener();
        subscribeUI();
        articleViewModel.getArticleListFromNetwork();
        articleViewModel.getBannerFromNetwork();
        return root;
    }

    private void initSearchView() {
        layoutSearch = root.findViewById(R.id.search_view);
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                View sharedView = layoutSearch;
                String transitionName = "searchView";
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);
                startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
    }

    /**
     * Adapter 观察 ViewModel 中数据是否有变化
     */
    private void subscribeUI() {
        articleViewModel.ArticleList.observe(getViewLifecycleOwner(), articleBeans -> {
            adapter.submitList(articleBeans);
            adapter.notifyDataSetChanged();
        });
        articleViewModel.bannerList.observe(getViewLifecycleOwner(), bannerBeans -> {
            adapter.setBanner(bannerBeans);
        });

        articleViewModel.isLoadData.observe(getViewLifecycleOwner(), isLoadData -> {
            layoutRefresh.setRefreshing(isLoadData);
        });
    }

    private void initRecyclerView() {
        layoutRefresh = root.findViewById(R.id.refreshLayout);
        layoutRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rvArticle = root.findViewById(R.id.rv_article);
        adapter = new ArticleAdapter(getActivity());
        rvArticle.setAdapter(adapter);
        rvArticle.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initListener() {
        layoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                articleViewModel.page = 0;
                articleViewModel.getArticleListFromNetwork();
            }
        });

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