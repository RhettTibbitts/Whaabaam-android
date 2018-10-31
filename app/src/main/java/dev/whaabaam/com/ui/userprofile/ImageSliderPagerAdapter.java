package dev.whaabaam.com.ui.userprofile;
/*
 * Created by RahulGupta on 11/9/18
 */

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.databinding.ItemOtherProfileSliderImageBinding;

public class ImageSliderPagerAdapter extends PagerAdapter {
    private ArrayList<UserModel.Images> list;

    public ImageSliderPagerAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ItemOtherProfileSliderImageBinding binding = ItemOtherProfileSliderImageBinding.inflate(
                LayoutInflater.from(container.getContext()), container, false);
        binding.setPath(list.get(position).getUserImages().getThumb());
        container.addView(binding.getRoot(), 0);
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void addImages(ArrayList<UserModel.Images> arrayList) {
        list.addAll(arrayList);
        notifyDataSetChanged();
    }
}
