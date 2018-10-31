package dev.whaabaam.com.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
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
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.signin.SignInActivityViewModel;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.userModel;

/**
 * @author rahul
 */
public class SignUpActivityViewModel extends BaseObservable implements ApiResponse {

    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> confirmPassword = new ObservableField<>();
    public final ObservableBoolean termsChecked = new ObservableBoolean(false);

    private Activity context;

    SignUpActivityViewModel(Activity context) {
        this.context = context;
    }

    public View.OnClickListener onSignUpClick(final Context context) {
        return view -> {
            ((SignUpActivity) context).hideKeyboard();
            if (Validation.isValidRegisterForm(context, firstName.get(), lastName.get(), email.get()
                    , password.get(), confirmPassword.get(), termsChecked.get())) {
                requestSignUpApi(context);
            }
        };
    }

    public View.OnClickListener onTermsClicked() {
        return v -> {
            String url = "http://whaabaam.com/terms-conditions";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(url));
        };
    }


    public void onCheckedChanged(CompoundButton button, boolean checked) {
        termsChecked.set(checked);
    }

    //--------------------END---------------------------------------

    private JSONObject getSignupRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.EMAIL, email.get());
            jsonObject.put(AppKeys.PASSWORD, password.get());
            jsonObject.put(AppKeys.FIRST_NAME, firstName.get());
            jsonObject.put(AppKeys.LAST_NAME, lastName.get());
            jsonObject.put("device_fcm_token", AppPreferences.getInstance(context).getString(AppPreferences.PREF_KEYS.FCM_TOKEN));
            jsonObject.put("device_type", "A");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void requestSignUpApi(Context context) {
        ApiManager.getInstance().requestApi(context, getSignupRequestBody(),
                AppConstants.API_MODE.REGISTER, this, true);
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        userModel = new Gson().fromJson(response.getAsJsonObject(AppKeys.DATA), UserModel.class);
        CommonUtils.saveUserData(userModel);
        CommonUtils.storeBooleanInPrefs(context, AppPreferences.PREF_KEYS.IS_LOGGED_IN, true);
        SignInActivityViewModel.handleFirstRunAfterLoginCase(context, userModel.getIs_profile_updated());
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }
}
