package com.example.wanandroid.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.databinding.ActivitySearchBindingImpl;
import com.example.wanandroid.ui.home.ArticleAdapter;
import com.example.wanandroid.utils.CommonUtils;
import com.example.wanandroid.utils.KeyboardUtils;
import com.example.wanandroid.utils.KeyboardVisibilityEvent;
import com.example.wanandroid.utils.StatusBarUtil;
import com.google.android.flexbox.FlexboxLayout;

import org.antlr.v4.misc.Utils;

import java.util.List;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * Created by Miracle on 2020/7/21
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etSearch;
    private FlexboxLayout layoutHotKey;//热门搜索
    private FlexboxLayout layout_history;//历史搜索
    private SearchViewModel viewModel;
    private ArticleAdapter adapter;
    private RecyclerView rvResult; //搜索结果列表
    private String keyword;
    private ActivitySearchBindingImpl dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, findViewById(R.id.search_view));
        StatusBarUtil.setLightMode(this);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        initData();
        initView();
        initHotKey();
        initHistory();
        initRecyclerView();
        initListener();
    }

    private void initRecyclerView() {
        rvResult = findViewById(R.id.rv_search_result);
        rvResult.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticleAdapter(this);
        rvResult.setAdapter(adapter);
        viewModel.searchResultList.observe(this, new Observer<List<ArticleBean>>() {
            @Override
            public void onChanged(List<ArticleBean> articleBeans) {
                adapter.submitList(articleBeans);
                adapter.notifyDataSetChanged();
                dataBinding.setSearchList(articleBeans);
            }
        });
    }

    private void initData() {
        viewModel.getAllHotKey();
        viewModel.getHistoryFormLocal();
    }

    private void initView() {
        dataBinding.ivBack.setOnClickListener(this);
        dataBinding.ivClear.setOnClickListener(this);
        etSearch = findViewById(R.id.et_search);
        KeyboardUtils.openKeyboard(this, etSearch);
    }


    private void initHotKey() {
        layoutHotKey = findViewById(R.id.layout_hot_key);
        viewModel.hotKeyList.observe(this, hotKeyBeanList -> {
            for (HotKeyBean hotKey : hotKeyBeanList) {
                layoutHotKey.addView(createHokeyText(hotKey.getName()));
            }
        });
    }

    private void initHistory() {
        layout_history = findViewById(R.id.layout_history);
        viewModel.historyList.observe(this, historyList -> {
            dataBinding.setHistoryList(historyList);
            layout_history.removeAllViews();
            for (HotKeyBean hotKey : historyList) {
                layout_history.addView(createHokeyText(hotKey.getName()));
            }
        });
    }

    private void initListener() {
        /**
         * 监听软键盘
         */
        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            if (!isOpen) {
                etSearch.clearFocus();
            } else {
                etSearch.requestFocus();
            }
        });
        /**
         * 搜索词汇
         */
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyword = etSearch.getText().toString();
                viewModel.searchPage = 0;
                viewModel.getSearchResultFromNetwork(keyword);
                KeyboardUtils.hideSoftKeyboard(SearchActivity.this, etSearch);
            }
            return false;
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    dataBinding.rvSearchResult.setVisibility(View.GONE);
                    dataBinding.layoutKeyWord.setVisibility(View.VISIBLE);
                }
            }
        });

        rvResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 >= adapter.getItemCount()) {
                    //加载更多
                    viewModel.getSearchResultFromNetwork(keyword);
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

    /**
     * 创建热搜词汇
     *
     * @param text
     * @return
     */
    private TextView createHokeyText(String text) {
        TextView tv = new TextView(this);
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = CommonUtils.dp2px(this, 18);
        params.bottomMargin = CommonUtils.dp2px(this, 15);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setOnClickListener(v -> {
            viewModel.getSearchResultFromNetwork(text);
            etSearch.setText(text);
            KeyboardUtils.hideSoftKeyboard(SearchActivity.this, etSearch);
        });
        tv.setTextColor(getResources().getColor(R.color.color_666666));
        tv.setLayoutParams(params);
        tv.setText(text);
        return tv;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                KeyboardUtils.hideSoftKeyboard(this, etSearch);
                break;
            case R.id.iv_clear:
                viewModel.clearHistory();
                break;
        }
    }

}
