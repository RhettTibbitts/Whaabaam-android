package dev.whaabaam.com.ui.signinsignupchooser;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivitySignInSignUpChooserBinding;
import dev.whaabaam.com.ui.base.BaseActivity;

public class SignInSignUpChooserActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignInSignUpChooserBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_sign_in_sign_up_chooser);
        SignInSignUpChooserViewModel viewModel = new SignInSignUpChooserViewModel(this);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
