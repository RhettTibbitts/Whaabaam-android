package dev.whaabaam.com.utils;
/*
 * Created by RahulGupta on 20/8/18
 */

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class StartLocationAlert implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Activity context;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private GoogleApiClient googleApiClient;

    public StartLocationAlert(Activity context) {
        this.context = context;
        googleApiClient = getInstance();
        if (googleApiClient != null) {
            //googleApiClient.connect();
            settingsrequest();
            googleApiClient.connect();
        }
    }

    public GoogleApiClient getInstance() {
        return new GoogleApiClient.Builder(context).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    private void settingsrequest() {
//        Log.e("settingsrequest", "Comes");


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            final LocationSettingsStates state = result1.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    // Log.e("Application","Button Clicked");
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
                    // Log.e("Application","Button Clicked1");
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                        Log.e("Applicationsett", e.toString());
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                    //Log.e("Application","Button Clicked2");
                    Log.d("apiclient", "Location enabled");
                    break;
            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {


    /*    MainActivity mm = new MainActivity();
        mm.requestLocationUpdates();*/
        Log.d("apiclient", "connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
