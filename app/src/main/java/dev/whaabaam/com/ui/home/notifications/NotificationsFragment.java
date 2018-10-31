package dev.whaabaam.com.ui.home.notifications;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.whaabaam.com.databinding.FragmentNotificationsBinding;

import dev.whaabaam.com.R;

public class NotificationsFragment extends Fragment {


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentNotificationsBinding binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        NotificationsViewModel viewModel = new NotificationsViewModel(getActivity());
        viewModel.initMyConnectionsList(binding.myNotificationsList);
        return binding.getRoot();
    }

}
