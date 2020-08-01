package com.example.wanandroid.ui.category;

import android.os.Binder;
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
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.CategoryTreeBean;
import com.example.wanandroid.databinding.FragmentCategoryBinding;
import com.example.wanandroid.utils.StringUtils;
import com.example.wanandroid.view.CategoryDialogFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements View.OnClickListener {

    private static FragmentCategoryBinding databinding;
    private CategoryDetailListAdapter adapter;
    private GlobalViewModel globalViewModel;//用ViewModel 将Fragment与Activity 通讯
    private static List<CategoryDetailFragment> categoryFragmentList;
    private List<String> categoryIdList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        globalViewModel = new ViewModelProvider(requireActivity()).get(GlobalViewModel.class);
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        databinding.ivMenu.setOnClickListener(this);
        initTabLayout();
        initViewPager2();
        initData();
        return databinding.getRoot();
    }

    private void initData() {

    }

    private void initTabLayout() {
        categoryFragmentList = new ArrayList<>();
        categoryIdList = new ArrayList<>();
        databinding.tablayout.setTabIndicatorFullWidth(false);
        globalViewModel.categoryList.observe(getViewLifecycleOwner(), categoryTreeBeans -> {
            for (int i = 0; i < categoryTreeBeans.size(); i++) {
                CategoryTreeBean categoryTree = categoryTreeBeans.get(i);
                categoryIdList.add(categoryTree.getName());
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

    int index = 0;

    /**
     * 选择分类
     *
     * @param pName 父分类 名称
     * @param cid   子分类id
     */
    public void selectCategory(String pName, int cid) {
        if (!StringUtils.isEmpty(pName)) {
            index = categoryIdList.indexOf(pName);
            if (index == -1) {
                return;
            }
            databinding.viewPager.setCurrentItem(index);
        }
        if (cid != 0) {
            databinding.viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    categoryFragmentList.get(index).setSelectCategory(cid);
                }
            }, 300);
        }
    }

    public void selectCategory(String pName) {
        selectCategory(pName, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                openCategoryDialog();
                break;
        }
    }

    private void openCategoryDialog() {
        CategoryDialogFragment dialog = new CategoryDialogFragment();
        dialog.show(getParentFragmentManager(), "Category");
        dialog.setListener(new CategoryDialogFragment.onItemClickListener() {
            @Override
            public void onItemClick(CategoryTreeBean treeBean) {
                selectCategory(treeBean.getName());
                dialog.dismiss();
            }
        });
    }
}