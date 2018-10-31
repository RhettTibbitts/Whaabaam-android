package dev.whaabaam.com.ui.myprofile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.databinding.ActivityImageSliderBinding;

public class ImageSliderActivity extends AppCompatActivity {
    private int currentPos = 0;
    private ArrayList<UserModel.Images> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImageSliderBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image_slider);
        getIntentData();
        initImageSlider(binding.imageSliderViewPager);
    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            currentPos = getIntent().getExtras().getInt(AppKeys.CURRENT_POSITION);
            list = getIntent().getExtras().getParcelableArrayList(AppKeys.IMAGE_ARRAY_LIST);
        }
    }

    private void initImageSlider(ViewPager imageSliderViewPager) {
        ImageSliderAdapter adapter = new ImageSliderAdapter(list);
        imageSliderViewPager.setAdapter(adapter);
        imageSliderViewPager.setCurrentItem(currentPos);
    }
}
