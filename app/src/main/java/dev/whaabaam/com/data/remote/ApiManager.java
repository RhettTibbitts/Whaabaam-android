package dev.whaabaam.com.data.remote;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.ui.signin.SignInActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.TOKEN;

public class ApiManager implements JSONObjectRequestListener {

    private static ApiManager apiManager;
    private ApiResponse apiResponse;
    private ProgressDialog progressDialog;
    private Context context;
    private JsonParser jsonParser;
    private AppConstants.API_MODE apiMode;


    private ApiManager() {
        jsonParser = new JsonParser();
    }

    public static ApiManager getInstance() {
//        ApiManager.context = context;
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Processing...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    public void requestApi(Context context, JSONObject jsonObject, AppConstants.API_MODE apiMode
            , ApiResponse apiResponse, boolean showDialog) {
        this.context = context;
        initProgressDialog();
        this.apiResponse = apiResponse;
        this.apiMode = apiMode;

        if (CommonUtils.isOnline(context)) {
            if (showDialog) progressDialog.show();

            Log.d("API_LOG_REQUEST_BODY", jsonObject.toString());

            switch (apiMode) {
                case LOGIN:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_LOGIN)
                            .getAsJSONObject(this);
                    break;
                case REGISTER:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_REGISTER)
                            .getAsJSONObject(this);
                    break;
                case MY_PROFILE:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_MY_PROFILE)
                            .getAsJSONObject(this);
                    break;
                case LOGOUT:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_LOGOUT)
                            .getAsJSONObject(this);
                    break;
                case CHANGE_PASSWORD:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_CHANGE_PASSWORD)
                            .getAsJSONObject(this);
                    break;
                case FORGOT_PASSWORD_REQUEST:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_FORGOT_PASSWORD)
                            .getAsJSONObject(this);
                    break;
                case FORGOT_PASSWORD_VERIFY_OTP:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_VERIFY_OTP)
                            .getAsJSONObject(this);
                    break;
                case RESET_PASSWORD:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_RESET_PASSWORD)
                            .getAsJSONObject(this);
                    break;
                case DELETE_PROFILE_PIC:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_DELETE_PROFILE)
                            .getAsJSONObject(this);
                    break;
                case DELETE_OTHER_PIC:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_DELETE_OTHER_PIC)
                            .getAsJSONObject(this);
                    break;
                case NOTIFICATION_PREFS:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_GET_NOTIF_PREFS)
                            .getAsJSONObject(this);
                    break;
                case SAVE_NOTIFICATION_PREFS:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_SAVE_NOTIF_PREFS)
                            .getAsJSONObject(this);
                    break;
                case MY_CONNECTIONS:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_MY_CONNECTIONS)
                            .getAsJSONObject(this);
                    break;
                case GET_NOTIFICATIONS:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_NOTIFICATIONS)
                            .getAsJSONObject(this);
                    break;
                case GET_CAPTURED_USERS:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_CLOSE_CONTACTS)
                            .getAsJSONObject(this);
                    break;
                case FAMILY_LIST:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_FAMILY_LIST)
                            .getAsJSONObject(this);
                    break;
                case REMOVE_FAMILY_MEMBER:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_REMOVE_FAMILY)
                            .getAsJSONObject(this);
                    break;
                case ADD_FAMILY_MEMBER:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_ADD_FAMILY)
                            .getAsJSONObject(this);
                    break;
                case CONNECTION_LIST_FOR_FAMILY:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_CONNECTION_LIST_FOR_FAMILY)
                            .getAsJSONObject(this);
                    break;
                case REQUEST_RELATION_OPTIONS:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_RELATION_LIST)
                            .getAsJSONObject(this);
                    break;
                case OTHER_PROFILE_DATA:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_VIEW_OTHER_PROFILE)
                            .getAsJSONObject(this);
                    break;
                case VIEW_NOTES:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_VIEW_NOTES)
                            .getAsJSONObject(this);
                    break;
                case ADD_NOTES:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_ADD_NOTE)
                            .getAsJSONObject(this);
                    break;
                case SEND_CONTACT_REQUEST:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_SEND_CONNECTION_REQUEST)
                            .getAsJSONObject(this);
                    break;
                case WITHDRAW_CONTACT_REQUEST:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_WITHDRAW_CONNECTION_REQ)
                            .getAsJSONObject(this);
                    break;
                case RESPOND_TO_CONTACT_REQUEST:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_RESPOND_CONNECTION_REQUEST)
                            .getAsJSONObject(this);
                    break;
                case REMOVE_CONNECTION:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_REMOVE_CONNECTION)
                            .getAsJSONObject(this);
                    break;
                case GET_CITIES:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_GET_CITIES)
                            .getAsJSONObject(this);
                    break;
                case CHECK_IN_CONNECTION:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_CHECK_IN_CONNECTION)
                            .getAsJSONObject(this);
                    break;
                case DELETE_RESUME:
                    getRequestBody(jsonObject, ApiEndPoint.ENDPOINT_DELETE_RESUME)
                            .getAsJSONObject(this);
                    break;
                case REPORT_USER:
                    getRequestBody(jsonObject, ApiEndPoint.END_POINT_REPORT_USER)
                            .getAsJSONObject(this);
                    break;
                case SEARCH:
                    getRequestBody(jsonObject, ApiEndPoint.END_POINT_SEARCH)
                            .getAsJSONObject(this);
                    break;
                default:
                    break;


            }
        } else
            apiResponse.onFailure(context.getString(R.string.no_internet), apiMode);


    }

    private ANRequest getRequestBody(JSONObject request, String url) {
        return AndroidNetworking.post(url)
                .addJSONObjectBody(request)
                .addHeaders("token", TOKEN)
                .build();
    }


    @Override
    public void onResponse(JSONObject response) {
        Log.d("API_LOG_RESPONSE", response.toString());
        dismissDialog();
        if (response != null) {
            try {
                switch (response.getInt(AppKeys.STATUS)) {
                    case AppKeys.SUCCESS:
                        apiResponse.onSuccess(getJson(response), apiMode);
                        break;
                    case AppKeys.SESSION_ERROR:
                        CommonUtils.storeBooleanInPrefs(context, AppPreferences.PREF_KEYS.IS_LOGGED_IN, false);
                        AppDialog.getInstance(context).showMessageDialogWithAction(context,
                                response.getString(AppKeys.MESSAGE),
                                (value) -> CommonUtils.startNewTaskInstance(context, SignInActivity.class));
                        break;
                    default:
                        apiResponse.onFailure(response.getString(AppKeys.MESSAGE), apiMode);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(ANError anError) {
        Log.d("API_LOG_RESPONSE", anError.toString());
        dismissDialog();
        String errorMessage;
        if (anError.getErrorCode() != 0)
            errorMessage = anError.getErrorDetail();
        else errorMessage = "Something went wrong. Please try again!";

        apiResponse.onFailure(errorMessage, apiMode);
    }

    private JsonObject getJson(JSONObject jsonObject) {
        return jsonParser.parse(jsonObject.toString()).getAsJsonObject();
    }

    private void dismissDialog() {
        if (context != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
