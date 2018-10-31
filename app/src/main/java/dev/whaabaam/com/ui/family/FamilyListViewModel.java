package dev.whaabaam.com.ui.family;
/*
 * Created by RahulGupta on 27/8/18
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.FamilyData;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.userprofile.UserProfileActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class FamilyListViewModel extends BaseObservable implements FamilyListAdapter.OnFamilyListActionsCallback,
        ApiResponse {
    private FamilyListAdapter adapter;
    private Context context;
    private int removeRequestedPosition = 0;
    final public ObservableBoolean noData = new ObservableBoolean(false);
    private int currentPage = 1;
    private int totalPage = 1;
    public int userID;

    public FamilyListViewModel(Context context, int userID) {
        this.context = context;
        this.userID=userID;
        adapter = new FamilyListAdapter(this);
    }

    public void initFamilyList(RecyclerView recyclerView) {
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
                        requestFamilyApi(true);
                    }
                }
            }
        });
    }

    public void requestFamilyApi(boolean paginate) {
        if (!paginate)
            adapter.clear();

        noData.set(false);
        ApiManager.getInstance().requestApi(context, getFamilyRequestBody(paginate), AppConstants.API_MODE.FAMILY_LIST,
                this, true);
    }

    private JSONObject getFamilyRequestBody(boolean paginate) {
        JSONObject jsonObject = new JSONObject();
        try {
            currentPage = paginate ? currentPage : 1;

            jsonObject.put("page", currentPage);
            jsonObject.put(AppKeys.USER_ID, userModel.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onFamilySelected(FamilyData data) {
        ((BaseActivity) context).launchIntent(UserProfileActivity.class, null, false);
    }

    @Override
    public void onFamilyRemoved(FamilyData data, int position) {
        removeRequestedPosition = position;
        ApiManager.getInstance().requestApi(context, getFamilyRemoveRequest(data), AppConstants.API_MODE.REMOVE_FAMILY_MEMBER,
                this, true);
    }

    private JSONObject getFamilyRemoveRequest(FamilyData data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("family_member_id", data.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        switch (apiMode) {
            case FAMILY_LIST:
                ArrayList<FamilyData> list = new Gson().fromJson(response.getAsJsonArray(AppKeys.DATA),
                        new TypeToken<ArrayList<FamilyData>>() {
                        }.getType());
                if (list.isEmpty())
                    noData.set(true);
                else
                    adapter.addFamilyList(list);

                if (adapter.getLoading())
                    adapter.setIsLoading(false);
                break;
            case REMOVE_FAMILY_MEMBER:
                adapter.remove(removeRequestedPosition);
                break;
        }
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    public View.OnClickListener onAddFamilyClick(Context context) {
        return view -> {
            ((BaseActivity) context).launchIntent(AddFamilyActivity.class, null, false);
        };
    }
}
