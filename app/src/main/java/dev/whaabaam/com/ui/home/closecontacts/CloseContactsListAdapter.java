package dev.whaabaam.com.ui.home.closecontacts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.CloseContactsData;
import dev.whaabaam.com.data.model.other.MyConnectionsData;
import dev.whaabaam.com.data.model.other.NotificationData;
import dev.whaabaam.com.databinding.ItemConnectionsBinding;
import dev.whaabaam.com.databinding.ItemLoadMoreBinding;
import dev.whaabaam.com.databinding.ItemNotificationsBinding;
import dev.whaabaam.com.ui.base.ItemLoadMoreVH;
import dev.whaabaam.com.ui.home.myconnections.MyConnectionsAdapter;
import dev.whaabaam.com.ui.home.notifications.NotificationAdapter;

/**
 * @author rahul
 */
public class CloseContactsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CloseContactsData> list;
    private OnCloseContactsClickCallback callback;
    private boolean isLoadingMore = false;

    public CloseContactsListAdapter(OnCloseContactsClickCallback callback) {
        list = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case R.layout.item_connections:
                return new CloseContactsVH(ItemConnectionsBinding.inflate(((AppCompatActivity) viewGroup.getContext())
                        .getLayoutInflater(), viewGroup, false));
            default:
                return new ItemLoadMoreVH(ItemLoadMoreBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                        viewGroup, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof CloseContactsVH) {
            CloseContactsVH closeContactsVH = (CloseContactsVH) viewHolder;

            CloseContactsData data = list.get(closeContactsVH.getAdapterPosition());
            closeContactsVH.binding.setModel(data);

            closeContactsVH.binding.imageView10.setOnClickListener(view -> callback.onOptionsClick(data,
                    closeContactsVH.getAdapterPosition()));
            closeContactsVH.binding.userDataView.setOnClickListener(view -> callback.onNavigateToProfileClicked(data));
        }
    }

    public void addList(ArrayList<CloseContactsData> dataList) {
        for (CloseContactsData data : dataList) {
            add(data);
        }

    }

    public void add(CloseContactsData data) {
        list.add(data);
        notifyItemInserted(list.size());
    }

    public void clearList() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (isLoadingMore && position == list.size() - 1)
                ? R.layout.item_load_more : R.layout.item_connections;
    }

    public boolean getLoading() {
        return isLoadingMore;
    }

    public void setIsLoading(boolean value) {
        isLoadingMore = value;

        if (value) {
            add(new CloseContactsData());
            notifyItemInserted(list.size());
        } else {
            list.remove(list.size() - 1);
            notifyItemRemoved(list.size() - 1);
        }
    }

    static class CloseContactsVH extends RecyclerView.ViewHolder {
        private ItemConnectionsBinding binding;

        public CloseContactsVH(@NonNull ItemConnectionsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnCloseContactsClickCallback {
        void onOptionsClick(CloseContactsData data, int position);

        void onNavigateToProfileClicked(CloseContactsData data);
    }
}
