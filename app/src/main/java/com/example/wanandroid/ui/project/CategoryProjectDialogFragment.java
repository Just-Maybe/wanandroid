package com.example.wanandroid.ui.project;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wanandroid.R;
import com.example.wanandroid.databinding.DialogCategoryProjectBinding;

public class CategoryProjectDialogFragment extends DialogFragment {

    private DialogCategoryProjectBinding dataBinding;
    private ProjectViewModel viewModel;
    private Dialog dialog;
    private CategoryProjectListAdapter adapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_category_project, null, false);
        initView();
        initData();
        subscribeUI();
        createDialog();
        return dialog;
    }

    private void initView() {
        adapter = new CategoryProjectListAdapter(getActivity(), R.layout.item_project);
        dataBinding.rvCategoryProject.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.rvCategoryProject.setAdapter(adapter);
        adapter.setCloseListner(() -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        window.setAttributes(attributes);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void initData() {
        viewModel.getHotProjectListFromNetwork();
    }

    private void subscribeUI() {
        viewModel.projectLiveData.observe(requireActivity(), projectBeans -> {
            adapter.updateData(projectBeans);
        });
    }

    private void createDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dataBinding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.translation);
    }
}
