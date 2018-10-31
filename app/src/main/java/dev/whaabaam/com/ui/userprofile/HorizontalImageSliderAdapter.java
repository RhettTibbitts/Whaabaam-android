package dev.whaabaam.com.ui.userprofile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.databinding.ItemOtherProfileSliderImageBinding;

/*
 * Created by RahulGupta on 11/9/18
 */

/**
 * @author rahul
 */
public class HorizontalImageSliderAdapter extends RecyclerView.Adapter<HorizontalImageSliderAdapter.ImageVH> {

    private ArrayList<UserModel.Images> imageList;
    private OnImageSelectedCallBack callBack;

    HorizontalImageSliderAdapter(OnImageSelectedCallBack callBack) {
        imageList = new ArrayList<>();
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ImageVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageVH(ItemOtherProfileSliderImageBinding.inflate(
                LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVH imageVH, int i) {
        UserModel.Images images = imageList.get(imageVH.getAdapterPosition());
        imageVH.binding.setPath(images.getUserImages().getThumb());
        imageVH.binding.itemView.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(images.getUserImages().getOrg())) {
                callBack.onImageSelected(images, imageVH.binding.imageView, imageVH.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    void addImages(ArrayList<UserModel.Images> arrayList) {
        for (UserModel.Images img : arrayList) {
            imageList.add(img);
            notifyItemInserted(imageList.size());
        }
    }

    public void add(UserModel.Images image) {
        imageList.add(image);
        notifyItemInserted(imageList.size() - 1);
    }

    class ImageVH extends RecyclerView.ViewHolder {
        ItemOtherProfileSliderImageBinding binding;

        ImageVH(@NonNull ItemOtherProfileSliderImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnImageSelectedCallBack {
        void onImageSelected(UserModel.Images imageData, ImageView image, int position);
    }
}
