package com.example.wanandroid.view;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wanandroid.GlobalViewModel;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.CategoryTreeBean;
import com.example.wanandroid.ui.search.SearchActivity;
import com.example.wanandroid.utils.CommonUtils;
import com.example.wanandroid.utils.KeyboardUtils;
import com.example.wanandroid.utils.ScreenUtils;
import com.example.wanandroid.utils.SpUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class CategoryDialogFragment extends DialogFragment {
    GlobalViewModel globalViewModel;
    private View root;
    private onItemClickListener listener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.category_dialog_style);
        globalViewModel = new ViewModelProvider(requireActivity()).get(GlobalViewModel.class);
    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCanceledOnTouchOutside(true);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.translation);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.horizontalMargin = 30;
        attributes.windowAnimations = R.style.DialogBottomAnimation;
        window.setAttributes(attributes);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dp2px(getActivity(),500));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_category, container, false);
        initView();
        return root;
    }

    private void initView() {
        ImageView ivBack = root.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().hide();
            }
        });

        FlexboxLayout flexboxLayout = root.findViewById(R.id.layout_flexbox);
        globalViewModel.categoryList.observe(getViewLifecycleOwner(), new Observer<List<CategoryTreeBean>>() {
            @Override
            public void onChanged(List<CategoryTreeBean> categoryTreeBeans) {
                for (CategoryTreeBean treeBean : categoryTreeBeans) {
                    flexboxLayout.addView(createHokeyText(treeBean.getName(), treeBean));
                }
            }
        });
    }

    public interface onItemClickListener {
        void onItemClick(CategoryTreeBean treeBean);
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 创建热搜词汇
     *
     * @param text
     * @return
     */
    private TextView createHokeyText(String text, CategoryTreeBean treeBean) {
        TextView tv = new TextView(getActivity());
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = CommonUtils.dp2px(getActivity(), 18);
        params.bottomMargin = CommonUtils.dp2px(getActivity(), 15);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(treeBean);
            }
        });
        tv.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        tv.setBackground(getResources().getDrawable(R.drawable.shape_dialog_catetgory_text_bg));
        tv.setLayoutParams(params);
        tv.setText(text);
        return tv;
    }
}
