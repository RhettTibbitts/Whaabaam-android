package dev.whaabaam.com.ui.forgotpassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivityForgotPasswordBinding;
import dev.whaabaam.com.ui.base.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityForgotPasswordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        ForgotPasswordActivityViewModel viewModel = new ForgotPasswordActivityViewModel(this);
        binding.setViewModel(viewModel);
    }
}
