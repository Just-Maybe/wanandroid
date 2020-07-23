package com.example.wanandroid.ui.search;

import android.content.Context;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.HotKeyBean;
import com.example.wanandroid.utils.CommonUtils;
import com.example.wanandroid.utils.KeyboardUtils;
import com.example.wanandroid.utils.KeyboardVisibilityEvent;
import com.example.wanandroid.utils.StatusBarUtil;
import com.google.android.flexbox.FlexboxLayout;

import org.antlr.v4.misc.Utils;

import java.util.List;

/**
 * Created by Miracle on 2020/7/21
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etSearch;
    private FlexboxLayout layoutHotKey;//热门搜索
    private SearchViewModel viewModel;
    private ImageView ivBack;

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
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        etSearch = findViewById(R.id.et_search);
        KeyboardUtils.openKeyboard(this, etSearch);
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
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                }
                return false;
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
        }
    }

}
