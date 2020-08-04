package com.example.wanandroid.ui.category;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wanandroid.GlobalViewModel;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.CategoryTreeBean;
import com.example.wanandroid.databinding.FragmentCategoryDetailBinding;
import com.example.wanandroid.listener.RvLoadMoreListener;
import com.example.wanandroid.ui.home.ArticleAdapter;
import com.example.wanandroid.utils.CommonUtils;

/**
 * Created by Miracle on 2020/7/27
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class CategoryDetailFragment extends Fragment {

    private FragmentCategoryDetailBinding databinding;
    private String title;
    private int id;
    private int position;
    private GlobalViewModel globalViewModel;
    private CategoryViewModel categoryViewModel;
    private CategoryTreeBean categoryTree;
    private ArticleAdapter adapter;


    public static CategoryDetailFragment newInstance(String title, int id, int position) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("id", id);
        args.putInt("position", position);
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getInt("id");
            title = arguments.getString("title");
            position = arguments.getInt("position");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_detail, container, false);
        initView();
        initData();
        initListener();
        subscribeUI();
        return databinding.getRoot();
    }

    private void initData() {
        globalViewModel = new ViewModelProvider(requireActivity()).get(GlobalViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

    }

    private void initView() {
        adapter = new ArticleAdapter(getActivity());
        databinding.rvSubCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        databinding.rvSubCategory.setAdapter(adapter);

        databinding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        databinding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    private void subscribeUI() {
        categoryViewModel.categoryArticleList.observe(getViewLifecycleOwner(), articleBeans -> {
            if (categoryViewModel.page == 0) {
                adapter.setData(articleBeans);
            } else {
                adapter.addData(articleBeans);
            }
        });

        globalViewModel.categoryList.observe(getViewLifecycleOwner(), categoryTreeBeans -> {
            categoryTree = categoryTreeBeans.get(position);
            for (int position = 0; position < categoryTree.getChildren().size(); position++) {
                databinding.rgSubCategory.addView(createSubCategoryText(categoryTree.getChildren().get(position), position));
            }
        });
    }

    private void initListener() {
        databinding.rgSubCategory.setOnCheckedChangeListener((group, checkedId) -> {
            categoryViewModel.page = 0;
            categoryViewModel.getCategoryArticleListFromNetwork(checkedId);
        });

        databinding.rvSubCategory.addOnScrollListener(new RvLoadMoreListener() {

            @Override
            public void loadMoreData() {
                categoryViewModel.getCategoryArticleListFromNetwork(databinding.rgSubCategory.getCheckedRadioButtonId());
            }

            @Override
            public int getItemCount() {
                return adapter.getItemCount();
            }
        });

        categoryViewModel.isLoadData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadData) {
                databinding.refreshLayout.setRefreshing(isLoadData);
            }
        });

        adapter.setListener(new ArticleAdapter.onClickItemListener() {
            @Override
            public void onClickSubCategory(ArticleBean articleBean) {

            }

            @Override
            public void onClickCategory(ArticleBean bean) {

            }

            @Override
            public void onCollectedArticle(boolean isCollect, ArticleBean bean) {
                if (isCollect) {
                    categoryViewModel.collectArticle(bean.getId());
                } else {
                    categoryViewModel.uncollectArticle(bean.getId());
                }
            }
        });
    }


    /**
     * 创建热搜词汇
     *
     * @param bean
     * @return
     */
    private RadioButton createSubCategoryText(CategoryTreeBean bean, int position) {
        RadioButton btn = new RadioButton(getActivity());
        if (position == 0) {
            btn.setChecked(true);
        }
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = CommonUtils.dp2px(getActivity(), 10);
        params.bottomMargin = CommonUtils.dp2px(getActivity(), 5);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        btn.setButtonDrawable(null);
        btn.setId(bean.getId());
        btn.setTextColor(getResources().getColorStateList(R.color.selector_sub_category_text_color));
        btn.setBackground(getResources().getDrawable(R.drawable.selector_sub_category_text_bg));
        btn.setLayoutParams(params);
        btn.setText(bean.getName());
        return btn;
    }

    public void setSelectCategory(int cid) {
        databinding.rgSubCategory.check(cid);
    }
}
