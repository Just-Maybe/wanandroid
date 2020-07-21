package com.example.wanandroid.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroid.R;
import com.example.wanandroid.utils.KeyboardUtils;
import com.example.wanandroid.utils.KeyboardVisibilityEvent;
import com.example.wanandroid.utils.StatusBarUtil;

/**
 * Created by Miracle on 2020/7/21
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class SearchActivity extends AppCompatActivity {
    private EditText etSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, findViewById(R.id.search_view));
        StatusBarUtil.setLightMode(this);
        initView();
        initListener();
    }

    private void initView() {
        etSearch = findViewById(R.id.et_search);
        etSearch.setFocusable(true);
        etSearch.requestFocus();
        KeyboardUtils.show(etSearch, 500);
    }

    private void initListener() {
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEvent.KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (!isOpen) {
                    etSearch.clearFocus();
                }
            }
        });
    }

}
