package com.example.wanandroid;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.ui.category.CategoryFragment;
import com.example.wanandroid.ui.home.HomeFragment;
import com.example.wanandroid.ui.information.InformationFragment;
import com.example.wanandroid.ui.project.ProjectFragment;
import com.example.wanandroid.utils.StatusBarUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private GlobalViewModel globalViewModel;
    private List<Fragment> fragmentList;
    private int lastPosition;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private InformationFragment informationFragment;
    private BottomNavigationView navView;
    private ProjectFragment projectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, findViewById(R.id.container));
        StatusBarUtil.setLightMode(this);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupWithNavController(navView, navController);
        initData();
        initNavigationView();
        switchFragment(0);
        globalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);
        globalViewModel.getCategoryTreeListFromNetwork();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        projectFragment = new ProjectFragment();
        informationFragment = new InformationFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(categoryFragment);
        fragmentList.add(projectFragment);
        fragmentList.add(informationFragment);
    }

    private void initNavigationView() {
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        switchFragment(0);
                        break;
                    case R.id.navigation_dashboard:
                        switchFragment(1);
                        break;
                    case R.id.navigation_project:
                        switchFragment(2);
                        break;
                    case R.id.navigation_notifications:
                        switchFragment(3);
                        break;
                }
                return true;
            }
        });
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    public CategoryFragment getCategoryFragment() {
        return categoryFragment;
    }

    public InformationFragment getInformationFragment() {
        return informationFragment;
    }

    public void switchFragment(int position) {
        navView.getMenu().getItem(position).setChecked(true);
        Fragment targetFragment = fragmentList.get(position);
        Fragment currentFragment = fragmentList.get(lastPosition);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.nav_host_fragment, targetFragment)
                    .show(targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        lastPosition = position;
    }

}