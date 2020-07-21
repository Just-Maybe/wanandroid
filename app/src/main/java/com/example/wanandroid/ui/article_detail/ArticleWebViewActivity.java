package com.example.wanandroid.ui.article_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.wanandroid.R;
import com.example.wanandroid.databinding.ActivityArticleDetailBinding;
import com.example.wanandroid.utils.StatusBarUtil;

/**
 * Created by Miracle on 2020/7/21
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class ArticleWebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityArticleDetailBinding dataBinding;
    private String title;
    private String link;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);
        StatusBarUtil.setTranslucent(this);
        initWebView();
        initData();
    }

    private void initWebView() {
        dataBinding.webView.getSettings().setDomStorageEnabled(true);
        dataBinding.webView.getSettings().setAppCacheEnabled(true);
        dataBinding.webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        dataBinding.webView.getSettings().setAllowFileAccess(true);
        dataBinding.webView.getSettings().setJavaScriptEnabled(true);
    }

    private void initData() {
        title = getIntent().getStringExtra("title");
        link = getIntent().getStringExtra("link");
        dataBinding.ivBack.setOnClickListener(this);
        dataBinding.tvTitle.setText(title);
        dataBinding.webView.loadUrl(link);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
