package dev.whaabaam.com.ui.myprofile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.databinding.ItemImageSliderBinding;

public class ImageSliderAdapter extends PagerAdapter {
    private ArrayList<UserModel.Images> imagesList;

    ImageSliderAdapter(ArrayList<UserModel.Images> imagesList) {
        this.imagesList = imagesList;
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ItemImageSliderBinding binding = ItemImageSliderBinding.inflate(LayoutInflater
                .from(container.getContext()), container, false);

        loadImageAndHandleProgress(container.getContext(), position, binding.itemImage, binding.itemImageProgress);
        return binding.getRoot();
    }

    private void loadImageAndHandleProgress(Context context, int position, ImageView itemImage, ProgressBar itemImageProgress) {
        Glide.with(context).load(imagesList.get(position).getUserImages().getThumb()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                           DataSource dataSource, boolean isFirstResource) {
                ((AppCompatActivity) context).supportPostponeEnterTransition();
                itemImageProgress.setVisibility(View.GONE);
                return false;
            }
        }).into(itemImage);

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void addImagesList(ArrayList<UserModel.Images> list) {
        imagesList.addAll(list);
        notifyDataSetChanged();
    }
}
