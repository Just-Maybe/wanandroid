package com.example.wanandroid.ui.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.databinding.FragmentProjectBinding;

/**
 * Created by Miracle on 2020/7/31
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class ProjectFragment extends Fragment {

    private FragmentProjectBinding databinding;
    private ProjectAdapter adapter;
    private ProjectViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        initView();
        subscribeUI();
        iniData();
        return databinding.getRoot();
    }

    private void iniData() {
        viewModel.getProjectCategoryListFromNetwork();
        viewModel.getHotProjectListFromNetwork();
    }

    private void initView() {
        adapter = new ProjectAdapter(getActivity());
        adapter.setListener((view, projectCategoryBean) -> {
            CategoryProjectDialogFragment dialogFragment = new CategoryProjectDialogFragment();
            dialogFragment.show(getChildFragmentManager(), "");
        });
        databinding.rvProject.setLayoutManager(new LinearLayoutManager(getActivity()));
        databinding.rvProject.setAdapter(adapter);
        databinding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        databinding.refreshLayout.setOnRefreshListener(() -> {
            viewModel.page = 0;
            viewModel.getProjectCategoryListFromNetwork();
            viewModel.getHotProjectListFromNetwork();
        });
    }

    private void subscribeUI() {
        viewModel.projectLiveData.observe(getViewLifecycleOwner(), projectBeans -> {
            if (viewModel.page == 0) {
                adapter.setData(projectBeans);
            } else {
                adapter.addData(projectBeans);
            }
        });
        viewModel.projectCategoryLiveData.observe(getViewLifecycleOwner(), projectCategoryBeans -> {
            adapter.setCategoryList(projectCategoryBeans);
        });
        viewModel.isLoadData.observe(getViewLifecycleOwner(), isLoadData -> {
            databinding.refreshLayout.setRefreshing(isLoadData);
        });
    }
}
