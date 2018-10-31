package dev.whaabaam.com.data.remote;

import com.google.gson.JsonObject;

import dev.whaabaam.com.app.AppConstants;

public interface ApiResponse {
    void onSuccess(JsonObject response, AppConstants.API_MODE apiMode);

    void onFailure(String message, AppConstants.API_MODE apiMode);
}
