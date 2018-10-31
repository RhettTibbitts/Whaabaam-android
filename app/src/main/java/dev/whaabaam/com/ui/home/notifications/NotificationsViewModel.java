package dev.whaabaam.com.ui.home.notifications;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidnetworking.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.ConnectionsAddFamilyData;
import dev.whaabaam.com.data.model.other.NotificationData;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.base.OnBottomReachedListener;
import dev.whaabaam.com.ui.userprofile.UserProfileActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.OnDialogOptionsClickCallback;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class NotificationsViewModel extends BaseObservable implements ApiResponse, NotificationAdapter.OnNotificationItemSelectedCallback {
    private int currentPage = 1;
    private NotificationAdapter adapter;
    private Context context;
    public final ObservableBoolean noData = new ObservableBoolean(false);
    private int totalPage = 1;

    NotificationsViewModel(Context context) {
        this.context = context;
        adapter = new NotificationAdapter(this);
    }

    public void initMyConnectionsList(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = lm.getChildCount();
                int totalItemCount = lm.getItemCount();
                int pastVisibleItems = lm.findFirstVisibleItemPosition();

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !adapter.getLoading()) {

                    if (currentPage < totalPage) {
                        currentPage++;
                        adapter.setIsLoading(true);
                        requestNotificationsApi(true);
                    }
                }
            }
        });
        requestNotificationsApi(false);

    }

    private void requestNotificationsApi(boolean paginate) {
        noData.set(false);
        ApiManager.getInstance().requestApi(context, getNotificationRequestBody(paginate), AppConstants.API_MODE.GET_NOTIFICATIONS,
                this, !paginate);
    }

    private JSONObject getNotificationRequestBody(boolean paginate) {
        JSONObject jsonObject = new JSONObject();
        try {
            currentPage = paginate ? currentPage : 1;

            jsonObject.put(AppKeys.PAGE, currentPage);
            jsonObject.put(AppKeys.USER_ID, userModel.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

        switch (apiMode) {
            case GET_NOTIFICATIONS:
                totalPage = response.get("last_page").getAsInt();
                ArrayList<NotificationData.Data> list = new Gson().fromJson(response.getAsJsonArray(AppKeys.DATA),
                        new TypeToken<ArrayList<NotificationData.Data>>() {
                        }.getType());

                if (adapter.getLoading()) {
                    adapter.setIsLoading(false);
                }

                if (list.isEmpty()) {
                    noData.set(true);
                } else {
                    adapter.addNotifications(list);
                    noData.set(false);
                }


                break;
            case RESPOND_TO_CONTACT_REQUEST:
                AppDialog.getInstance(context).showMessageDialogToDismiss(context, response.get(AppKeys.MESSAGE).getAsString());
                break;
            default:
                break;
        }

    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    @Override
    public void onUserProfileSelected(NotificationData.Data data) {

    }

    @Override
    public void onNotificationActionSelected(NotificationData.Data data) {
        String name = data.getConcerned_user().getFirst_name() + " " +
                (data.getConcerned_user().getLast_name() != null ? data.getConcerned_user().getLast_name() : "");

        switch (data.getEvent_type()) {
            case "F":
                AppDialog.getInstance(context).showAcceptRejectDialog(context, name, value -> {
                    ApiManager.getInstance().requestApi(context,
                            CommonUtils.getAcceptRejectRequestBody(value, data.getProfile_user_id()),
                            AppConstants.API_MODE.RESPOND_TO_CONTACT_REQUEST, this, true);
                });

                break;
            case "C":
                Bundle bundle = new Bundle();
                bundle.putString("other_user_id", data.getProfile_user_id());
                ((BaseActivity) context).launchIntent(UserProfileActivity.class, bundle, false);
                break;
            default:
                break;
        }
    }


}
