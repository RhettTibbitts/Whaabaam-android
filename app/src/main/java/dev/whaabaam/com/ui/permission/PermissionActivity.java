package dev.whaabaam.com.ui.permission;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivityPermissionBinding;
import dev.whaabaam.com.ui.base.BaseActivity;

public class PermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPermissionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_permission);
        PermissionViewModel viewModel = new PermissionViewModel(this);
        binding.setViewModel(viewModel);
    }
}
