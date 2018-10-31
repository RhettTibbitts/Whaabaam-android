package dev.whaabaam.com.ui.signin;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import dev.whaabaam.com.Validation;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.forgotpassword.ForgotPasswordActivity;
import dev.whaabaam.com.ui.home.HomeActivity;
import dev.whaabaam.com.ui.myprofile.MyProfileActivity;
import dev.whaabaam.com.ui.signup.SignUpActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.TOKEN;
import static dev.whaabaam.com.app.MyApplication.context;
import static dev.whaabaam.com.app.MyApplication.userModel;
import static dev.whaabaam.com.utils.CommonUtils.requestLocationUpdateTask;

public class SignInActivityViewModel extends BaseObservable implements ApiResponse {

    private Activity mContext;
    private ObservableField<String> email = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    public final ObservableBoolean isRememberChecked = new ObservableBoolean();

    // constructor
    SignInActivityViewModel(Activity context) {
        this.mContext = context;
        setupRememberedData();
    }

    private void setupRememberedData() {
        if (AppPreferences.getInstance(mContext).getBoolean(AppPreferences.PREF_KEYS.IS_REMEMBER_CHECKED)) {
            email.set(CommonUtils.getRememberedData(mContext, 1));
            password.set(CommonUtils.getRememberedData(mContext, 2));
            isRememberChecked.set(true);
        }
    }

    /*
     * BEGIN: Getter and Setters
     * */

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
        notifyChange();
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
        notifyChange();
    }
    //-------------------END---------------------------------

    /*
     * BEGIN: onClick Listeners
     * */

    // sign in click
    public View.OnClickListener onSignInClick(final Context context) {
        return view -> {
            if (Validation.isValidLoginForm(context, getEmail(), getPassword()))
                doLogin(context);
        };
    }

    // sign up click
    public View.OnClickListener onSignUpClick(final Context context) {
        return view -> {
            ((SignInActivity) context).hideKeyboard();
            ((SignInActivity) context).launchIntent(SignUpActivity.class, null, false);
        };
    }

    // forgot click
    public View.OnClickListener onForgotClick(final Context context) {
        return view -> {
            ((SignInActivity) context).hideKeyboard();
            ((SignInActivity) context).launchIntent(ForgotPasswordActivity.class, null, false);
        };
    }

    //---------------------END---------------------

    // user defined methods

    private void doLogin(Context context) {
        ApiManager.getInstance().requestApi(context, getLoginRequestBody(),
                AppConstants.API_MODE.LOGIN, this, true);
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        CommonUtils.saveRememberMeData(mContext, email.get(), password.get(), isRememberChecked.get());
        CommonUtils.storeStringInPrefs(mContext, AppPreferences.PREF_KEYS.TOKEN, TOKEN);

        Log.e("login", response.toString());

        TOKEN = response.get(AppKeys.TOKEN).getAsString();
        JsonObject data = response.getAsJsonObject(AppKeys.DATA);

        userModel = new Gson().fromJson(data.toString(), UserModel.class);
        CommonUtils.storeBooleanInPrefs(mContext, AppPreferences.PREF_KEYS.IS_LOGGED_IN, true);
        CommonUtils.saveUserData(userModel);
        requestLocationUpdateTask(mContext);
        handleFirstRunAfterLoginCase(mContext, userModel.getIs_profile_updated());
    }

    public static void handleFirstRunAfterLoginCase(Context mContext, int isProfileUpdated) {

        if (isProfileUpdated == 1) {
            CommonUtils.startNewTaskInstance(context, HomeActivity.class);
            ((BaseActivity) context).finish();
        } else {
            ((BaseActivity) mContext).launchIntent(MyProfileActivity.class, CommonUtils.getFirstRunBundle(), true);
        }
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(mContext).showMessageDialogToDismiss(mContext, message);
    }

    private JSONObject getLoginRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.EMAIL, getEmail());
            jsonObject.put(AppKeys.PASSWORD, getPassword());
            jsonObject.put(AppKeys.DEVICE_FCM_TOKEN, AppPreferences.getInstance(mContext)
                    .getString(AppPreferences.PREF_KEYS.FCM_TOKEN));
            jsonObject.put(AppKeys.DEVICE_TYPE, AppConstants.DEVICE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void onCheckedChanged(CompoundButton btb, boolean isChecked) {
        isRememberChecked.set(isChecked);
    }

}
