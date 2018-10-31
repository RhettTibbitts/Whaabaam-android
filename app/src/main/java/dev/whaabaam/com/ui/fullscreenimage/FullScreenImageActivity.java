package dev.whaabaam.com.ui.fullscreenimage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.databinding.ActivityFullScreenImageBinding;

/**
 * @author rahul
 */
public class FullScreenImageActivity extends AppCompatActivity {

    private ActivityFullScreenImageBinding binding;

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, FullScreenImageActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_screen_image);
        supportPostponeEnterTransition();
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            loadImage(bundle.getString(AppConstants.IMAGE_PATH), binding.imageViewFull);
        }
    }

    private void loadImage(String imagePath, ImageView imageView) {
        Glide.with(this)
                .load(imagePath)
                .apply(new RequestOptions()
                        .dontAnimate())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Toast.makeText(FullScreenImageActivity.this, "Some error occurred in loading image. Please try again.", Toast.LENGTH_SHORT).show();
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        binding.progress.setVisibility(View.GONE);
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                }).into(imageView);
    }

    @Override
    public void onBackPressed() {
        finish();
        supportFinishAfterTransition();
    }
}
