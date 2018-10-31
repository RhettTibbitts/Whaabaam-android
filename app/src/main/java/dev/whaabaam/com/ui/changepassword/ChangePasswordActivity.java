package dev.whaabaam.com.ui.changepassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivityChangePasswordBinding;
import dev.whaabaam.com.ui.base.BaseActivity;

public class ChangePasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChangePasswordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        binding.setViewModel(new ChangePasswordViewModel(this));
    }
}
