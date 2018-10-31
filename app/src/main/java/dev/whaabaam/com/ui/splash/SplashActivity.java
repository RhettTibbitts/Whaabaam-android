package dev.whaabaam.com.ui.splash;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import com.tbruyelle.rxpermissions2.RxPermissions;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.home.HomeActivity;
import dev.whaabaam.com.ui.permission.PermissionActivity;
import dev.whaabaam.com.ui.signinsignupchooser.SignInSignUpChooserActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;

public class SplashActivity extends BaseActivity {

    RxPermissions rxPermissions;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        rxPermissions = new RxPermissions(this);


//        rxPermissions.requestEachCombined(AppConstants.GPS_PERMISSIONS)
//                .subscribe(permission -> {
//                    if (permission.granted) {
        // `permission.name` is granted !

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                new MyThread().run();

            }
        }, 2000);


//                    } else if (permission.shouldShowRequestPermissionRationale) {
//                        // Denied permission without ask never again
//                        CommonUtils.toast(this, "Cannot access location without your permission!");
//                    } else {
//                        // Denied permission with ask never again
//                        // Need to go to the settings
//                        AppDialog.getInstance(this).showGoToAppSettingsDialog(this);
//                    }
//                });
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            boolean res = CommonUtils.hasPermissions(SplashActivity.this, AppConstants.ALL_PERMISSIONS);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!res) {
                        launchIntent(PermissionActivity.class, null, true);

                    } else {

                        if (MyApplication.isLoggedIn) {
                            launchIntent(HomeActivity.class, null, true);
                        } else {
                            launchIntent(SignInSignUpChooserActivity.class, null, true);
                        }
                    }

                }
            });

        }
    }
}
