package dev.whaabaam.com.ui.home.myconnections;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.MyConnectionsData;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.search.SearchActivity;
import dev.whaabaam.com.ui.userprofile.UserProfileActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.OnDialogOptionsClickCallback;

import static dev.whaabaam.com.app.MyApplication.userModel;

/**
 * @author rahul
 */
public class MyConnectionsViewModel extends BaseObservable implements OnDialogOptionsClickCallback, ApiResponse,
        MyConnectionsAdapter.OnMyConnectionsClickCallback {
    private MyConnectionsAdapter adapter;
    private BaseActivity context;
    public final ObservableBoolean noData = new ObservableBoolean(false);
    private int currentPage = 1;
    private int totalPage = 1;

    public MyConnectionsViewModel(BaseActivity context) {
        this.context = context;
        adapter = new MyConnectionsAdapter(this);
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
                        requestMyConnectionsApi(true);
                    }
                }
            }
        });
        requestMyConnectionsApi(false);
    }


//    public View.OnClickListener onAddClick(final Context context) {
//        return view -> AppDialog.getInstance(context).showConnectionRequestDialog(context,
//                "test", MyConnectionsViewModel.this);
//    }

    @Override
    public void onPositiveClick(boolean value) {

    }

    private void requestMyConnectionsApi(boolean paginate) {

        if (!paginate) {
            adapter.clear();
        }


        ApiManager.getInstance().requestApi(context, getMyConnectionRequestBody(paginate),
                AppConstants.API_MODE.MY_CONNECTIONS, this, false);
    }

    private JSONObject getMyConnectionRequestBody(boolean paginate) {
        JSONObject jsonObject = new JSONObject();
        try {
            currentPage = paginate ? currentPage : 1;
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put(AppKeys.PAGE, currentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

        if (adapter.getLoading()) {
            adapter.setIsLoading(false);
        }

        ArrayList<MyConnectionsData> list = new Gson().fromJson(response.getAsJsonArray(AppKeys.DATA),
                new TypeToken<ArrayList<MyConnectionsData>>() {
                }.getType());


        if (list.isEmpty()) {
            noData.set(true);
        } else {
            noData.set(false);
            adapter.addConnectionList(list);
        }
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    @Override
    public void onChatClicked(MyConnectionsData data) {
        String userName = data.getFirst_name() + (data.getLast_name() != null ? data.getLast_name() : "");
        try {
            context.launchChatActivity(null,
                    Integer.parseInt(data.getQuickblox_id()), userName);
        } catch (Exception e) {
            e.printStackTrace();
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, "Something went wrong. Please try again");
        }
    }

    @Override
    public void onNavigateToProfileClicked(MyConnectionsData data) {
        Bundle bundle = new Bundle();
        bundle.putString("other_user_id", data.getFriend_user_id());
        context.launchIntent(UserProfileActivity.class, bundle, false);
    }

    public void afterTextChanged(Editable s) {
        adapter.filterList(s.toString());
    }

    public View.OnClickListener onSearchClick() {
        return v -> {
            context.launchIntent(SearchActivity.class, null, false);
        };
    }
}
