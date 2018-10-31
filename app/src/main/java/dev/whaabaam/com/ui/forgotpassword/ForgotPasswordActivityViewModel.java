package dev.whaabaam.com.ui.forgotpassword;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import dev.whaabaam.com.Validation;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

public class ForgotPasswordActivityViewModel extends BaseObservable implements ApiResponse {

    private ObservableField<String> email = new ObservableField<>();
    private Context context;

    // constructor
    public ForgotPasswordActivityViewModel(Context context) {
        this.context = context;
    }

    /*
     * BEGIN: Getter and Setter
     * */

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
        notifyChange();
    }
//-------------END-----------------


    /*
     * Begin: onClick Listeners
     * */
    // reset password
    public View.OnClickListener onResetPasswordClick(final Context context) {
        return view -> {
            ((BaseActivity) context).hideKeyboard();
            if (CommonUtils.isOnline(context)) {
                if (Validation.isValidForgotPasswordForm(context, getEmail())) {
                    requestForgotPasswordAPI(context);
                }
            }
        };
    }

    private void requestForgotPasswordAPI(Context context) {
        ApiManager.getInstance().requestApi(context, getRequestForgotPinApi(getEmail()),
                AppConstants.API_MODE.FORGOT_PASSWORD_REQUEST,
                this, true);
    }

    static JSONObject getRequestForgotPinApi(String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.EMAIL, email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    // login
    public View.OnClickListener onLoginClick(final Context context) {
        return view -> ((AppCompatActivity) context).onBackPressed();
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
//        AppDialog.getInstance(context).showMessageDialogToDismiss(context, response.get(AppKeys.MESSAGE).getAsString());

        Bundle bundle = new Bundle();
        bundle.putString(AppKeys.EMAIL, getEmail());
        ((BaseActivity) context).launchIntent(VerifyOTPActivity.class, bundle, false);
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    //---------------END ------------------

}
