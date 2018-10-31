package dev.whaabaam.com.ui.family;
/*
 * Created by RahulGupta on 28/8/18
 */

import android.app.Dialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.michaelflisar.rxbus2.RxBusBuilder;
import com.michaelflisar.rxbus2.rx.RxBusMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.ConnectionsAddFamilyData;
import dev.whaabaam.com.data.model.other.Relationships;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.home.notifications.NotificationsViewModel;
import dev.whaabaam.com.ui.userprofile.UserProfileActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class AddFamilyViewModel extends BaseObservable implements ApiResponse, AddFamilyConnectionListAdapter.OnConnectionSelectedCallback {
    private Context context;
    private AddFamilyConnectionListAdapter adapter;
    private ConnectionsAddFamilyData data;
    private int toBeAddedFamilyPos = 0;
    private Dialog builder;


    public AddFamilyViewModel(Context context) {
        this.context = context;
        adapter = new AddFamilyConnectionListAdapter(this);
        RxBusBuilder.create(Relationships.class)
                .withBound(this)
                .withKey("relation")
                .withMode(RxBusMode.Main)
                .subscribe(data -> {
                    if (data.getName().equalsIgnoreCase("other")) {
                        new Handler().postDelayed(() -> requestOtherRelationDialog(data), 1000);
                    } else {
                        requestAddConnectionToFamilyApi(data, "");
                    }

                });
    }

    private void requestOtherRelationDialog(Relationships relationships) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.other_relation_dialog, null);
        EditText editText = view.findViewById(R.id.et_other_relation);
        Button submit = view.findViewById(R.id.btnSubmit);
        Button cancel = view.findViewById(R.id.btnCancel);
        builder.setContentView(view);


        Objects.requireNonNull(builder.getWindow())
                .setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        submit.setOnClickListener(view1 -> {
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError(null);
                requestAddConnectionToFamilyApi(relationships, editText.getText().toString());
            } else {
                editText.setError("Enter a valid relation");
                editText.requestFocus();
            }
        });
        cancel.setOnClickListener(view1 -> builder.dismiss());
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        builder.show();
    }

    public void initList(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
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
                    adapter.setIsLoading(true);
                    requestConnectionList(true);
                }
            }
        });
        requestConnectionList(false);
    }

    private void requestConnectionList(boolean paginate) {


        ApiManager.getInstance().requestApi(context, CommonUtils.getRequestBodyWithUserID(),
                AppConstants.API_MODE.CONNECTION_LIST_FOR_FAMILY, this, !paginate);
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        switch (apiMode) {
            case REQUEST_RELATION_OPTIONS:
                //populate bottom sheet with relation options
                /*Reusing the Relationship class which ahs the same data members*/
                ArrayList<Relationships> list1 = new Gson().fromJson(response.getAsJsonArray(AppKeys.DATA),
                        new TypeToken<ArrayList<Relationships>>() {
                        }.getType());
                requestRelationDialog(list1);

                break;
            case CONNECTION_LIST_FOR_FAMILY:
                ArrayList<ConnectionsAddFamilyData> list = new Gson().fromJson(response.getAsJsonArray(AppKeys.DATA),
                        new TypeToken<ArrayList<ConnectionsAddFamilyData>>() {
                        }.getType());
                adapter.addList(list);

                if (adapter.getLoading())
                    adapter.setIsLoading(false);

                break;
            case ADD_FAMILY_MEMBER:
                if (builder != null) builder.dismiss();
                Toast.makeText(context, response.get(AppKeys.MESSAGE).getAsString(), Toast.LENGTH_SHORT).show();
                adapter.remove(toBeAddedFamilyPos);
                break;
        }

    }

    private void requestRelationDialog(ArrayList<Relationships> list1) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("relations", list1);
        RelationOptionsSheet dialog = new RelationOptionsSheet();
        dialog.setArguments(bundle);
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    @Override
    public void onViewProfile(ConnectionsAddFamilyData data) {
        ((BaseActivity) context).launchIntent(UserProfileActivity.class, null, false);
    }

    @Override
    public void onAddConnectionToFamily(ConnectionsAddFamilyData data, int pos) {
        this.data = data;
        toBeAddedFamilyPos = pos;
        requestRelationOptionsApi();
    }

    private void requestRelationOptionsApi() {
        ApiManager.getInstance().requestApi(context, new JSONObject(),
                AppConstants.API_MODE.REQUEST_RELATION_OPTIONS, this, true);
    }

    private void requestAddConnectionToFamilyApi(Relationships relationData, String other) {
        ApiManager.getInstance().requestApi(context, getRequestBody(relationData, other),
                AppConstants.API_MODE.ADD_FAMILY_MEMBER, this, true);
    }

    private JSONObject getRequestBody(Relationships relationData, String other) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("another_user_id", data.getFriend_user_id());
            jsonObject.put("family_relation_id", relationData.getId());
            jsonObject.put("other_relation_detail", other);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
