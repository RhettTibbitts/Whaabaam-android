package dev.whaabaam.com.ui.home.chatlist;
/*
 * Created by RahulGupta on 31/8/18
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.whaabaam.com.quickblox.QuickbloxUtils;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.chat.ChatActivity;
import dev.whaabaam.com.ui.home.myconnections.MyConnectionsFragment;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class ChatListViewModel extends BaseObservable implements ChatListAdapter.OnChatDialogSelectedCallback,
        ChatListAdapter.OnChatDialogFoundCallback {

    private Context context;
    private ChatListAdapter chatListAdapter;
    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableBoolean noData = new ObservableBoolean(false);

    public ChatListViewModel(Context context) {
        this.context = context;
        chatListAdapter = new ChatListAdapter(this);
    }

    public void initChatDialogList(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(chatListAdapter);
    }

    public void fetchAllChatDialogs() {
        isLoading.set(true);
        chatListAdapter.clear();
        noData.set(false);
        QuickbloxUtils.getChatDialogs(this);
    }

    @Override
    public void onChatDialogSelected(QBChatDialog chatDialog, int position) {
        chatDialog.setUnreadMessageCount(0);
        chatListAdapter.notifyItemChanged(position);
        ((BaseActivity) context).launchChatActivity(chatDialog,
                chatDialog.getRecipientId(), chatDialog.getName());
    }

    @Override
    public void onChatDialogFound(ArrayList<QBChatDialog> dialogList) {
        isLoading.set(false);
        if (dialogList.isEmpty()) {
            noData.set(true);
        } else {
            Collections.sort(dialogList, (t1, chatDialog) -> (chatDialog.getUpdatedAt().compareTo(t1.getUpdatedAt())));
            chatListAdapter.addChatDialogList(dialogList);
        }
    }

    //
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        chatListAdapter.filterList(s.toString());
//    }
    public void afterTextChanged(Editable s) {
        chatListAdapter.filterList(s.toString());
    }

    public View.OnClickListener onMyConnectionsClick() {
        return v -> {
            ((BaseActivity) context).launchIntent(MyConnectionsFragment.class,
                    null, false);
        };
    }

}
