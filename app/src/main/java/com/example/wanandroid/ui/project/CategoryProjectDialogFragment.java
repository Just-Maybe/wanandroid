package com.example.wanandroid.ui.project;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.R;
import com.example.wanandroid.databinding.DialogCategoryProjectBinding;

public class CategoryProjectDialogFragment extends DialogFragment {

    private DialogCategoryProjectBinding dataBinding;
    private ProjectViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_category_project, null, false);
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dataBinding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.translation);
        return dialog;
    }
}
