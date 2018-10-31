package dev.whaabaam.com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.utils.CommonUtils;

public class DeviceStartUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (AppPreferences.getInstance(context).getBoolean(AppPreferences.PREF_KEYS.IS_LOGGED_IN)) {
            CommonUtils.requestLocationUpdateTask(context);
        }
    }
}
