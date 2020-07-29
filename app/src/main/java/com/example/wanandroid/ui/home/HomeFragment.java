package com.example.wanandroid.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wanandroid.GlobalViewModel;
import com.example.wanandroid.MainActivity;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.CategoryTreeBean;
import com.example.wanandroid.listener.RvLoadMoreListener;
import com.example.wanandroid.ui.category.CategoryDetailFragment;
import com.example.wanandroid.ui.category.CategoryFragment;
import com.example.wanandroid.ui.search.SearchActivity;

import java.util.List;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class HomeFragment extends Fragment {

    private HomeViewModel articleViewModel;
    private View layoutSearch;
    private RecyclerView rvArticle;//文章列表
    private SwipeRefreshLayout layoutRefresh;
    private ArticleAdapter adapter;
    private View root;
    private GlobalViewModel globalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        articleViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        globalViewModel = new ViewModelProvider(requireActivity()).get(GlobalViewModel.class);
        initRecyclerView();
        initSearchView();
        initListener();
        subscribeUI();
        articleViewModel.getArticleListFromNetwork();
        articleViewModel.getBannerFromNetwork();
        articleViewModel.getTopArticleFromNetwork();
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
        articleViewModel.articleList.observe(getViewLifecycleOwner(), articleBeans -> {
            if (articleViewModel.page == 0) {
                adapter.setData(articleBeans);
            } else {
                adapter.addData(articleBeans);
            }
        });

        articleViewModel.topArticleList.observe(getViewLifecycleOwner(), topArticleBeans -> {
            adapter.setTopArticle(topArticleBeans);
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
        layoutRefresh.setOnRefreshListener(() -> {
            articleViewModel.page = 0;
            articleViewModel.getArticleListFromNetwork();
            articleViewModel.getBannerFromNetwork();
            articleViewModel.getTopArticleFromNetwork();
        });

        rvArticle.addOnScrollListener(new RvLoadMoreListener() {

            @Override
            public void loadMoreData() {
                articleViewModel.getArticleListFromNetwork();
            }

            @Override
            public int getItemCount() {
                return adapter.getItemCount();
            }
        });

        adapter.setListener(new ArticleAdapter.onClickItemListener() {
            @Override
            public void onClickSubCategory(ArticleBean articleBean) {
                ((MainActivity) getActivity()).switchFragment(1);
                rvArticle.postDelayed(() -> ((MainActivity) getActivity()).getCategoryFragment().selectCategory((articleBean.getSuperChapterId() - 1), articleBean.getChapterId()), 500);
            }

            @Override
            public void onClickCategory(ArticleBean articleBean) {
                ((MainActivity) getActivity()).switchFragment(1);
                rvArticle.postDelayed(() -> ((MainActivity) getActivity()).getCategoryFragment().selectCategory((articleBean.getSuperChapterId() - 1)), 500);
            }
        });
    }


}