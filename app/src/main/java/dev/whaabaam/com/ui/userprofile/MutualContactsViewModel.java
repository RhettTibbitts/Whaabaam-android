package dev.whaabaam.com.ui.userprofile;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.utils.AppDialog;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class MutualContactsViewModel extends BaseObservable implements ApiResponse {

    private Context context;
    private MutualContactsAdapter adapter;
    public final ObservableBoolean noData = new ObservableBoolean(false);
    public int profileID = 0;
    private int currentPage = 1;
    private int totalPage = 1;

    public MutualContactsViewModel(Context context) {
        this.context = context;
    }

    public void initMutualContactsList(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void requestMutualContactsApi(boolean paginate) {

        ApiManager.getInstance().requestApi(context, getMutualContactsRequestBody(paginate), AppConstants.API_MODE.MUTUAL_CONTACTS,
                this, false);
    }

    private JSONObject getMutualContactsRequestBody(boolean paginate) {
        JSONObject jsonObject = new JSONObject();
        try {
            currentPage = paginate ? currentPage : 1;
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("profile_user_id", profileID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context,message);
    }
}
