package com.example.wanandroid.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.databinding.ActivityLoginBinding;
import com.example.wanandroid.utils.StatusBarUtil;
import com.example.wanandroid.utils.StringUtils;

/**
 * Created by Miracle on 2020/7/25
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private boolean isLogin = true; //  登录或注册状态
    private ActivityLoginBinding databinding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, findViewById(R.id.layout_title));
        StatusBarUtil.setLightMode(this);
        initView();
        subscribe();
    }

    private void initView() {
        databinding.setIsLogin(isLogin);
        databinding.ivBack.setOnClickListener(this);
        databinding.tvRegister.setOnClickListener(this);
        databinding.btnConfirm.setOnClickListener(this);
    }

    private void subscribe() {
        viewModel.isLogin.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogin) {
                if (isLogin) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_register:
                databinding.setIsLogin(!isLogin);
                isLogin = !isLogin;
                break;
            case R.id.btn_confirm:
                if (isLogin) {
                    Login();
                } else {
                    register();
                }
                break;
        }
    }

    private void Login() {
        String username = databinding.etUsername.getText().toString();
        String password = databinding.etPassword.getText().toString();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.login(username, password);
    }

    private void register() {
        String username = databinding.etUsername.getText().toString();
        String password = databinding.etPassword.getText().toString();
        String repassword = databinding.etRePassword.getText().toString();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(repassword)) {
            Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(this, "请输入相同密码", Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.register(username, password, repassword);
    }
}
