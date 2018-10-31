package dev.whaabaam.com.ui.home.myconnections;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.FragmentMyConnectionsBinding;
import dev.whaabaam.com.ui.base.BaseActivity;


public class MyConnectionsFragment extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentMyConnectionsBinding binding = DataBindingUtil.setContentView(this, R.layout.fragment_my_connections);
        MyConnectionsViewModel viewModel = new MyConnectionsViewModel(this);
        binding.setViewModel(viewModel);
        viewModel.initMyConnectionsList(binding.myConnectionsList);
    }


}
