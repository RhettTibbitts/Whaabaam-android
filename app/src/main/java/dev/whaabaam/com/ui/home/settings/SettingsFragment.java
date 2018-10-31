package dev.whaabaam.com.ui.home.settings;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.whaabaam.com.databinding.FragmentSettingsBinding;

import dev.whaabaam.com.R;

public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSettingsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        binding.setViewModel(new SettingsViewModel(getActivity()));
        return binding.getRoot();
    }

}
