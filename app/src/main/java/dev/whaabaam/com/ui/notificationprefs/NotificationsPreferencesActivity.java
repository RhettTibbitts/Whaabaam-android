package dev.whaabaam.com.ui.notificationprefs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.databinding.ActivityNotificationsPreferencesBinding;
import dev.whaabaam.com.ui.base.BaseActivity;

public class NotificationsPreferencesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotificationsPreferencesBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_notifications_preferences);
        NotificationPrefsViewModel viewModel = new NotificationPrefsViewModel(this);
        viewModel.isFirstRun = getIntent().getExtras() != null &&
                Objects.requireNonNull(getIntent().getExtras()).getBoolean(AppPreferences.PREF_KEYS.IS_FIRST_RUN, false);

        binding.setViewModel(viewModel);
        viewModel.initPrefsList(binding.preferencesItemList);

    }
}
