package dev.whaabaam.com.ui.chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.michaelflisar.rxbus2.RxBus;
import com.michaelflisar.rxbus2.RxBusBuilder;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.databinding.DialogReportUserBinding;
import dev.whaabaam.com.quickblox.QuickbloxUtils;
import dev.whaabaam.com.ui.imagepicker.ImagePickerOptionsDialog;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.OnDialogOptionsClickCallback;
import io.reactivex.functions.Consumer;

import static dev.whaabaam.com.app.MyApplication.userModel;
import static dev.whaabaam.com.ui.myprofile.MyProfileActivityViewModel.getImagePickerBundle;

/**
 * @author rahul
 */
public class ChatActivityViewModel extends BaseObservable implements
        QuickbloxUtils.OnChatDialogCreatedCallBack, ApiResponse {
    public final ObservableField<String> message = new ObservableField<>();
    private ChatAdapter chatAdapter;
    private QBChatDialog chatDialog;
    private Activity context;
    public final ObservableField<String> chatUserName = new ObservableField<>();
    public final ObservableBoolean loadingChat = new ObservableBoolean(true);
    public final ObservableField<String> lastActivity = new ObservableField<>("");
    public final ObservableBoolean canUserChat = new ObservableBoolean(false);
    public final ObservableField<String> chatUserProfileImage = new ObservableField<>();
    private String otherUserId = "";

    ChatActivityViewModel(Activity context, QBChatDialog chatDialog, int participantID) {
        this.context = context;
        chatAdapter = new ChatAdapter();

        if (chatDialog != null) {
            this.chatDialog = chatDialog;
            initChat(chatDialog);
        } else {
            QuickbloxUtils.createChatDialog(participantID, this);
        }

        RxBusBuilder.create(String.class)
                .subscribe(path -> QuickbloxUtils.sendAttachmentMessage(path, this.chatDialog));
    }

    private void setupChatSystem() {
        QuickbloxUtils.getChatHistoryForGivenDialog(chatDialog.getDialogId(), this);
        lastActivity.set(CommonUtils.secondsToTimeStamp(QuickbloxUtils.getLastActivityOfUser(chatDialog.getRecipientId())));

        QBUsers.getUser(chatDialog.getRecipientId()).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                chatUserProfileImage.set(qbUser.getCustomData());
            }

            @Override
            public void onError(QBResponseException e) {
                e.printStackTrace();

            }
        });
    }


    public View.OnClickListener onSendClick(final Context context) {
        return view -> {
            if (TextUtils.isEmpty(message.get())) {
                Toast.makeText(context, "Enter a valid message", Toast.LENGTH_SHORT).show();
            } else {
                proceedToSendMessage();
            }
        };
    }

    public View.OnClickListener onSendImage(final Context context) {
        return view -> {
            ImagePickerOptionsDialog dialog = new ImagePickerOptionsDialog();
            dialog.setArguments(getImagePickerBundle(AppConstants.IMAGE_PICKER_SOURCE.CHAT_ATTACHMENT, null));
            dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), null);
        };
    }

    public View.OnClickListener onOverFlowMenuClick() {
        return this::launchChatPopupDialog;
    }

    @Override
    public void onChatDialogCreated(QBChatDialog dialog) {
        this.chatDialog = dialog;
        initChat(dialog);
//        if (!TextUtils.isEmpty(message.get()))
//            QuickbloxUtils.sendMessage(message.get(), chatDialog, null);
    }


    @Override
    public void onChatHistoryLoaded(ArrayList<QBChatMessage> qbChatMessages) {
        chatAdapter.addToList(qbChatMessages);
        loadingChat.set(false);
        RxBus.get().withKey("scrollView").send("");
        checkUserAvailableForChat();
    }

    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {
        switch (apiMode) {
            case CHECK_IN_CONNECTION:
                loadingChat.set(false);
                canUserChat.set(true);
                break;
            case REMOVE_CONNECTION:
                canUserChat.set(false);
                break;
            case REPORT_USER:
                Toast.makeText(context, response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    private void requestRemoveConnectionApi() {
        ApiManager.getInstance().requestApi(context, CommonUtils.getWithdrawOrDeleteRequestQB(chatDialog.getRecipientId()),
                AppConstants.API_MODE.REMOVE_CONNECTION,
                this, true);
    }

//    private JSONObject getRemoveConnectionRequest() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put(AppKeys.USER_ID, userModel.getId());
//            jsonObject.put("", otherUserId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        switch (apiMode) {
            case CHECK_IN_CONNECTION:
                canUserChat.set(false);
                break;
            default:
                break;
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private void initChat(QBChatDialog dialog) {
        try {
            dialog.initForChat(QBChatService.getInstance());
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
            context.finish();
        }
        setupChatSystem();
        getOtherUserId();
    }

    private void getOtherUserId() {
//        QBUsers.getUser(chatDialog.getRecipientId()).performAsync(new QBEntityCallback<QBUser>() {
//            @Override
//            public void onSuccess(QBUser qbUser, Bundle bundle) {
//                otherUserId = qbUser.getLogin();
//            }
//
//            @Override
//            public void onError(QBResponseException e) {
//                e.printStackTrace();
//            }
//        });
    }

    private void proceedToSendMessage() {
        QuickbloxUtils.sendMessage(message.get(), chatDialog, null);
    }

    private void checkUserAvailableForChat() {
        ApiManager.getInstance().requestApi(context, getCheckCanChatRequestBody(), AppConstants.API_MODE.CHECK_IN_CONNECTION,
                this, false);
    }

    private JSONObject getCheckCanChatRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("quickblox_id", chatDialog.getRecipientId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void initChatList(RecyclerView recyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(chatAdapter);

        RxBusBuilder.create(QBChatMessage.class)
                .withKey("QBChatMessageReceived")
                .subscribe(m -> {
                    addMessageToList(recyclerView, m);
                });

        RxBusBuilder.create(QBChatMessage.class)
                .withKey("QBChatMessageSent")
                .subscribe(m -> {
                    message.set("");
                    addMessageToList(recyclerView, m);
                });
        RxBusBuilder.create(String.class)
                .withKey("scrollView")
                .subscribe(m -> {
                    recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                });
    }

    private void addMessageToList(RecyclerView recyclerView, QBChatMessage message) {
        chatAdapter.addNewMessage(message);
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    private void launchChatPopupDialog(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.chat_overflow_menu);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.chat_menu_report:
                    requestReportUserApi();
                    return true;
                case R.id.chat_menu_unfriend:
                    AppDialog.getInstance(context).showRemoveConnectionDialog(
                            context, chatUserName.get(), value -> {
                                requestRemoveConnectionApi();
                            }
                    );
                    return true;
                default:
                    return false;
            }
        });
    }

    private void launchReportUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogReportUserBinding binding = DialogReportUserBinding.inflate(
                context.getLayoutInflater(), null
        );
        builder.setView(binding.getRoot());
        builder.setPositiveButton("Report", ((dialog, which) -> {
            String reportMessage = binding.etReportMessage.getText().toString();
            if (!TextUtils.isEmpty(reportMessage)) {
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Enter a valid report message", Toast.LENGTH_SHORT).show();
            }

        }));
        builder.setNegativeButton("Cancel", ((dialog, which) -> dialog.dismiss()));
        builder.setCancelable(false);
        builder.create().show();
    }

    private void requestReportUserApi() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());

            jsonObject.put("to_quickblox_id", chatDialog.getRecipientId());
            ApiManager.getInstance().requestApi(context,
                    jsonObject, AppConstants.API_MODE.REPORT_USER,
                    ChatActivityViewModel.this, true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
