package dev.whaabaam.com.ui.family;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivityFamilyMemberListBinding;
import dev.whaabaam.com.ui.base.BaseActivity;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class FamilyMemberList extends BaseActivity {

    private FamilyListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFamilyMemberListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_family_member_list);
        viewModel = new FamilyListViewModel(this, userModel.getId());
        viewModel.initFamilyList(binding.familyList);
        binding.setFamilyListViewModel(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.requestFamilyApi(false);
    }
}
