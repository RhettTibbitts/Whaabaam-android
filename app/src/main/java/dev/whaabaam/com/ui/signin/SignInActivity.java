package dev.whaabaam.com.ui.signin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.EditText;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivitySignInBinding;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.utils.CommonUtils;

public class SignInActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignInBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        SignInActivityViewModel viewModel = new SignInActivityViewModel(this);
        binding.setViewModel(viewModel);
        setupPasswordTransformationMethod(binding.textInputLayout3.getEditText());
    }

    private void setupPasswordTransformationMethod(EditText password) {
        CommonUtils.setAstericPasswordTransformation(password);
    }
}
