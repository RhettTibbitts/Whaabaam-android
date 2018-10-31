package dev.whaabaam.com.ui.binding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.content.QBContent;
import com.quickblox.core.Consts;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.core.exception.QBResponseException;

import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.CloseContactsData;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.filter.FilterBottomSheet;
import dev.whaabaam.com.utils.CommonUtils;
import fr.ganfra.materialspinner.MaterialSpinner;

import static dev.whaabaam.com.app.AppConstants.BASE_FILES_PATH;

public class Bindings {
    @BindingAdapter("bind:back_nav")
    public static void bindBackPress(final View view, final boolean shouldFinish) {
        view.setOnClickListener(view1 -> {
            ((BaseActivity) view1.getContext()).hideKeyboard();
            if (shouldFinish)
                ((BaseActivity) view1.getContext()).finish();
            else
                ((BaseActivity) view1.getContext()).onBackPressed();
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("bind:closeFilter")
    public static void bindCloseFilter(final TextView textView, final Context context) {
        textView.setOnTouchListener((view, event) -> {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (event.getRawX() >= textView.getRight() - textView.getTotalPaddingRight()) {
                    FilterBottomSheet.getInstance().dismiss();
                    return true;
                }
            }
            return false;
        });
    }

    @BindingAdapter("bind:astericPassword")
    public static void bindAstericPassword(TextInputEditText editText, String dummy) {
        CommonUtils.setAstericPasswordTransformation(editText);
    }

    @BindingAdapter("bind:drawable")
    public static void bindDrawable(ImageView imageView, Drawable drawable) {
        if (imageView.getVisibility() == View.VISIBLE)
            Glide.with(imageView.getContext()).load(drawable).into(imageView);
    }

    @BindingAdapter("bind:drawableId")
    public static void bindDrawableId(ImageView imageView, int drawableId) {
        Glide.with(imageView.getContext()).load(
                ContextCompat.getDrawable(imageView.getContext(), drawableId)).into(imageView);
    }

    @BindingAdapter("bind:ms_hint")
    public static void bindHint(MaterialSpinner spinner, String hint) {
        if (!TextUtils.isEmpty(hint)) {
            spinner.setHint(hint);
        }
    }

    @BindingAdapter("bind:image")
    public static void bindImage(CircleImageView imageView, String path) {
        if (path != null) {
            if (path.contains("http://"))
                Glide.with(imageView.getContext()).load(path).apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)).into(imageView);
            else
                Glide.with(imageView.getContext()).load(new File(path)).apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)).into(imageView);
        }
    }

    @BindingAdapter("bind:profile_image")
    public static void bindProfileImage(CircleImageView imageView, String path) {
        if (!TextUtils.isEmpty(path)) {
            if (path.contains("http://"))
                Glide.with(imageView.getContext()).load(path).apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)).into(imageView);
            else
                Glide.with(imageView.getContext()).load(new File(path)).apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(60, 60)
                        .placeholder(R.drawable.placeholder)).into(imageView);
        }
    }

    @BindingAdapter("bind:prettyTime")
    public static void bindTime(TextView textView, String time) {
        if (!TextUtils.isEmpty(time)) {
            PrettyTime prettyTime = new PrettyTime();
            try {
                textView.setText(prettyTime.format(CommonUtils.getDateFromString(time)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @BindingAdapter("bind:close_contacts")
    public static void bindCloseContactsOptions(ImageView imageView, String req_status) {
        if (!TextUtils.isEmpty(req_status)) {
            switch (req_status) {
                case "NEW_USER":
                    imageView.setImageResource(R.drawable.request_sent_top);
                    break;
                case "REQ_SENT":
                    imageView.setImageResource(R.drawable.unfriend);
                    break;
                case "FRIEND":
                    imageView.setImageResource(R.drawable.message);
                    break;
                case "REQ_RECEIVED":
                    imageView.setImageResource(R.drawable.req_received);
                    break;
            }
        }
    }

    @BindingAdapter("bind:secToDate")
    public static void bindSecToDate(TextView textView, long second) {
        if (second > 0) {
            Date d = new Date(second * 1000);
            String dt = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(d);
            textView.setText(dt);
        }
    }

    @BindingAdapter({"bind:chatAttachment", "bind:imageProgress"})
    public static void bindchatAttachment(ImageView imageView, QBChatMessage message, TextView imageProgress) {
        // check if image already ready

        if (message.getAttachments() != null && message.getAttachments().size() > 0) {
            String fileUID = message.getAttachments().iterator().next().getId();
            if (!CommonUtils.chatImageExist(fileUID)) {
                downloadImage(fileUID, imageProgress, imageView);
            } else {

                try {
                    File f = new File(CommonUtils.getChatFilePath(fileUID));
                    Glide.with(imageView.getContext()).load(f)
                            .into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                    downloadImage(fileUID, imageProgress, imageView);
                }

            }
        } else {
            imageView.setVisibility(View.GONE);
        }

    }

    @BindingAdapter("bind:profile_slider")
    public static void bindProfileSlider(CircleImageView imageView, String path) {
        if (!TextUtils.isEmpty(path))
            Glide.with(imageView.getContext()).load(path)
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(imageView);
    }

    public static void downloadImage(String fileUID, TextView imageProgress, ImageView imageView) {
        QBContent.downloadFile(fileUID, progress -> {
            imageView.setVisibility(View.VISIBLE);

            if (progress < 100)
                imageProgress.setText(progress + "%");
            else
                imageProgress.setVisibility(View.GONE);

        }, null).performAsync(new QBEntityCallback<InputStream>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onSuccess(InputStream inputStream, Bundle params) {
                CommonUtils.saveImageToStorage(imageView, inputStream, fileUID);
            }

            @Override
            public void onError(QBResponseException errors) {
                errors.printStackTrace();
            }
        });
    }

    @BindingAdapter("bind:enabled")
    public static void bindLocationDropdown(MaterialSpinner spinner, int position) {
//        if (position == -1) {
//            spinner.setEnabled(false);
//        } else {
//            spinner.setEnabled(true);
//        }
    }


}
