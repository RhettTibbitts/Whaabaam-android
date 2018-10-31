package dev.whaabaam.com.ui.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.quickblox.chat.model.QBChatMessage;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ItemMessageReceivedBinding;
import dev.whaabaam.com.databinding.ItemMessageSentBinding;

import static dev.whaabaam.com.app.MyApplication.userModel;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<QBChatMessage> chatHistory;

    public ChatAdapter() {
        chatHistory = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (i) {
            case R.layout.item_message_sent:
                return new SentChatVH(ItemMessageSentBinding.inflate(inflater, viewGroup, false));
            default:
                return new ReceivedChatVH(ItemMessageReceivedBinding.inflate(inflater, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        QBChatMessage message = chatHistory.get(viewHolder.getAdapterPosition());
        if (viewHolder instanceof SentChatVH) {
            ((SentChatVH) viewHolder).binding.setModel(message);
        } else {
            ((ReceivedChatVH) viewHolder).binding.setModel(message);
        }
    }

    @Override
    public int getItemCount() {
        return chatHistory.size();
    }

    @Override
    public int getItemViewType(int position) {
        return userModel.getQuickblox_id().equals(String.valueOf(chatHistory.get(position).getSenderId())) ?
                R.layout.item_message_sent : R.layout.item_message_received;
    }

    public void addToList(ArrayList<QBChatMessage> qbChatMessages) {
        chatHistory.addAll(qbChatMessages);
        notifyDataSetChanged();
    }

    public void addNewMessage(QBChatMessage m) {
        chatHistory.add(m);
        notifyItemInserted(chatHistory.size());
    }

    static class ReceivedChatVH extends RecyclerView.ViewHolder {
        ItemMessageReceivedBinding binding;

        public ReceivedChatVH(@NonNull ItemMessageReceivedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static class SentChatVH extends RecyclerView.ViewHolder {
        ItemMessageSentBinding binding;

        public SentChatVH(@NonNull ItemMessageSentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}