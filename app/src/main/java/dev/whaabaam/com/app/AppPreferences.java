package dev.whaabaam.com.app;

import android.content.Context;
import android.content.SharedPreferences;

import static dev.whaabaam.com.app.MyApplication.APP_NAME;

public class AppPreferences {

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    private static AppPreferences instancePreferences;


    public class PREF_KEYS {
        public static final String USER_DATA = "user_data";
        public static final String TOKEN = "user_token";
        public static final String IS_FIRST_RUN = "first_run";
        public static final String IS_LOGGED_IN = "logged_in";
        public static final String FCM_TOKEN = "fcm_token";
        public static final String IS_REMEMBER_CHECKED = "is_remember_checked";
    }

    private AppPreferences(Context context) {

        instancePreferences = this;

        preferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);

        editor = preferences.edit();
    }

    public static AppPreferences getInstance(Context context) {
        if (instancePreferences == null)
            instancePreferences = new AppPreferences(context);

        return instancePreferences;
    }

    public boolean storeString(String key, String value) {

        editor.putString(key, value);

        return editor.commit();
    }

    public String getString(String key) {

        return preferences.getString(key, null);
    }

    public boolean storeBoolean(String key, boolean value) {

        editor.putBoolean(key, value);

        return editor.commit();
    }

    public boolean getBoolean(String key) {

        return preferences.getBoolean(key, false);
    }

    public boolean storeInt(String key, int value) {

        editor.putInt(key, value);

        return editor.commit();
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public boolean keyExists(String key) {
        return preferences.contains(key);
    }

    public boolean isFirstRun() {

        return getBoolean(PREF_KEYS.IS_FIRST_RUN);
    }
}
