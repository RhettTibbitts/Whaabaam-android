package dev.whaabaam.com.ui.myprofile;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.databinding.ActivityMyProfileBinding;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.BR;
import dev.whaabaam.com.utils.FileUtils;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class MyProfileActivity extends BaseActivity {

    private ActivityMyProfileBinding binding;
    private MyProfileActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        viewModel = new MyProfileActivityViewModel(this);
        viewModel.isFirstRun = Objects.requireNonNull(getIntent().getExtras())
                .getBoolean(AppPreferences.PREF_KEYS.IS_FIRST_RUN,
                        false);
        binding.setIsFirstRun(viewModel.isFirstRun);
        viewModel.initProfileImageList(binding.profileImagesList);
        viewModel.isApiSuccessful.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.init();
                binding.setViewModel(viewModel);
                populateAllDropdowns();
            }
        });
    }

    private void populateAllDropdowns() {

        new Handler().postDelayed(() -> {
            viewModel.initMilitary(binding.military);
            viewModel.initPoliticalSpinner(binding.political);
            viewModel.initReligionSpinner(binding.religion);
            viewModel.initRelationships(binding.relationship);
            viewModel.initState(binding.materialSpinner2);
            viewModel.initCity(binding.materialSpinner);
            viewModel.initFromState(binding.fromLocationState);
            viewModel.initFromCity(binding.fromLocationCity);
            viewModel.notifyChange();
        }, 1000);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 789 && resultCode == RESULT_OK && data != null) {
            viewModel.resumeFile = FileUtils.getFile(this, data.getData());
        }

    }
}
