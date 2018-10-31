package dev.whaabaam.com.ui.notes;
/*
 * Created by RahulGupta on 11/9/18
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.NotesData;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.userprofile.UserProfileViewModel;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class NotesViewModel extends BaseObservable implements ApiResponse {

    private Context context;
    public final ObservableField<String> notesText = new ObservableField<>();
    public final ObservableBoolean noData = new ObservableBoolean(false);
    private NotesAdapter notesAdapter;

    private String otherUserId;
    private int currentPage = 1;
    private int totalPage = 1;

    public NotesViewModel(Context context, String otherUserId) {
        this.otherUserId = otherUserId;
        this.context = context;
        notesAdapter = new NotesAdapter();
    }

    public void initNotesList(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(notesAdapter);
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

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !notesAdapter.getLoading()) {

                    if (currentPage <= totalPage) {
                        currentPage++;
                        notesAdapter.setIsLoading(true);
                        requestFetchAllNotesApi(true);
                    }
                }
            }
        });


        requestFetchAllNotesApi(false);
    }

    private void requestFetchAllNotesApi(boolean paginate) {
        ApiManager.getInstance().requestApi(context, getNotesRequestBody(otherUserId, paginate),
                AppConstants.API_MODE.VIEW_NOTES, this, true);
    }

    private JSONObject getNotesRequestBody(String otherUserId, boolean paginate) {
        JSONObject jsonObject = new JSONObject();
        try {
            currentPage = paginate ? currentPage : 1;
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("profile_user_id", otherUserId);
            jsonObject.put("page", currentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        switch (apiMode) {
            case VIEW_NOTES:
                ArrayList<NotesData> list = new Gson().fromJson(response.getAsJsonArray(AppKeys.DATA),
                        new TypeToken<ArrayList<NotesData>>() {
                        }.getType());
                if (list.isEmpty())
                    noData.set(true);
                else
                    notesAdapter.addNotes(list);
                break;
            default:
                notesAdapter.add(new NotesData("", notesText.get(), CommonUtils.getCurrentDateTime()));
                notesText.set("");
                break;
        }
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    public View.OnClickListener OnAddNoteClick() {
        return v -> {
            if (!TextUtils.isEmpty(notesText.get())) {
                try {
                    JSONObject jsonObject = UserProfileViewModel.getRequestBody(otherUserId);
                    jsonObject.put("note", notesText.get());
                    ApiManager.getInstance().requestApi(
                            context, jsonObject, AppConstants.API_MODE.ADD_NOTES, this, true
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
