package dev.whaabaam.com.ui.family;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivityAddFamilyBinding;
import dev.whaabaam.com.ui.base.BaseActivity;

public class AddFamilyActivity extends BaseActivity {
    ActivityAddFamilyBinding binding;
    AddFamilyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_family);
        model = new AddFamilyViewModel(this);
        model.initList(binding.allConnectionList);
    }
}
