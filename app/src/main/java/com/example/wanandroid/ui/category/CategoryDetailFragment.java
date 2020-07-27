package com.example.wanandroid.ui.category;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.CategoryTreeBean;
import com.example.wanandroid.databinding.FragmentCategoryDetailBinding;
import com.example.wanandroid.ui.search.SearchActivity;
import com.example.wanandroid.utils.CommonUtils;
import com.example.wanandroid.utils.KeyboardUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

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
    private CategoryViewModel viewModel;
    private CategoryTreeBean categoryTree;

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
        return databinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        viewModel.categoryList.observe(getViewLifecycleOwner(), categoryTreeBeans -> {
            categoryTree = categoryTreeBeans.get(position);
            for (CategoryTreeBean child : categoryTree.getChildren()) {
                databinding.rgSubCategory.addView(createHokeyText(child.getName()));
            }
        });
    }

    private void initView() {

    }

    /**
     * 创建热搜词汇
     *
     * @param text
     * @return
     */
    private TextView createHokeyText(String text) {
        TextView tv = new TextView(getActivity());
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = CommonUtils.dp2px(getActivity(), 12);
        params.leftMargin = CommonUtils.dp2px(getActivity(), 10);
        params.bottomMargin = CommonUtils.dp2px(getActivity(), 15);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv.setBackground(getResources().getDrawable(R.drawable.shape_category_bg));
        tv.setOnClickListener(v -> {

        });
        tv.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        tv.setLayoutParams(params);
        tv.setText(text);
        return tv;
    }
}
