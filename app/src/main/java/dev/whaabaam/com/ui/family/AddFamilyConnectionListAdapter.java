package dev.whaabaam.com.ui.family;
/*
 * Created by RahulGupta on 28/8/18
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import dev.whaabaam.com.data.model.other.FamilyData;
import dev.whaabaam.com.databinding.ItemAddFamilyMemberBinding;

import java.util.ArrayList;

import dev.whaabaam.com.data.model.other.ConnectionsAddFamilyData;

public class AddFamilyConnectionListAdapter extends RecyclerView.Adapter<AddFamilyConnectionListAdapter.AddFamilyVH> {


    private OnConnectionSelectedCallback callback;
    private ArrayList<ConnectionsAddFamilyData> list;
    private boolean isLoading = false;

    public AddFamilyConnectionListAdapter(OnConnectionSelectedCallback callback) {
        list = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public AddFamilyVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AddFamilyVH(ItemAddFamilyMemberBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddFamilyVH addFamilyVH, int i) {
        ConnectionsAddFamilyData data = list.get(addFamilyVH.getAdapterPosition());
        addFamilyVH.binding.setModel(data);
        addFamilyVH.binding.btnAdd.setOnClickListener(view -> callback.onAddConnectionToFamily(data, addFamilyVH.getAdapterPosition()));
        addFamilyVH.binding.userDataView.setOnClickListener(view -> callback.onViewProfile(data));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(ArrayList<ConnectionsAddFamilyData> dataList) {
        for (ConnectionsAddFamilyData data : dataList) {
            list.add(data);
            notifyItemInserted(list.size());
        }
    }

    public void add(ConnectionsAddFamilyData d) {
        list.add(d);
        notifyItemInserted(list.size() - 1);
    }

    public void remove(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public boolean getLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean value) {
        isLoading = value;
        if (value) {
            add(new ConnectionsAddFamilyData("9999"));
            notifyItemInserted(list.size() - 1);
        } else {
            list.remove(list.size() - 1);
            notifyItemRemoved(list.size() - 1);
        }
    }

    static class AddFamilyVH extends RecyclerView.ViewHolder {
        private ItemAddFamilyMemberBinding binding;

        AddFamilyVH(@NonNull ItemAddFamilyMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnConnectionSelectedCallback {
        void onViewProfile(ConnectionsAddFamilyData data);

        void onAddConnectionToFamily(ConnectionsAddFamilyData data, int pos);
    }
}
