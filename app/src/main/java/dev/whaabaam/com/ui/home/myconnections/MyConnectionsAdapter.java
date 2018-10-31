package dev.whaabaam.com.ui.home.myconnections;
/*
 * Created by RahulGupta on 27/8/18
 */

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.quickblox.chat.model.QBChatDialog;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.CloseContactsData;
import dev.whaabaam.com.data.model.other.MyConnectionsData;
import dev.whaabaam.com.databinding.ItemConnectionsBinding;
import dev.whaabaam.com.databinding.ItemLoadMoreBinding;
import dev.whaabaam.com.databinding.ItemMyConnectionsBinding;
import dev.whaabaam.com.ui.base.ItemLoadMoreVH;
import dev.whaabaam.com.ui.home.closecontacts.CloseContactsListAdapter;

public class MyConnectionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MyConnectionsData> connectionsData;
    private OnMyConnectionsClickCallback callback;
    private ArrayList<MyConnectionsData> copyList;
    private boolean isLoadingMore = false;

    public MyConnectionsAdapter(OnMyConnectionsClickCallback callback) {
        connectionsData = new ArrayList<>();
        copyList = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (i) {
            case R.layout.item_my_connections:
                return new MyConnectionVH(ItemMyConnectionsBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                        viewGroup, false));
            default:
                return new ItemLoadMoreVH(ItemLoadMoreBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                        viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof MyConnectionVH) {
            MyConnectionVH myConnectionVH = (MyConnectionVH) viewHolder;
            MyConnectionsData data = connectionsData.get(myConnectionVH.getAdapterPosition());
            myConnectionVH.binding.setModel(data);
            myConnectionVH.binding.userDataView.setOnClickListener(view -> callback.onNavigateToProfileClicked(data));
            myConnectionVH.binding.imageView10.setOnClickListener(view -> callback.onChatClicked(data));
        }
    }

    @Override
    public int getItemCount() {
        return connectionsData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (isLoadingMore && position == connectionsData.size() - 1)
                ? R.layout.item_load_more : R.layout.item_my_connections;
    }

    public void clear() {
        connectionsData.clear();
        copyList.clear();
        notifyDataSetChanged();
    }

    public void addConnectionList(ArrayList<MyConnectionsData> list) {
        copyList.addAll(list);
        for (MyConnectionsData data : list) {
            add(data);
        }
    }

    private void add(MyConnectionsData data) {
        connectionsData.add(data);
        notifyItemInserted(connectionsData.size());

    }

    public boolean getLoading() {
        return isLoadingMore;
    }

    public void setIsLoading(boolean value) {
        isLoadingMore = value;

        if (value) {
            add(new MyConnectionsData());
            notifyItemInserted(connectionsData.size());
        } else {
            connectionsData.remove(connectionsData.size() - 1);
            notifyItemRemoved(connectionsData.size() - 1);
        }
    }

    public void filterList(String input) {
        if (!TextUtils.isEmpty(input)) {
            connectionsData.clear();
            for (MyConnectionsData d : copyList) {
                if (d.getFirst_name().toLowerCase().contains(input.toLowerCase())
                        || d.getLast_name().contains(input.toLowerCase())) {
                    connectionsData.add(d);
                }
            }

        } else {
            connectionsData.clear();
            connectionsData.addAll(copyList);
        }
        notifyDataSetChanged();
    }

    static class MyConnectionVH extends RecyclerView.ViewHolder {
        private ItemMyConnectionsBinding binding;

        public MyConnectionVH(@NonNull ItemMyConnectionsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnMyConnectionsClickCallback {
        void onChatClicked(MyConnectionsData data);

        void onNavigateToProfileClicked(MyConnectionsData data);
    }
}
