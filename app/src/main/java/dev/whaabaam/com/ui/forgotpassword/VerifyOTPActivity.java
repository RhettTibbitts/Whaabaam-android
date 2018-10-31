package dev.whaabaam.com.ui.forgotpassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.databinding.ActivityVerifyOtpBinding;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.ui.forgotpassword.ForgotPasswordActivityViewModel.getRequestForgotPinApi;

public class VerifyOTPActivity extends BaseActivity implements View.OnClickListener, ApiResponse {

    private ActivityVerifyOtpBinding binding;
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp);
        this.email = Objects.requireNonNull(getIntent().getExtras()).getString(AppKeys.EMAIL);
        binding.btnResend.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                String pinValue = Objects.requireNonNull(binding.pinView.getText()).toString();
                if (TextUtils.isEmpty(pinValue)) {
                    CommonUtils.toast(this, "Pin cannot be empty");
                } else if (pinValue.length() < 6) {

                    CommonUtils.toast(this, "Pin cannot be less than 6 digits.");
                } else {
                    requestVerifyOtpApi(pinValue);
                }
                break;
            case R.id.btnResend:
                requestResendPinApi();
                break;
            default:
                break;
        }

    }

    private void requestVerifyOtpApi(String pinValue) {
        ApiManager.getInstance().requestApi(this, getVerifyOtpRequestBody(pinValue),
                AppConstants.API_MODE.FORGOT_PASSWORD_VERIFY_OTP,
                this, true);
    }

    private JSONObject getVerifyOtpRequestBody(String pinValue) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.EMAIL, email);
            jsonObject.put("verify_code", pinValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        switch (apiMode) {
            case FORGOT_PASSWORD_VERIFY_OTP:
                Bundle bundle = new Bundle();
                bundle.putString(AppKeys.EMAIL, email);
                bundle.putString("security_code", response.get("security_code").getAsString());
                launchIntent(ResetPasswordActivity.class, bundle, false);
                break;
            case FORGOT_PASSWORD_REQUEST:
                binding.pinView.clearComposingText();
                AppDialog.getInstance(this).showMessageDialogToDismiss(this, response.get(AppKeys.MESSAGE).getAsString());
                break;
        }
    }

    private void requestResendPinApi() {
        ApiManager.getInstance().requestApi(this, getRequestForgotPinApi(email), AppConstants.API_MODE.FORGOT_PASSWORD_REQUEST,
                this, true);
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(this).showMessageDialogToDismiss(this, message);
    }
}
