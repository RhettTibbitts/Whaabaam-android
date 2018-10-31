package dev.whaabaam.com.ui.signinsignupchooser;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import dev.whaabaam.com.ui.signin.SignInActivity;
import dev.whaabaam.com.ui.signup.SignUpActivity;

public class SignInSignUpChooserViewModel extends BaseObservable {


    SignInSignUpChooserViewModel(Activity context) {

    }

    /* BEGIN
     * @click: Sign up: navigate to sign up screen
     * */
    public View.OnClickListener onSignUpClick(final Context context) {
        return view -> ((SignInSignUpChooserActivity) context)
                .launchIntent(SignUpActivity.class, null, false);
    }
    //-----------------------END-------------------------


    /* BEGIN
     * @click: Sign in: navigate to sign in screen
     * */
    public View.OnClickListener onSignInClick(final Context context) {
        return view -> ((SignInSignUpChooserActivity) context)
                .launchIntent(SignInActivity.class, null, true);
    }
    //-----------------------END-------------------------


}
