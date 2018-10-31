package dev.whaabaam.com.ui.home.notifications;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.NotificationData;
import dev.whaabaam.com.databinding.ItemLoadMoreBinding;
import dev.whaabaam.com.databinding.ItemNotificationsBinding;
import dev.whaabaam.com.ui.base.ItemLoadMoreVH;
import dev.whaabaam.com.ui.base.OnBottomReachedListener;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NotificationData.Data> notificationList;

    private OnNotificationItemSelectedCallback callback;
    private boolean isLoadingMore = false;

    public NotificationAdapter(OnNotificationItemSelectedCallback callback) {
        this.callback = callback;
        notificationList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case R.layout.item_notifications:
                return new NotificationVh(ItemNotificationsBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                        viewGroup, false));
            default:
                return new ItemLoadMoreVH(ItemLoadMoreBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                        viewGroup, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof NotificationVh) {
            NotificationVh notificationVh = (NotificationVh) holder;
            NotificationData.Data data = notificationList.get(notificationVh.getAdapterPosition());
            notificationVh.binding.setModel(data);
            notificationVh.binding.imageView10.setOnClickListener(v -> {
                callback.onNotificationActionSelected(data);
            });
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (isLoadingMore && position == notificationList.size() - 1)
                ? R.layout.item_load_more : R.layout.item_notifications;
    }

    public void addNotifications(ArrayList<NotificationData.Data> list) {
        for (NotificationData.Data data : list) {
            add(data);
        }
    }

    public void add(NotificationData.Data data) {
        if (!notificationList.contains(data)) {
            notificationList.add(data);
            notifyItemInserted(notificationList.size());
        }
    }

    public boolean getLoading() {
        return isLoadingMore;
    }

    public void setIsLoading(boolean value) {
        isLoadingMore = value;

        if (value) {
            add(new NotificationData.Data());
            notifyItemInserted(notificationList.size());
        } else {
            notificationList.remove(notificationList.size() - 1);
            notifyItemRemoved(notificationList.size() - 1);
        }


    }

    static class NotificationVh extends RecyclerView.ViewHolder {
        private ItemNotificationsBinding binding;

        public NotificationVh(@NonNull ItemNotificationsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnNotificationItemSelectedCallback {
        void onUserProfileSelected(NotificationData.Data data);

        void onNotificationActionSelected(NotificationData.Data data);
    }
}
