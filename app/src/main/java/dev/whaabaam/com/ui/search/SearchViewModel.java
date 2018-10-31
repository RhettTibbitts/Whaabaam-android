package dev.whaabaam.com.ui.search;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.CloseContactsData;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.home.closecontacts.CloseContactsListAdapter;
import dev.whaabaam.com.ui.userprofile.UserProfileActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.userModel;
import static dev.whaabaam.com.ui.home.closecontacts.CloseContactsViewModel.requestSendConnectionApi;

/**
 * @author rahul
 */
public class SearchViewModel extends BaseObservable implements ApiResponse, CloseContactsListAdapter.OnCloseContactsClickCallback {

    public final ObservableField<String> searchText = new ObservableField<>("");
    private BaseActivity context;
    private int currentPage = 1;
    private int totalPage = 1;
    private CloseContactsListAdapter adapter;
    private CloseContactsData currentClickItemData;
    private int currentClickItemDataPosition;
    private boolean hasAccepted = false;

    public SearchViewModel(BaseActivity context) {
        this.context = context;
        adapter = new CloseContactsListAdapter(this);
    }


    void initUsersList(RecyclerView recyclerView) {
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
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = lm.getChildCount();
                int totalItemCount = lm.getItemCount();
                int pastVisibleItems = lm.findFirstVisibleItemPosition();

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !adapter.getLoading()) {

                    if (currentPage < totalPage) {
                        currentPage++;
                        adapter.setIsLoading(true);
                        requestSearchUserApi(true);
                    }
                }
            }
        });
        requestSearchUserApi(false);
    }

    void requestSearchUserApi(boolean paginate) {

        context.hideKeyboard();

        if (!paginate) {
            adapter.clearList();
        }

        ApiManager.getInstance().requestApi(
                context, getSearchUserRequestBody(paginate),
                AppConstants.API_MODE.SEARCH, this, !paginate
        );
    }

    public JSONObject getSearchUserRequestBody(boolean paginate) {
        JSONObject jsonObject = new JSONObject();
        try {
            currentPage = paginate ? currentPage : 1;
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("search", searchText.get());
            jsonObject.put(AppKeys.PAGE, currentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

        switch (apiMode) {
            case SEARCH:
                if (response != null && response.get(AppKeys.DATA) != null) {

                    JsonArray data = response.get(AppKeys.DATA).getAsJsonArray();
                    totalPage = response.get("last_page").getAsInt();
                    ArrayList<CloseContactsData> list = new Gson().fromJson(data,
                            new TypeToken<ArrayList<CloseContactsData>>() {
                            }.getType());

                    if (adapter.getLoading()) {
                        adapter.setIsLoading(false);
                    }

                    if (list.isEmpty()) {
                    } else {
                        adapter.addList(list);
                    }

                }
                break;
            case SEND_CONTACT_REQUEST:
                currentClickItemData.setReq_status("REQ_SENT");
                adapter.notifyDataSetChanged();
                break;
            case WITHDRAW_CONTACT_REQUEST:
                currentClickItemData.setReq_status("NEW_USER");
                adapter.notifyDataSetChanged();
                break;
            case REMOVE_CONNECTION:
                adapter.notifyItemRemoved(currentClickItemDataPosition);
                break;
            case RESPOND_TO_CONTACT_REQUEST:
                if (hasAccepted) {
                    currentClickItemData.setReq_status("FRIEND");

                } else {
                    currentClickItemData.setReq_status("NEW_USER");
                }
                adapter.notifyDataSetChanged();

                break;
            default:
                break;
        }

    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        CommonUtils.toast(context, message);
    }

    @Override
    public void onOptionsClick(CloseContactsData data, int position) {
        currentClickItemData = data;
        currentClickItemDataPosition = position;
        String userName = data.getUser_info().getFirst_name() + " "
                + (data.getUser_info().getLast_name() != null ? data.getUser_info().getLast_name() : "");

        if (data.getReq_status().equalsIgnoreCase("FRIEND")) {
            try {
                context.launchChatActivity(null,
                        Integer.parseInt(data.getUser_info().getQuickblox_id()), userName);
            } catch (Exception e) {
                e.printStackTrace();
                AppDialog.getInstance(context).showMessageDialogToDismiss(context, "User not registered with WhaaBaam chat.");
            }

        } else if (data.getReq_status().equalsIgnoreCase(AppConstants.USER_REQUEST_STATUS.NEW_USER)) {
            AppDialog.getInstance(context).showConnectionRequestDialog(context, userName, value -> {
                requestSendConnectionApi(context, data, this);
            });
        } else if (data.getReq_status().equalsIgnoreCase(AppConstants.USER_REQUEST_STATUS.REQ_SENT)) {
            AppDialog.getInstance(context).showCancelConnectionRequestDialog(context, userName, value -> {
                ApiManager.getInstance().requestApi(context,
                        CommonUtils.getWithdrawOrDeleteRequestBody(data.getUser_info().getId()),
                        AppConstants.API_MODE.WITHDRAW_CONTACT_REQUEST, this, true);
            });
        } else if (data.getReq_status().equalsIgnoreCase(AppConstants.USER_REQUEST_STATUS.REQ_RECEIVED)) {
            AppDialog.getInstance(context).showAcceptRejectDialog(context, userName, value -> {
                hasAccepted = value;
                ApiManager.getInstance().requestApi(context,
                        CommonUtils.getAcceptRejectRequestBody(value, data.getUser_info().getId()),
                        AppConstants.API_MODE.RESPOND_TO_CONTACT_REQUEST, this, true);
            });
        }
    }

    @Override
    public void onNavigateToProfileClicked(CloseContactsData data) {
        Bundle bundle = new Bundle();
        bundle.putString("other_user_id", data.getUser_info().getId());
        context.launchIntent(UserProfileActivity.class, bundle, false);
    }
}
