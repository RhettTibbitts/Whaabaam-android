package dev.whaabaam.com.ui.home.chatlist;
/*
 * Created by RahulGupta on 31/8/18
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import dev.whaabaam.com.databinding.ItemChatDialogBinding;
import dev.whaabaam.com.ui.binding.Bindings;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatVH> {
    private ArrayList<QBChatDialog> chatDialogList;
    private ArrayList<QBChatDialog> copyList;
    private OnChatDialogSelectedCallback callback;

    public ChatListAdapter(OnChatDialogSelectedCallback callback) {
        this.callback = callback;
        copyList = new ArrayList<>();
        chatDialogList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChatVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChatVH(ItemChatDialogBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatVH chatVH, int i) {
        QBChatDialog data = chatDialogList.get(chatVH.getAdapterPosition());
        chatVH.binding.setModel(data);
        chatVH.binding.userDataView.setOnClickListener(view -> {
            callback.onChatDialogSelected(data, chatVH.getAdapterPosition());
        });
        QBUsers.getUser(data.getRecipientId()).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Bindings.bindProfileImage(chatVH.binding.imageView7, qbUser.getCustomData());
            }

            @Override
            public void onError(QBResponseException e) {
                e.printStackTrace();

            }
        });
    }

    @Override
    public int getItemCount() {
        return chatDialogList.size();
    }

    public void addChatDialogList(ArrayList<QBChatDialog> list) {
        copyList.addAll(list);
        for (QBChatDialog d : list) {
            chatDialogList.add(d);
            notifyItemInserted(chatDialogList.size());
        }
    }

    public ArrayList<QBChatDialog> getChatDialogList() {
        return chatDialogList;
    }

    public QBChatDialog getDialogByPosition(int pos) {
        return chatDialogList.get(pos);
    }

    public void filterList(String input) {
        if (!TextUtils.isEmpty(input)) {
            chatDialogList.clear();
            for (QBChatDialog d : copyList) {
                if (d.getName().toLowerCase().contains(input.toLowerCase())) {
                    chatDialogList.add(d);
                }
            }

        } else {
            chatDialogList.clear();
            chatDialogList.addAll(copyList);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        chatDialogList.clear();
        notifyDataSetChanged();
    }

    static class ChatVH extends RecyclerView.ViewHolder {
        private ItemChatDialogBinding binding;

        public ChatVH(@NonNull ItemChatDialogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnChatDialogSelectedCallback {
        void onChatDialogSelected(QBChatDialog chatDialog, int pos);
    }

    public interface OnChatDialogFoundCallback {
        void onChatDialogFound(ArrayList<QBChatDialog> dialogList);
    }
}
