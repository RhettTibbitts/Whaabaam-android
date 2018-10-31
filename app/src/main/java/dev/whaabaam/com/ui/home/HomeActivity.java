package dev.whaabaam.com.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import com.quickblox.messages.services.SubscribeService;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivityHomeBinding;
import dev.whaabaam.com.quickblox.QuickbloxUtils;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.StartLocationAlert;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initBottomNav(binding.bottomNav);
        binding.bottomNav.setSelectedItemId(R.id.nav_home);
    }

    private void initBottomNav(BottomNavigationView bottomNav) {
        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            CommonUtils.switchBottomTabs(getSupportFragmentManager(), menuItem);
            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new StartLocationAlert(this);
        QuickbloxUtils.init();
        CommonUtils.requestLocationUpdateTask(this);


    }


}
