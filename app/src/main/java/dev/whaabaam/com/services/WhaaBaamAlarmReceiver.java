package dev.whaabaam.com.services;
/*
 * Created by RahulGupta on 12/9/18
 */

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.text.TextUtils;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.data.remote.ApiEndPoint;

import static dev.whaabaam.com.app.MyApplication.userModel;
import static dev.whaabaam.com.services.LocationUpdateServices.scheduleAlarm;

/**
 * @author rahul
 */
public class WhaaBaamAlarmReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "dev.whaabaam.services.ACTION_ALARM";

    private RxLocation rxLocation;
    private LocationRequest locationRequest;
    private LocationManager locationManager;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent != null && intent.getAction() != null && intent.getAction().equalsIgnoreCase(ACTION_ALARM)) {

            if (AppPreferences.getInstance(context).getBoolean(AppPreferences.PREF_KEYS.IS_LOGGED_IN)) {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                if (Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    requestLocationGPSLocation(context);
                }
            }
        }
    }

    @SuppressLint({"MissingPermission", "CheckResult"})
    private void requestLocationGPSLocation(Context context) {
        try {
            rxLocation = new RxLocation(context);
            locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            rxLocation.location().updates(locationRequest)
                    .flatMap(location -> rxLocation.geocoding().fromLocation(location).toObservable())
                    .subscribe(address -> {
                        updateLocation(address.getLatitude(), address.getLongitude(), getViableAddress(address));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getViableAddress(Address address) {
        return address.getAddressLine(0);
    }

    private void updateLocation(double latitude, double longitude, String address) {
        JSONObject object = new JSONObject();
        try {
            object.put(AppKeys.USER_ID, userModel.getId());
            object.put(AppKeys.LATITUDE, latitude);
            object.put(AppKeys.LONGITUDE, longitude);
            object.put(AppKeys.ADDRESS, address);
            requestLocationUpdateApi(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void requestLocationUpdateApi(JSONObject request) {

        AndroidNetworking.post(ApiEndPoint.BASE_URL + "/location/add")
                .addJSONObjectBody(request)
                .addHeaders(AppKeys.TOKEN, MyApplication.TOKEN)
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                LocationUpdateServices.cancelAlarm(context);
                scheduleAlarm(true, context);


            }

            @Override
            public void onError(ANError anError) {
                LocationUpdateServices.cancelAlarm(context);
                scheduleAlarm(true, context);
            }
        });
    }

//    private String getAddress(Location location) {
//        String address = "N/A";
//        try {
//            List<Address> addresses = new Geocoder(context)
//                    .getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            if (!addresses.isEmpty())
//                address = getViableAddress(addresses.get(0));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return address;
//    }


}
