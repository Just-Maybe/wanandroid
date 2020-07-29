package com.example.wanandroid.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wanandroid.GlobalViewModel;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.CategoryTreeBean;
import com.example.wanandroid.databinding.FragmentCategoryBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private static FragmentCategoryBinding databinding;
    private CategoryDetailListAdapter adapter;
    private GlobalViewModel globalViewModel;//用ViewModel 将Fragment与Activity 通讯
    private static List<CategoryDetailFragment> categoryFragmentList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        globalViewModel = new ViewModelProvider(requireActivity()).get(GlobalViewModel.class);
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        initTabLayout();
        initViewPager2();
        initData();
        return databinding.getRoot();
    }

    private void initData() {

    }

    private void initTabLayout() {
        categoryFragmentList = new ArrayList<>();
        databinding.tablayout.setTabIndicatorFullWidth(false);
        globalViewModel.categoryList.observe(getViewLifecycleOwner(), categoryTreeBeans -> {
            for (int i = 0; i < categoryTreeBeans.size(); i++) {
                CategoryTreeBean categoryTree = categoryTreeBeans.get(i);
                databinding.tablayout.addTab(databinding.tablayout.newTab().setText(categoryTree.getName()));
                categoryFragmentList.add(CategoryDetailFragment.newInstance(categoryTree.getName(), categoryTree.getId(), i));
            }

            adapter = new CategoryDetailListAdapter(this, categoryFragmentList);
            databinding.viewPager.setAdapter(adapter);
            //关联 TabLayout viewpager2
            new TabLayoutMediator(databinding.tablayout, databinding.viewPager, (tab, position) -> {
                tab.setText(categoryTreeBeans.get(position).getName());
            }).attach();
        });

    }

    private void initViewPager2() {

    }


}