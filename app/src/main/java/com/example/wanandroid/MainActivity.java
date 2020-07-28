package com.example.wanandroid;

import android.os.Bundle;

import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.utils.StatusBarUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity {
    private GlobalViewModel globalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, findViewById(R.id.container));
        StatusBarUtil.setLightMode(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        globalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);
        globalViewModel.getCategoryTreeListFromNetwork();
    }

}