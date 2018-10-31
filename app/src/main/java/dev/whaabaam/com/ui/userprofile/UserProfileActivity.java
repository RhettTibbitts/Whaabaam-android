package dev.whaabaam.com.ui.userprofile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import java.util.Objects;

import dev.whaabaam.com.BR;
import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivityUserProfileBinding;
import dev.whaabaam.com.ui.base.BaseActivity;

/**
 * @author rahul
 */
public class UserProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        UserProfileViewModel viewModel = new UserProfileViewModel(this,
                Objects.requireNonNull(getIntent().getExtras()).getString("other_user_id"));
        binding.setViewModel(viewModel);
        binding.setVariable(BR.model, viewModel.otherUserData);
        viewModel.initHorizontalImageSlider(binding.userProfileImageSlider);
        viewModel.initUserInfoList(findViewById(R.id.userDetailsList));
        viewModel.initUserInfoList1(findViewById(R.id.userDetailsList1));
        viewModel.initMutualContactsSlider(findViewById(R.id.mutualFriendList));
        viewModel.initfamilyMemberSlider(findViewById(R.id.userFamilyMembersList));
        viewModel.requestOtherUserProfileApi();
    }
}
