package dev.whaabaam.com.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.DialogAcceptRejectBinding;
import dev.whaabaam.com.databinding.DialogConnectBinding;

/**
 * @author rahul
 */
public class AppDialog {
    private static AppDialog appDialog;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    private AppDialog(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public static AppDialog getInstance(Context context) {
        if (appDialog == null)
            appDialog = new AppDialog(context);

        return appDialog;
    }

    private void show(int dialogLayoutID, OnDialogOptionsClickCallback clickCallback) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, dialogLayoutID, null, false);
        builder.setView(binding.getRoot());
    }

    public void showConnectionRequestDialog(Context context, String userName, final OnDialogOptionsClickCallback clickCallback) {
        if (!((AppCompatActivity) context).isFinishing()) {
            builder = new AlertDialog.Builder(context);
            DialogConnectBinding binding = DialogConnectBinding.inflate(inflater, null);
            binding.textView30.setText(CommonUtils.getConnectionRequestText(userName));
            builder.setView(binding.getRoot());
            builder.setCancelable(false);
            final AlertDialog dialog = builder.create();
            binding.button7.setOnClickListener(view -> {
                dialog.dismiss();
                clickCallback.onPositiveClick(true);
            });
            binding.button8.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        }
    }

    public void showCancelConnectionRequestDialog(Context context, String userName, final OnDialogOptionsClickCallback clickCallback) {
        if (!((AppCompatActivity) context).isFinishing()) {
            builder = new AlertDialog.Builder(context);
            DialogConnectBinding binding = DialogConnectBinding.inflate(inflater, null);
            binding.textView30.setText(CommonUtils.getConnectionCancelText(userName));
            builder.setView(binding.getRoot());
            builder.setCancelable(false);
            final AlertDialog dialog = builder.create();
            binding.button7.setOnClickListener(view -> {
                dialog.dismiss();
                clickCallback.onPositiveClick(true);
            });
            binding.button8.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        }
    }

    public void showAcceptRejectDialog(Context context, String userName, final OnDialogOptionsClickCallback clickCallback) {
        if (!((AppCompatActivity) context).isFinishing()) {
            builder = new AlertDialog.Builder(context);
            DialogAcceptRejectBinding binding = DialogAcceptRejectBinding.inflate(inflater, null);
            binding.textView30.setText(CommonUtils.getAcceptRejectText(userName));
            builder.setView(binding.getRoot());
            final AlertDialog dialog = builder.create();
            binding.button7.setOnClickListener(view -> {
                dialog.dismiss();
                clickCallback.onPositiveClick(true);
            });
            binding.button8.setOnClickListener(view -> {
                dialog.dismiss();
                clickCallback.onPositiveClick(false);
            });
            binding.getRoot().findViewById(R.id.btnClose).setOnClickListener(view -> {
                dialog.dismiss();
            });
            dialog.show();
        }
    }

    public void showRemoveConnectionDialog(Context context, String userName, final OnDialogOptionsClickCallback clickCallback) {
        if (!((AppCompatActivity) context).isFinishing()) {
            builder = new AlertDialog.Builder(context);
            DialogConnectBinding binding = DialogConnectBinding.inflate(inflater, null);
            binding.textView30.setText(CommonUtils.getConnectionRemoveText(userName));
            builder.setView(binding.getRoot());
            builder.setCancelable(false);
            final AlertDialog dialog = builder.create();
            binding.button7.setOnClickListener(view -> {
                dialog.dismiss();
                clickCallback.onPositiveClick(true);
            });
            binding.button8.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        }
    }

    public void showMessageDialogWithTitleToDismiss(Context context, String message, String title) {
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNeutralButton("OKAY", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public void showMessageDialogToDismiss(Context context, String message) {
        if (!((AppCompatActivity) context).isFinishing()) {
            if (!((AppCompatActivity) context).isFinishing()) {
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Message");
                builder.setMessage(message);
                builder.setCancelable(false);
                builder.setNeutralButton("OKAY", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.create().show();
            }
        }
    }

    public void showMessageDialogWithAction(Context context, String message, final OnDialogOptionsClickCallback clickCallback) {
        if (!((AppCompatActivity) context).isFinishing()) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("Message");
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setNeutralButton("OKAY", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                clickCallback.onPositiveClick(true);
            });
            builder.create().show();
        }
    }

    public void showTwoButtonDialog(Context context, String message, String positiveBtnText,
                                    String negativeBtnText, final OnDialogOptionsClickCallback clickCallback) {
        if (!((AppCompatActivity) context).isFinishing()) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("Message");
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setPositiveButton(positiveBtnText, (dialogInterface, i) -> {
                dialogInterface.dismiss();
                clickCallback.onPositiveClick(true);
            });
            builder.setNegativeButton(negativeBtnText, (dialogInterface, i) -> {
                dialogInterface.dismiss();
//            clickCallback.onPositiveClick(false);
            });
            builder.create().show();
        }
    }

    public void showGoToAppSettingsDialog(Context context) {
        if (!((AppCompatActivity) context).isFinishing()) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("Message");
            builder.setCancelable(false);
            builder.setMessage("You have set not to ask for permissions during your previous interactions. Please allow us permission from the app settings.");
            builder.setPositiveButton("Take me to Settings", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                launchAppSettings(context);

            });
            builder.setNegativeButton("Dismiss", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.create().show();
        }
    }

    private void launchAppSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

}
