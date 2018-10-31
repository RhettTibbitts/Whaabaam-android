package dev.whaabaam.com.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import dev.whaabaam.com.app.AppPreferences;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        AppPreferences.getInstance(getApplicationContext())
                .storeString(AppPreferences.PREF_KEYS.FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());
    }
}
