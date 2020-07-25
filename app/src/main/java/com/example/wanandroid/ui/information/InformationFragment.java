package com.example.wanandroid.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.R;
import com.example.wanandroid.databinding.FragmentInformationBinding;
import com.example.wanandroid.ui.login.LoginActivity;

public class InformationFragment extends Fragment {

    private InformationViewModel notificationsViewModel;
    private FragmentInformationBinding dataBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(InformationViewModel.class);
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_information, container, false);
        final TextView textView = dataBinding.textNotifications.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        initView();
        return dataBinding.getRoot();
    }

    private void initView() {
        dataBinding.textNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}