package dev.whaabaam.com.ui.permission;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.signinsignupchooser.SignInSignUpChooserActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

public class PermissionViewModel extends BaseObservable {

    public final ObservableBoolean allGranted = new ObservableBoolean(false);

    private Context context;
    private RxPermissions rxPermissions;

    public PermissionViewModel(Context context) {
        this.context = context;
        rxPermissions = new RxPermissions((AppCompatActivity) context);
    }

    public View.OnClickListener onProceedClick() {
        return v -> {
            CommonUtils.startNewTaskInstance(context, SignInSignUpChooserActivity.class);
            ((BaseActivity) context).finish();
        };
    }

    public View.OnClickListener onGrantPermissionClick() {
        return v -> {
            rxPermissions.requestEachCombined(AppConstants.ALL_PERMISSIONS)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            allGranted.set(true);
                        } else {
                            AppDialog.getInstance(context).showGoToAppSettingsDialog(context);
                        }
                    });
        };
    }
}
