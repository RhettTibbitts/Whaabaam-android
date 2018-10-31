package dev.whaabaam.com.ui.filter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev.whaabaam.com.data.model.other.FilterData;
import dev.whaabaam.com.databinding.ItemFilterCbBinding;

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.FilterVH> {

    private ArrayList<FilterData> dataList;
    private OnFilterItemSelectedCallback callback;

    FilterListAdapter(ArrayList<FilterData> dataList, OnFilterItemSelectedCallback callback) {
        this.dataList = dataList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public FilterVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FilterVH(ItemFilterCbBinding.inflate(LayoutInflater.from(viewGroup.getContext())
                , viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterVH filterVH, int i) {
        filterVH.binding.setModel(dataList.get(filterVH.getAdapterPosition()));
        filterVH.binding.itemFilter.setOnClickListener(view -> callback.onFilterItemSelected(dataList.get(filterVH.getAdapterPosition()),
                filterVH.getAdapterPosition()));

    }

    public void clearList() {
        dataList.clear();
    }
    public void addToList(ArrayList<FilterData> a)
    {
        dataList.addAll(a);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class FilterVH extends RecyclerView.ViewHolder {
        private ItemFilterCbBinding binding;

        FilterVH(@NonNull ItemFilterCbBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
