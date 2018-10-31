package dev.whaabaam.com;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.utils.AppDialog;

public class Validation {


    /*
     * Login form validation
     * */
    public static boolean isValidLoginForm(Context context, String email, String password) {
        boolean isValid = false;
        if (TextUtils.isEmpty(email) || !Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS), email))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_email));
        else if (TextUtils.isEmpty(password))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_password));
        else
            isValid = true;
        return isValid;
    }

    /*
     * Forgot Password Form Validation
     * */
    public static boolean isValidForgotPasswordForm(Context context, String email) {
        boolean isValid = false;
        if (TextUtils.isEmpty(email) || !Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS), email))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_email));
        else isValid = true;
        return isValid;
    }

    /*
     * Register form validation
     * */
    public static boolean isValidRegisterForm(Context context, String fName, String lName,
                                              String email, String pass, String confrmPass,
                                              boolean tersmChecked) {
        boolean isValid = false;
        if (TextUtils.isEmpty(fName))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_first_name));
        else if (TextUtils.isEmpty(lName))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_last_name));
        else if (TextUtils.isEmpty(email) || !Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS), email))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_email));
        else if (TextUtils.isEmpty(pass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_password));
        else if (TextUtils.isEmpty(confrmPass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_empty_confirm_password));
        else if (!pass.equals(confrmPass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_passwords_unequal));
        else if(!tersmChecked)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_accept_terms));
        else isValid = true;

        return isValid;
    }

    public static boolean isValidChangePasswordForm(Context context, String currentPass, String newPass, String confPass) {
        boolean isValid = false;
        if (TextUtils.isEmpty(currentPass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_password));
        else if (TextUtils.isEmpty(newPass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_empty_new_password));
        else if (TextUtils.isEmpty(confPass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_empty_confirm_password));
        else if (!newPass.equals(confPass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_passwords_unequal));
        else isValid = true;

        return isValid;
    }

    public static boolean isValidProfileForm(Context context, String email, String fName, int state_id,
                                             int city_id, int military_id, int political_id,
                                             int religion_id, int relationship_id, boolean firstRun, String profileImage, int fromStateId,
                                             int fromCityId) {
        boolean isValid = false;
        if (TextUtils.isEmpty(email) || !Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS), email))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_email));
        else if (TextUtils.isEmpty(fName))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_first_name));
        else if (state_id == -1)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Select a valid Current state");
        else if (city_id == -1)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Select a valid Current city");
        else if (fromStateId == -1)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Select a valid From state");
        else if (fromCityId == -1)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Select a valid From city");
        else if (military_id == -1)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Select a valid Military");
        else if (political_id == -1)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Select a valid Political Affiliation");
        else if (religion_id == -1)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Select a valid Religion");
        else if (relationship_id == -1)
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Select a valid Relationship Status");
        else if (firstRun && TextUtils.isEmpty(profileImage))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Upload a profile image.");
        else {
            isValid = true;
        }
        return isValid;

    }

    public static boolean isValidResetPasswordForm(String password, String confirmPass) {
        boolean isValid = false;
        Context context = MyApplication.context;
        if (TextUtils.isEmpty(password))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_password));
        else if (TextUtils.isEmpty(confirmPass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_empty_confirm_password));
        else if (!password.equals(confirmPass))
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.error_passwords_unequal));
        else isValid = true;
        return isValid;
    }
}
