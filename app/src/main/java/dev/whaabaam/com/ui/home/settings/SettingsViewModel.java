package dev.whaabaam.com.ui.home.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonObject;
import com.quickblox.chat.QBChatService;

import org.json.JSONObject;

import dev.whaabaam.com.BuildConfig;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.quickblox.QuickbloxUtils;
import dev.whaabaam.com.services.LocationUpdateServices;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.changepassword.ChangePasswordActivity;
import dev.whaabaam.com.ui.home.myconnections.MyConnectionsFragment;
import dev.whaabaam.com.ui.myprofile.MyProfileActivity;
import dev.whaabaam.com.ui.notificationprefs.NotificationsPreferencesActivity;
import dev.whaabaam.com.ui.signin.SignInActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.OnDialogOptionsClickCallback;

import static dev.whaabaam.com.app.MyApplication.context;
import static dev.whaabaam.com.app.MyApplication.userModel;

public class SettingsViewModel extends BaseObservable implements ApiResponse, OnDialogOptionsClickCallback {

    private Activity context;

    SettingsViewModel(Activity context) {
        this.context = context;
    }
    /*
     * BEGIN: on click listeners
     * */

    // notification preferences
    public View.OnClickListener onNotificationPreferncesClick(final Context context) {
        return view -> ((BaseActivity) context).launchIntent(NotificationsPreferencesActivity.class, null, false);
    }

    // my profile
    public View.OnClickListener onMyProfileClick(final Context context) {
        return view -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(AppPreferences.PREF_KEYS.IS_FIRST_RUN, false);
            ((BaseActivity) context).launchIntent(MyProfileActivity.class, bundle, false);
        };
    }

    // change password
    public View.OnClickListener onChangePasswordClick(final Context context) {
        return view -> ((BaseActivity) context).launchIntent(ChangePasswordActivity.class, null, false);
    }

    public View.OnClickListener onMyConnectionsClick(final Context context) {
        return view -> ((BaseActivity) context).launchIntent(MyConnectionsFragment.class, null, false);
    }

    public View.OnClickListener onContactUsClick() {
        return view -> {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "admin@whaabaam.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
            emailIntent.putExtra(Intent.EXTRA_TEXT, getEmailBody());
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));

        };
    }

    private String getEmailBody() {

        return "\n\n\nApp Version: " +
                BuildConfig.VERSION_NAME +
                "\nOS version: " +
                Build.VERSION.SDK_INT +
                "\nUserID: " +
                userModel.getId() +
                "\nEmail ID: " +
                userModel.getEmail() +
                "\n\n\nSent from " +
                Build.MODEL;
    }


    //sign out
    public View.OnClickListener onSignOutClick(final Context context) {
        return view -> requestLogoutAPI(context);
    }
    //-----------------END----------------

    private void requestLogoutAPI(Context context) {
        ApiManager.getInstance().requestApi(context, getLogoutRequestBody(), AppConstants.API_MODE.LOGOUT,
                this, true);
    }

    private JSONObject getLogoutRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        QuickbloxUtils.logoutChatService(QBChatService.getInstance());
        AppDialog.getInstance(context).showMessageDialogWithAction(context,
                response.get(AppKeys.MESSAGE).getAsString(), this);
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    @Override
    public void onPositiveClick(boolean value) {
        /*
         * Launch a new instance of the app, and reset user token
         * */
        context.stopService(new Intent(context, LocationUpdateServices.class));
        MyApplication.getInstance().resetUserData(context);
        CommonUtils.startNewTaskInstance(context, SignInActivity.class);
    }
}
