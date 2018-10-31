package dev.whaabaam.com.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.StoringMechanism;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBNotificationChannel;
import com.quickblox.messages.services.SubscribeService;

import dev.whaabaam.com.BuildConfig;
import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import dev.whaabaam.com.data.model.other.FilterData;
import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.services.LocationUpdateServices;
import dev.whaabaam.com.utils.CommonUtils;
import okhttp3.OkHttpClient;

/**
 * @author rahul
 */
public class MyApplication extends Application {

    public static String TOKEN = "";
    public static UserModel userModel = null;
    private static MyApplication myApplication;
    public static final String APP_NAME = "Whaabaam";
    public static boolean isLoggedIn = false;


    private static final String APP_ID = BuildConfig.WHAABAAM_APP_ID;
    private static final String AUTH_KEY = BuildConfig.WHAABAAM_AUTH_KEY;
    private static final String AUTH_SECRET = BuildConfig.WHAABAAM_AUTH_SECRET;
    private static final String ACCOUNT_KEY = BuildConfig.WHAABAAM_ACCOUNT_KEY;


    public static boolean isOnChatScreen = false;
    public static Context context;
    public static ArrayList<FilterData> filterPreferences = new ArrayList<>();

    public static MyApplication getInstance() {
        if (myApplication == null)
            myApplication = new MyApplication();

        return myApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        isLoggedIn = CommonUtils.getBooleanFromPrefs(getApplicationContext(), AppPreferences.PREF_KEYS.IS_LOGGED_IN);

        FirebaseApp.initializeApp(this);
        generateFcmToken();
        try {
            initUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initAndroidNetworking();
//        initSampleConfigs();
        initQuickBlox();
    }


    private void initQuickBlox() {
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setStoringMehanism(StoringMechanism.UNSECURED);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }

    public void generateFcmToken() {
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
//            Log.e("fcm_token", instanceIdResult.getToken());
        String fcmToken = AppPreferences.getInstance(getApplicationContext()).getString(AppPreferences.PREF_KEYS.FCM_TOKEN);
        if (TextUtils.isEmpty(fcmToken)) {
            fcmToken = FirebaseInstanceId.getInstance().getToken();
            AppPreferences.getInstance(getApplicationContext())
                    .storeString(AppPreferences.PREF_KEYS.FCM_TOKEN, fcmToken);
        }


//        });
    }

    private void initUserData() throws Exception {

        setUpFirstRun();
        /*
         * if user is logged in, load it's data to app
         * */
        if (isLoggedIn) {
            userModel = new UserModel();
            // check if user data exists, the load to global variable
            if (AppPreferences.getInstance(getApplicationContext()).keyExists(AppPreferences.PREF_KEYS.USER_DATA)) {
                String data = CommonUtils.getStringFromPrefs(getApplicationContext(), AppPreferences.PREF_KEYS.USER_DATA);
                if (!TextUtils.isEmpty(data)) {
                    userModel = new Gson().fromJson(data, UserModel.class);
                    TOKEN = CommonUtils.getStringFromPrefs(getApplicationContext(), AppPreferences.PREF_KEYS.TOKEN);
                    Log.e("token", TOKEN);
                }
            }
        }
        if (!CommonUtils.isServiceRunning(getApplicationContext(), LocationUpdateServices.class)) {
            CommonUtils.requestLocationUpdateTask(getApplicationContext());
        }

    }

    private void setUpFirstRun() {
        if (!AppPreferences.getInstance(context).keyExists(AppPreferences.PREF_KEYS.IS_FIRST_RUN)) {
            AppPreferences.getInstance(context).storeBoolean(AppPreferences.PREF_KEYS.IS_FIRST_RUN, true);
        } else {
            AppPreferences.getInstance(context).storeBoolean(AppPreferences.PREF_KEYS.IS_FIRST_RUN, false);
        }
    }

    private void initAndroidNetworking() {


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
//        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
    }

    /*
     * reset user token and profile data saved to preferences
     * */
    public void resetUserData(Context context) {
        TOKEN = "";
        CommonUtils.storeStringInPrefs(context, AppPreferences.PREF_KEYS.TOKEN, "");
        CommonUtils.storeBooleanInPrefs(context, AppPreferences.PREF_KEYS.IS_LOGGED_IN, false);
        CommonUtils.storeStringInPrefs(context, AppPreferences.PREF_KEYS.USER_DATA, null);
    }


}
