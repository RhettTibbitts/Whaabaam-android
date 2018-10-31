package dev.whaabaam.com.ui.family;
/*
 * Created by RahulGupta on 27/8/18
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.FamilyData;
import dev.whaabaam.com.data.model.other.NotificationData;
import dev.whaabaam.com.databinding.ItemFamilyMemberBinding;
import dev.whaabaam.com.databinding.ItemLoadMoreBinding;
import dev.whaabaam.com.ui.base.ItemLoadMoreVH;

public class FamilyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FamilyData> familyList;
    private OnFamilyListActionsCallback callback;
    private boolean isLoadingMore = false;

    public FamilyListAdapter(OnFamilyListActionsCallback callback) {
        this.callback = callback;
        familyList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case R.layout.item_family_member:
                return new FamilyListVH(ItemFamilyMemberBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                        viewGroup, false));
            default:
                return new ItemLoadMoreVH(ItemLoadMoreBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                        viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof FamilyListVH) {
            FamilyListVH familyListVH = (FamilyListVH) viewHolder;
            FamilyData data = familyList.get(familyListVH.getAdapterPosition());

            familyListVH.binding.setModel(data);

            familyListVH.binding.realationSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                if (!b) {
                    callback.onFamilyRemoved(data, familyListVH.getAdapterPosition());
                }
            });
            familyListVH.binding.userDataView.setOnClickListener(view -> callback.onFamilySelected(data));
        }
    }

    @Override
    public int getItemCount() {
        return familyList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (isLoadingMore && familyList.get(position).getId().equals("9999"))
                ? R.layout.item_load_more : R.layout.item_family_member;
    }

    public void addFamilyList(ArrayList<FamilyData> list) {
        for (FamilyData d : list) {
            add(d);
        }
    }

    public void add(FamilyData data) {
        familyList.add(data);
        notifyItemInserted(familyList.size());
    }

    public void remove(int position) {
        familyList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        familyList.clear();
        notifyDataSetChanged();
    }

    public boolean getLoading() {
        return isLoadingMore;
    }

    public void setIsLoading(boolean value) {
        isLoadingMore = value;

        if (value) {
            add(new FamilyData("9999"));
            notifyItemInserted(familyList.size() - 1);
        } else {
            familyList.remove(familyList.size() - 1);
            notifyItemRemoved(familyList.size() - 1);
        }
    }

    static class FamilyListVH extends RecyclerView.ViewHolder {
        private ItemFamilyMemberBinding binding;

        public FamilyListVH(@NonNull ItemFamilyMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnFamilyListActionsCallback {
        void onFamilySelected(FamilyData data);

        void onFamilyRemoved(FamilyData data, int position);
    }
}
