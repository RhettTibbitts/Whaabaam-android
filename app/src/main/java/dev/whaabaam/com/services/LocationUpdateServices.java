package dev.whaabaam.com.services;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.app.MyApplication;
import io.reactivex.disposables.Disposable;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class LocationUpdateServices extends IntentService {


    public LocationUpdateServices() {

        super(LocationUpdateServices.class.getName());
    }

    public LocationUpdateServices(String name) {
        super(name);
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @SuppressLint({"MissingPermission", "CheckResult"})
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (AppPreferences.getInstance(getApplicationContext()).getBoolean(AppPreferences.PREF_KEYS.IS_LOGGED_IN)) {
            scheduleAlarm(true, getApplicationContext());
        } else {
            // stop the service if user is not logged in
            this.onDestroy();
        }
    }




    public static void scheduleAlarm(boolean isAdd, Context context) {
        // application running status
        Log.d("Whaabaam service", "Scheduled");
        Intent intentToFire = new Intent(context, WhaaBaamAlarmReceiver.class);
        intentToFire.setAction("dev.whaabaam.services.ACTION_ALARM");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,
                0, intentToFire, 0);
        AlarmManager alarmManager = (AlarmManager) context.
                getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        if (alarmManager != null) {
            if (isAdd) {
                c.add(Calendar.MINUTE, 4);
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), alarmIntent);
        }
    }

    public static void cancelAlarm(Context context) {
        // application running status
        Intent intentToFire = new Intent(context, WhaaBaamAlarmReceiver.class);
        intentToFire.setAction("dev.whaabaam.services.ACTION_ALARM");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,
                0, intentToFire, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarm != null) {
            alarm.cancel(alarmIntent);
        }
    }

}
