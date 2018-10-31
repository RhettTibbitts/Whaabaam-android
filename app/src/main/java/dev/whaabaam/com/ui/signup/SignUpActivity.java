package dev.whaabaam.com.ui.signup;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivitySignUpBinding;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.utils.CommonUtils;

/**
 * @author rahul
 */
public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        SignUpActivityViewModel viewModel = new SignUpActivityViewModel(this);
        binding.setViewModel(viewModel);
        setupPasswordTransformationMethod(Objects.requireNonNull(binding.textInputLayout6.getEditText()),
                Objects.requireNonNull(binding.textInputLayout7.getEditText()));
    }

    private void setupPasswordTransformationMethod(EditText password,
                                                   EditText confirmPassword) {
        CommonUtils.setAstericPasswordTransformation(password);
        CommonUtils.setAstericPasswordTransformation(confirmPassword);
    }
}
