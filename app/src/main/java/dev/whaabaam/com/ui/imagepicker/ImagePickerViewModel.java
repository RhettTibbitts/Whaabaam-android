package dev.whaabaam.com.ui.imagepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.michaelflisar.rxbus2.RxBus;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.FileUtils;

public class ImagePickerViewModel extends BaseObservable {
    private RxPermissions rxPermissions;
    private Context context;
    private OnDialogOptionSelected callback;
    private AppConstants.IMAGE_PICKER_SOURCE pickerSource;

    public ImagePickerViewModel(Context context, OnDialogOptionSelected callback, AppConstants.IMAGE_PICKER_SOURCE source) {
        this.context = context;
        rxPermissions = new RxPermissions((AppCompatActivity) context);
        this.callback = callback;
        pickerSource = source;
    }

    public View.OnClickListener onCameraClick() {
        return view -> {
            doProcess(1);

        };
    }

    public View.OnClickListener onGalleryClick() {
        return view -> {
            doProcess(0);
        };
    }

    public View.OnClickListener onRemoveClick(Context context) {
        return view -> {
            callback.dismissDialog();
            RxBus.get().withKey("remove_profile_image").send("");
        };
    }

    @SuppressLint("CheckResult")
    private void doProcess(int value) {
        callback.dismissDialog();

        rxPermissions.requestEachCombined(AppConstants.STORAGE_PERMISSIONS)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // `permission.name` is granted !
                        if (value == 0)
                            launchGalleryIntent();
                        else
                            launchCameraIntent();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // Denied permission without ask never again
                        CommonUtils.toast(context, "Cannot access Photos without your permission!");
                    } else {
                        // Denied permission with ask never again
                        // Need to go to the settings
                        AppDialog.getInstance(context).showGoToAppSettingsDialog(context);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void launchCameraIntent() {
        RxImagePicker.with(((AppCompatActivity) context).getFragmentManager()).requestImage(Sources.CAMERA).subscribe(uri -> {
            sendImagePathBroadcast(1, FileUtils.getPath(context, uri));
        });
    }

    @SuppressLint("CheckResult")
    private void launchGalleryIntent() {
        RxImagePicker.with(((AppCompatActivity) context).getFragmentManager()).requestImage(Sources.GALLERY).subscribe(uri -> {
            sendImagePathBroadcast(0, FileUtils.getPath(context, uri));
        });
    }

    private void sendImagePathBroadcast(int source, String path) {
        Intent imageIntent = new Intent(AppConstants.IMAGE_PATH_INTENT);
        imageIntent.putExtra(AppConstants.IMAGE_PATH, path);
        imageIntent.putExtra(AppConstants.IMAGE_SOURCE, source);

        switch (pickerSource) {
            case PROFILE_IMAGE:
                RxBus.get().withKey(AppConstants.ACTION_PICK_PROFILE_IMAGE).send(path);
//                imageIntent.putExtra("action", AppConstants.ACTION_PICK_PROFILE_IMAGE);
                break;
            case OTHER_PROFILE_IMAGES:
                RxBus.get().withKey(AppConstants.ACTION_PICK_EXTRA_PROFILE_IMAGE).send(path);
//                imageIntent.putExtra("action", AppConstants.ACTION_PICK_EXTRA_PROFILE_IMAGE);
                break;
            case CHAT_ATTACHMENT:
                RxBus.get().send(path);
                break;
            default:

                break;
        }
//        LocalBroadcastManager.getInstance(context).sendBroadcast(imageIntent);
    }

    interface OnDialogOptionSelected {
        void dismissDialog();
    }
}
