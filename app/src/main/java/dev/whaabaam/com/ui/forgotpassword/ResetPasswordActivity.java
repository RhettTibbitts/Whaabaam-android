package dev.whaabaam.com.ui.forgotpassword;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import dev.whaabaam.com.R;
import dev.whaabaam.com.Validation;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.databinding.ActivityResetPasswordBinding;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.signin.SignInActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener, ApiResponse {

    private ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        binding.button6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        if (v.getId() == R.id.button6) {
            if (Validation.isValidResetPasswordForm(binding.textInputLayout10.getEditText().getText().toString(),
                    binding.textInputLayout11.getEditText().getText().toString())) {
                requestResetPasswordApi();
            }
        }
    }

    private void requestResetPasswordApi() {
        ApiManager.getInstance().requestApi(this, getResetPasswordRequestBody(), AppConstants.API_MODE.RESET_PASSWORD,
                this, true);
    }

    private JSONObject getResetPasswordRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.EMAIL, getIntent().getExtras().getString(AppKeys.EMAIL));
            jsonObject.put("security_code", getIntent().getExtras().getString("security_code"));
            jsonObject.put("password", binding.textInputLayout11.getEditText().getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(this).showMessageDialogWithAction(this, response.get(AppKeys.MESSAGE).getAsString(),
                c -> {
                    CommonUtils.startNewTaskInstance(this, SignInActivity.class);
                    finish();
                });
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(this).showMessageDialogToDismiss(this, message);
    }
}
