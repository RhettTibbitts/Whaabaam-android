package dev.whaabaam.com.ui.notificationprefs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.persistence.room.util.StringUtil;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.FilterData;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.home.HomeActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class NotificationPrefsViewModel extends BaseObservable implements OnNotificationPrefsCallback, ApiResponse {

    private ArrayList<FilterData> list;
    private NotificationPrefsAdapter adapter;
    private Activity context;
    public final ObservableField<String> captureTime = new ObservableField<>();
    public boolean isFirstRun = false;

    NotificationPrefsViewModel(Activity context) {
        this.context = context;
        list = new ArrayList<>();
        adapter = new NotificationPrefsAdapter(this);
        requestPrefsApi();
    }

    private void requestPrefsApi() {
        ApiManager.getInstance().requestApi(context, getNotifPrefsRequestBody(), AppConstants.API_MODE.NOTIFICATION_PREFS,
                this, true);
    }

    private JSONObject getNotifPrefsRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void initPrefsList(RecyclerView recyclerView) {
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPrefsSelected(FilterData data, int position) {
        if (data.getSelected().equals("true"))
            data.setSelected("false");
        else
            data.setSelected("true");

        adapter.notifyItemChanged(position);
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        switch (apiMode) {
            case NOTIFICATION_PREFS:
                list = new Gson().fromJson(response.getAsJsonArray(AppKeys.DATA), new TypeToken<ArrayList<FilterData>>() {
                }.getType());
                adapter.addAll(list);
                captureTime.set(response.get(AppKeys.CAPTURE_TIME_PERIOD).getAsString());
                break;
            case SAVE_NOTIFICATION_PREFS:
                if (isFirstRun) {
                    CommonUtils.startNewTaskInstance(context, HomeActivity.class);
                    context.finish();
                } else
                    AppDialog.getInstance(context).showMessageDialogToDismiss(context, response.get(AppKeys.MESSAGE).getAsString());
                break;
        }
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    public View.OnClickListener onSavePreferencesClick() {
        return view -> {
            ((BaseActivity) context).hideKeyboard();
            if (!TextUtils.isEmpty(captureTime.get())) {
                if (Integer.parseInt(captureTime.get()) < 5) {
                    CommonUtils.toast(context, "Capture time cannot be less than five minutes.");
                } else {
                    requestSavePreferencesApi();
                }
            } else {
                CommonUtils.toast(context, "Enter a valid capture time. ");
            }
        };
    }

    private void requestSavePreferencesApi() {
        ApiManager.getInstance().requestApi(context, getUserPrefsRequestBody(), AppConstants.API_MODE.SAVE_NOTIFICATION_PREFS,
                this, true);
    }

    @SuppressLint("RestrictedApi")
    private JSONObject getUserPrefsRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put(AppKeys.CAPTURE_TIME_PERIOD, captureTime.get());
            jsonObject.put(AppKeys.CAPTURE_FILTER_IDS, StringUtil.joinIntoString(adapter.getCheckedPrefs()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
