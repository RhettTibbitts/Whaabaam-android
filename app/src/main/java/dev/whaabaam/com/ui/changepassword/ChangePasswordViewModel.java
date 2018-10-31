package dev.whaabaam.com.ui.changepassword;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import dev.whaabaam.com.Validation;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.signin.SignInActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.OnDialogOptionsClickCallback;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class ChangePasswordViewModel extends BaseObservable implements ApiResponse, OnDialogOptionsClickCallback {
    private ObservableField<String> currentPassword = new ObservableField<>();
    private ObservableField<String> newPassword = new ObservableField<>();
    private ObservableField<String> confirmNewPassword = new ObservableField<>();

    private Activity context;

    public ChangePasswordViewModel(Activity context) {
        this.context = context;
    }
    /*
     * BEGIN: Getter and Setter
     * */

    public String getCurrentPassword() {
        return currentPassword.get();
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword.set(currentPassword);
        notifyChange();
    }

    public String getNewPassword() {
        return newPassword.get();
    }

    public void setNewPassword(String newPassword) {
        this.newPassword.set(newPassword);
        notifyChange();
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword.get();
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword.set(confirmNewPassword);
        notifyChange();
    }
    //------------END-------------


    public View.OnClickListener onUpdateClick(final Context context) {
        return view -> {
            if (Validation.isValidChangePasswordForm(context, getCurrentPassword(), getNewPassword(), getConfirmNewPassword())) {
                requestChangePasswordApi(context);
            }
        };
    }

    private void requestChangePasswordApi(Context context) {
        ApiManager.getInstance().requestApi(context,getChangePasswordRequestBody(),
                AppConstants.API_MODE.CHANGE_PASSWORD, this, true);
    }

    private JSONObject getChangePasswordRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put(AppKeys.CURRENT_PASSWORD, getCurrentPassword());
            jsonObject.put(AppKeys.NEW_PASSWORD, getNewPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogWithAction(context, response.get(AppKeys.MESSAGE).getAsString(), this);
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context)
                .showMessageDialogToDismiss(context, message);
    }

    @Override
    public void onPositiveClick(boolean value) {
        // navigate user to sign in screen and ask to login again after password has been changed
        CommonUtils.startNewTaskInstance(context, SignInActivity.class);
    }
}
