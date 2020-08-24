package com.example.wanandroid.ui.arithmetic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.wanandroid.R;
import com.example.wanandroid.databinding.ActivityArithmeticDetailBinding;
import com.example.wanandroid.databinding.ActivityArticleDetailBinding;

/**
 * Created by Miracle on 2020/8/24
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class ArithmeticDetailActivity extends AppCompatActivity {
    static {
        System.loadLibrary("native-lib");
    }

    private ActivityArithmeticDetailBinding dataBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_arithmetic_detail);
        initView();
    }

    private void initView() {
        dataBinding.tvResult.setText("结果:" + getResult());
    }

    public native int getResult();
}
