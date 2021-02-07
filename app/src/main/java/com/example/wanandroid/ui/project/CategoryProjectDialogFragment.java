package com.example.wanandroid.ui.project;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ProjectBean;
import com.example.wanandroid.databinding.DialogCategoryProjectBinding;

import java.util.List;

public class CategoryProjectDialogFragment extends DialogFragment {

    private DialogCategoryProjectBinding dataBinding;
    private ProjectViewModel viewModel;
    private List<ProjectBean> mDataList;
    private Dialog dialog;
    private CategoryProjectListAdapter adapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_category_project, null, false);
        initView();
        subscribeUI();
        createDialog();
        return dialog;
    }

    private void subscribeUI() {
        viewModel.projectLiveData.observe(requireActivity(), projectBeans -> adapter.updateData(mDataList));
    }

    private void initView() {
        adapter = new CategoryProjectListAdapter(getActivity(), R.layout.item_project);
        dataBinding.rvCategoryProject.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.rvCategoryProject.setAdapter(adapter);
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
