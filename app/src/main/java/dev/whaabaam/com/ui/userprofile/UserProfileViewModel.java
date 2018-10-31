package dev.whaabaam.com.ui.userprofile;
/*
 * Created by RahulGupta on 11/9/18
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.data.model.other.UserProfileData;
import dev.whaabaam.com.data.model.other.UserProfileInfoModel;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.fullscreenimage.FullScreenImageActivity;
import dev.whaabaam.com.ui.myprofile.MyProfileActivity;
import dev.whaabaam.com.ui.notes.NotesActivity;
import dev.whaabaam.com.ui.resumeviewer.ResumeViewerActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.DividerDecorator;
import dev.whaabaam.com.utils.OnDialogOptionsClickCallback;
import dev.whaabaam.com.utils.SnappyRecyclerView;
import dev.whaabaam.com.utils.UserInfoExtractorThread;

import static dev.whaabaam.com.app.MyApplication.userModel;

/**
 * @author rahul
 */
/*
 * TODO: View all family and view all Mutual contacts
 * */


public class UserProfileViewModel extends BaseObservable implements
        HorizontalImageSliderAdapter.OnImageSelectedCallBack, ApiResponse,
        OnDialogOptionsClickCallback, UserInfoExtractorThread.OnUserInfoExtractedCallback, UserInfoAdapter.OnItemClickCallback {

    private AppCompatActivity context;
    private HorizontalImageSliderAdapter adapter;
    public final ObservableField<UserProfileData> otherUserData = new ObservableField<>();
    private MutualContactsAdapter mutualContactsAdapter;
    private FamilyMembersAdapter familyMembersAdapter;
    public final ObservableInt numMutual = new ObservableInt(0);
    public final ObservableInt numFamily = new ObservableInt(0);
    public final ObservableBoolean viewAllMutuals = new ObservableBoolean(false);
    public final ObservableBoolean viewAllFamily = new ObservableBoolean(false);

    private String otherUserId;
    private UserInfoAdapter userInfoAdapter;
    private UserInfoAdapter2 userInfoAdapter2;
    public final ObservableBoolean noData = new ObservableBoolean(false);
    public final ObservableBoolean noData_ = new ObservableBoolean(false);
    public final ObservableField<String> req_status = new ObservableField<>();

    public UserProfileViewModel(AppCompatActivity context, String otherUserId) {
        this.context = context;
        this.otherUserId = otherUserId;
        adapter = new HorizontalImageSliderAdapter(this);
        mutualContactsAdapter = new MutualContactsAdapter(context);
        familyMembersAdapter = new FamilyMembersAdapter(context);
        userInfoAdapter = new UserInfoAdapter(this);
        userInfoAdapter2 = new UserInfoAdapter2(this);
    }

    public void initHorizontalImageSlider(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void initMutualContactsSlider(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mutualContactsAdapter);
    }

    public void initfamilyMemberSlider(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(familyMembersAdapter);
    }

    public void initUserInfoList1(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(userInfoAdapter2);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerDecorator(ContextCompat.getDrawable(context, R.drawable.recyclerview_divider)));
    }

    public void initUserInfoList(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(userInfoAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerDecorator(ContextCompat.getDrawable(context, R.drawable.recyclerview_divider)));
    }


//    public void initHorizontalImagePagerSlider(ViewPager viewPager) {
////        viewPager.setAdapter(pagerAdapter);
////        viewPager.setClipToPadding(false);
////        // set padding manually, the more you set the padding the more you see of prev & next page
////        viewPager.setPadding(90, 0, 90, 0);
////        // sets a margin b/w individual pages to ensure that there is a gap b/w them
////        viewPager.setPageMargin(20);
//    }

    void requestOtherUserProfileApi() {
        ApiManager.getInstance().requestApi(context, getRequestBody(otherUserId),
                AppConstants.API_MODE.OTHER_PROFILE_DATA,
                this, true);
    }

    public static JSONObject getRequestBody(String otherUserId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("profile_user_id", otherUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onImageSelected(UserModel.Images imageData, ImageView imageView, int position) {
        new Handler().postDelayed(() -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(context,
                            imageView,
                            Objects.requireNonNull(ViewCompat.getTransitionName(imageView)));
            Bundle bundle = options.toBundle();
            Objects.requireNonNull(bundle).putString(AppConstants.IMAGE_PATH, imageData.getUserImages().getOrg());
            FullScreenImageActivity.start(context, bundle);
        }, 100);
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

        switch (apiMode) {
            case OTHER_PROFILE_DATA:
                otherUserData.set(new Gson().fromJson(response.getAsJsonObject(AppKeys.DATA), UserProfileData.class));
                requestInfoExtraction(otherUserData.get());

                if (otherUserData.get().getImage() != null) {
                    adapter.add(new UserModel.Images(otherUserData.get().getImage()));
                }
                adapter.addImages(Objects.requireNonNull(otherUserData.get()).getImages());
                mutualContactsAdapter.addMembers(Objects.requireNonNull(otherUserData.get()).getMutual_friends().getData());
                familyMembersAdapter.addFamilyList(Objects.requireNonNull(otherUserData.get()).getFamily().getData());
                viewAllFamily.set(otherUserData.get().getFamily().getLast_page() > otherUserData.get().getFamily().getCurrent_page());
                viewAllMutuals.set(otherUserData.get().getMutual_friends().getLast_page() > otherUserData.get().getMutual_friends().getCurrent_page());
                req_status.set(otherUserData.get().getReq_status());
                numMutual.set(otherUserData.get().getMutual_friends().getTotal());
                numFamily.set(otherUserData.get().getFamily().getTotal());
                break;
            case REMOVE_CONNECTION:
                AppDialog.getInstance(context).showMessageDialogWithAction(context, response.get(AppKeys.MESSAGE).getAsString(),
                        value -> {
                            ((BaseActivity) context).finish();
                        });
                break;
            case WITHDRAW_CONTACT_REQUEST:
                req_status.set("NEW_USER");
                break;
            case SEND_CONTACT_REQUEST:
                req_status.set("REQ_SENT");
                break;
            default:
                break;

        }

    }

    private void requestInfoExtraction(UserProfileData data) {

        UserInfoExtractorThread thread = new UserInfoExtractorThread(data, this);
        thread.run();
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    public View.OnClickListener onNotesClick() {
        return v -> {
            Bundle bundle = new Bundle();
            bundle.putString("other_user_id", otherUserId);
            ((BaseActivity) context).launchIntent(
                    NotesActivity.class, bundle, false
            );
        };
    }


    /**
     * @param type: type=1 for view all mutual contacts
     *              type=2 for view all family contacts
     */
    public View.OnClickListener onViewAllClick(int type) {
        return v -> {

        };
    }

    public View.OnClickListener onDeleteContactClick() {

        return v -> {
            String userName = otherUserData.get().getFirst_name() + " " + (otherUserData.get().getLast_name() != null ?
                    otherUserData.get().getLast_name() : "");

            switch (req_status.get()) {
                case "NEW_USER":
                    AppDialog.getInstance(context).showCancelConnectionRequestDialog(context, userName, m -> {
                        ApiManager.getInstance().requestApi(context, CommonUtils.getSendContactRequestBody(otherUserId),
                                AppConstants.API_MODE.SEND_CONTACT_REQUEST, this, true);
                    });
                    break;
                case "REQ_RECEIVED":
                    AppDialog.getInstance(context).showAcceptRejectDialog(context, userName, n -> {

                    });
                    break;
                case "REQ_SENT":
                    AppDialog.getInstance(context).showCancelConnectionRequestDialog(context, userName, o -> {
                        ApiManager.getInstance().requestApi(context, CommonUtils.getWithdrawOrDeleteRequestBody(otherUserId),
                                AppConstants.API_MODE.WITHDRAW_CONTACT_REQUEST, this, true);
                    });
                    break;
                case "FRIEND":
                    AppDialog.getInstance(context).showRemoveConnectionDialog(context, userName, p -> {
                        ApiManager.getInstance().requestApi(context, CommonUtils.getWithdrawOrDeleteRequestBody(otherUserId),
                                AppConstants.API_MODE.REMOVE_CONNECTION, this, true);
                    });
                    break;
                default:
                    break;

            }
        };
    }

    public View.OnClickListener onChatClick() {
        return v -> {
            try {
                ((BaseActivity) context).launchChatActivity(null,
                        Integer.parseInt(otherUserData.get().getQuickblox_id()),
                        otherUserData.get().getFirst_name() +
                                (!TextUtils.isEmpty(otherUserData.get().getLast_name()) ? otherUserData.get().getLast_name() : "")
                );
            } catch (Exception e) {
                e.printStackTrace();
                CommonUtils.toast(context, "Some error occurred, please try again!");
            }
        };
    }

    @Override
    public void onPositiveClick(boolean value) {
        ApiManager.getInstance().requestApi(context, CommonUtils.getWithdrawOrDeleteRequestBody(otherUserId),
                AppConstants.API_MODE.REMOVE_CONNECTION, this, true);
    }

    @Override
    public void onUserInfoExtracted(ArrayList<UserProfileInfoModel> data, ArrayList<UserProfileInfoModel> data1) {

        if (data.isEmpty()) {
            noData_.set(true);
        } else {
            userInfoAdapter2.addData(data);
        }


        if (!data1.isEmpty()) {
            userInfoAdapter.addData(data1);

        } else {
            noData.set(true);
        }

    }

    @Override
    public void onViewResumeClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("resume_url", otherUserData.get().getResume());
        ((BaseActivity) context).launchIntent(ResumeViewerActivity.class, bundle, false);
    }

    @Override
    public void onEmailClicked() {
        composeEmail(new String[]{otherUserData.get().getEmail()}, String.format("Hi %s -Sent from Whaabaam",
                otherUserData.get().getFirst_name()));
    }

    @Override
    public void onPhoneClicked() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + otherUserData.get().getPhone()));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(intent);
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, String.format("\n\nRegards \n%s\nSent from WhaaBaam", userModel.getFirst_name()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
