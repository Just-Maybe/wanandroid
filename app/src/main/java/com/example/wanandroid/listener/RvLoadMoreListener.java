package com.example.wanandroid.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public abstract class RvLoadMoreListener extends RecyclerView.OnScrollListener {
    private int lastVisibleItem;
    private int itemCount;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            lastVisibleItem = manager.findLastVisibleItemPosition();
            itemCount = getItemCount();
        }
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (newState == SCROLL_STATE_IDLE &&
                lastVisibleItem + 1 >= itemCount) {
            //加载更多
            loadMoreData();
        }
    }

    public abstract void loadMoreData();

    public abstract int getItemCount();
}
