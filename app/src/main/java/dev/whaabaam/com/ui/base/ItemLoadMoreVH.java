package dev.whaabaam.com.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import dev.whaabaam.com.databinding.ItemLoadMoreBinding;

public class ItemLoadMoreVH extends RecyclerView.ViewHolder {
    private ItemLoadMoreBinding binding;

    public ItemLoadMoreVH(@NonNull ItemLoadMoreBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
