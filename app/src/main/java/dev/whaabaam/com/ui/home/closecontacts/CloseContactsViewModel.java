package dev.whaabaam.com.ui.home.closecontacts;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.michaelflisar.rxbus2.RxBusBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.CloseContactsData;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.filter.FilterBottomSheet;
import dev.whaabaam.com.ui.userprofile.UserProfileActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.StartSnapHelper;

import static dev.whaabaam.com.app.MyApplication.userModel;

/**
 * @author rahul
 */
public class CloseContactsViewModel extends BaseObservable implements ApiResponse,
        CloseContactsListAdapter.OnCloseContactsClickCallback {
    private CloseContactsListAdapter contactsListAdapter;
    public final ObservableBoolean isLoading = new ObservableBoolean();
    public final ObservableField<Date> selectedDate = new ObservableField<>(new Date());
    public final ObservableBoolean noData = new ObservableBoolean(false);
    private Context context;
    private int currentPage = 1;
    private int totalPage = 1;
    private String searchText = "";
    private CloseContactsData currentClickItemData = null;
    private int currentClickItemDataPosition = -1;
    private boolean hasAccepted = false;
    public int selectedDatePosition = 0;


    // constructor
    CloseContactsViewModel(Context context) {
        this.context = context;
        contactsListAdapter = new CloseContactsListAdapter(CloseContactsViewModel.this);
        selectedDate.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                currentPage = 1;
                requestCloseContactsApi(false);
            }
        });


        RxBusBuilder.create(String.class)
                .withKey("applyFilter")
                .subscribe(s -> {
                    requestCloseContactsApi(false);
                });
    }


    /*
     * BEGIN: Setup Close Contacts List
     * */
    public void setupContactsList(RecyclerView contactsList) {
        contactsList.setLayoutManager(new LinearLayoutManager(contactsList.getContext()));
        contactsList.setHasFixedSize(true);
        contactsList.setAdapter(contactsListAdapter);
        new StartSnapHelper().attachToRecyclerView(contactsList);
        contactsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !contactsListAdapter.getLoading()) {

                    if (currentPage < totalPage) {
                        currentPage++;
                        contactsListAdapter.setIsLoading(true);
                        requestCloseContactsApi(true);
                    }
                }
            }
        });

    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            requestCloseContactsApi(false);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    public void requestCloseContactsApi(boolean paginate) {

        if (!paginate) {
            contactsListAdapter.clearList();
            isLoading.set(true);
        }

        noData.set(false);
        ApiManager.getInstance().requestApi(context, getRequestBody(paginate), AppConstants.API_MODE.GET_CAPTURED_USERS,
                this, false);

    }

    private JSONObject getRequestBody(boolean paginate) {
        JSONObject object = new JSONObject();
        try {
            currentPage = paginate ? currentPage : 1;
            object.put(AppKeys.DATE, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.get()));
            object.put(AppKeys.PAGE, currentPage);
            object.put("filters", new JSONArray(CommonUtils.getUserFilters()));
            object.put("search", searchText);
            object.put(AppKeys.USER_ID, userModel.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return object;
    }

    // ------------------------END---------------


    /*
    * {
	"status": 200,	"message": "Close users",	"data": [{		"id": 16,		"user_id": 1,
		"capture_user_id": 15,		"updated_at": "2018-08-13 16:02:16",		"lat": "30.9108",		"lng": "75.8793",
		"address": "",		"user_info": {			"id": 15,			"first_name": "Pranav",
			"last_name": "kumar",			"image": null		},		"req_status": "NEW_USER"	}],
	"current_page": 1,	"per_page": 10,	"last_page": 2}    * */

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

        switch (apiMode) {
            case GET_CAPTURED_USERS:
                searchText = "";
                isLoading.set(false);
                JsonArray data = response.get(AppKeys.DATA).getAsJsonArray();
                totalPage = response.get("last_page").getAsInt();
                ArrayList<CloseContactsData> list = new Gson().fromJson(data,
                        new TypeToken<ArrayList<CloseContactsData>>() {
                        }.getType());
                if (contactsListAdapter.getLoading())
                    contactsListAdapter.setIsLoading(false);

                if (list.isEmpty())
                    noData.set(true);
                else {
                    contactsListAdapter.addList(list);
                    noData.set(false);
                }


                break;
            case SEND_CONTACT_REQUEST:
                currentClickItemData.setReq_status("REQ_SENT");
                contactsListAdapter.notifyDataSetChanged();
                break;
            case WITHDRAW_CONTACT_REQUEST:
                currentClickItemData.setReq_status("NEW_USER");
                contactsListAdapter.notifyDataSetChanged();
                break;
            case REMOVE_CONNECTION:
                contactsListAdapter.notifyItemRemoved(currentClickItemDataPosition);
                break;
            case RESPOND_TO_CONTACT_REQUEST:
                if (hasAccepted) {
                    currentClickItemData.setReq_status("FRIEND");

                } else {
                    currentClickItemData.setReq_status("NEW_USER");
                }
                contactsListAdapter.notifyDataSetChanged();

                break;
            default:
                break;

        }

    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        isLoading.set(false);
    }

    public View.OnClickListener onSearchClick(Context context) {
        return view -> {
//            ((BaseActivity) context).launchIntent(SearchActivity.class,
//                    null, false);
            searchText = "";
            DialogPlus dialog = DialogPlus.newDialog(context)
                    .setGravity(Gravity.TOP)
                    .setOnBackPressListener(DialogPlus::dismiss)
                    .setContentHolder(new ViewHolder(R.layout.layout_search_dialog))
                    .setOnItemClickListener((dialog1, item, view1, position) -> {
                    })
                    .setExpanded(true, 300)
                    .setPadding(20, 20, 20, 20)
//                    .setOnClickListener((dialog12, view12) -> {
//
                    .create();
            dialog.show();

            View dialogView = dialog.getHolderView();
            dialogView.findViewById(R.id.dialogBtnSearch).setOnClickListener(v ->
            {
                EditText st = dialogView.findViewById(R.id.dialogEtSearch);
                searchText = st.getText().toString();
                dialog.dismiss();
                requestCloseContactsApi(false);
            });


        };

    }

    public View.OnClickListener onFilter(final Context context) {
        return view -> FilterBottomSheet.getInstance().show(((AppCompatActivity) context)
                .getSupportFragmentManager(), "");
    }

    @Override
    public void onOptionsClick(CloseContactsData data, int position) {
        currentClickItemData = data;
        currentClickItemDataPosition = position;
        String userName = data.getUser_info().getFirst_name() + " "
                + (data.getUser_info().getLast_name() != null ? data.getUser_info().getLast_name() : "");

        if (data.getReq_status().equalsIgnoreCase("FRIEND")) {
            try {
                ((BaseActivity) context).launchChatActivity(null,
                        Integer.parseInt(data.getUser_info().getQuickblox_id()), userName);
            } catch (Exception e) {
                e.printStackTrace();
                AppDialog.getInstance(context).showMessageDialogToDismiss(context, "User not registered with WhaaBaam chat.");
            }

        } else if (data.getReq_status().equalsIgnoreCase(AppConstants.USER_REQUEST_STATUS.NEW_USER)) {
            AppDialog.getInstance(context).showConnectionRequestDialog(context, userName, value -> {
                requestSendConnectionApi(context, data, CloseContactsViewModel.this);
            });
        } else if (data.getReq_status().equalsIgnoreCase(AppConstants.USER_REQUEST_STATUS.REQ_SENT)) {
            AppDialog.getInstance(context).showCancelConnectionRequestDialog(context, userName, value -> {
                ApiManager.getInstance().requestApi(context,
                        CommonUtils.getWithdrawOrDeleteRequestBody(data.getCapture_user_id()),
                        AppConstants.API_MODE.WITHDRAW_CONTACT_REQUEST, this, true);
            });
        } else if (data.getReq_status().equalsIgnoreCase(AppConstants.USER_REQUEST_STATUS.REQ_RECEIVED)) {
            AppDialog.getInstance(context).showAcceptRejectDialog(context, userName, value -> {
                hasAccepted = value;
                ApiManager.getInstance().requestApi(context,
                        CommonUtils.getAcceptRejectRequestBody(value, data.getCapture_user_id()),
                        AppConstants.API_MODE.RESPOND_TO_CONTACT_REQUEST, this, true);
            });
        }
    }

    public static void requestSendConnectionApi(Context context, CloseContactsData data, ApiResponse callback) {
        ApiManager.getInstance().requestApi(context,
                CommonUtils.getSendContactRequestBody(data.getUser_info().getId()),
                AppConstants.API_MODE.SEND_CONTACT_REQUEST, callback, true);
    }

    @Override
    public void onNavigateToProfileClicked(CloseContactsData data) {
        Bundle bundle = new Bundle();
        bundle.putString("other_user_id", data.getUser_info().getId());
        ((BaseActivity) context).launchIntent(UserProfileActivity.class, bundle, false);
    }
}
