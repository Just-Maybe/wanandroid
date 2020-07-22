package com.example.wanandroid.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.utils.KeyboardUtils;
import com.example.wanandroid.utils.KeyboardVisibilityEvent;
import com.example.wanandroid.utils.StatusBarUtil;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * Created by Miracle on 2020/7/21
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class SearchActivity extends AppCompatActivity {
    private EditText etSearch;
    private FlexboxLayout layoutHotKey;//热门搜索
    private SearchViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, findViewById(R.id.search_view));
        StatusBarUtil.setLightMode(this);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        initData();
        initView();
        initHotKey();
        initListener();
    }

    private void initData() {
        viewModel.getAllHotKey();
    }

    private void initView() {
        etSearch = findViewById(R.id.et_search);
        KeyboardUtils.show(etSearch, 500);

    }


    private void initHotKey() {
        layoutHotKey = findViewById(R.id.layout_hot_key);
        viewModel.hotKeyList.observe(this, new Observer<List<HotKeyBean>>() {
            @Override
            public void onChanged(List<HotKeyBean> hotKeyBeanList) {
                for (HotKeyBean hotKey : hotKeyBeanList) {
                    layoutHotKey.addView(createHokeyText(hotKey.getName()));
                }
            }
        });
    }

    private void initListener() {
        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            if (!isOpen) {
                etSearch.clearFocus();
            } else {
                etSearch.setFocusable(true);
                etSearch.requestFocus();
            }
        });
    }

    private TextView createHokeyText(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        return tv;
    }
}
