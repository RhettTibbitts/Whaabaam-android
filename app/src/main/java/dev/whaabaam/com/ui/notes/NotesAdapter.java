package dev.whaabaam.com.ui.notes;
/*
 * Created by RahulGupta on 11/9/18
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.NotesData;
import dev.whaabaam.com.data.model.other.NotificationData;
import dev.whaabaam.com.databinding.ItemLoadMoreBinding;
import dev.whaabaam.com.databinding.ItemNotesBinding;
import dev.whaabaam.com.databinding.ItemNotificationsBinding;
import dev.whaabaam.com.ui.base.ItemLoadMoreVH;
import dev.whaabaam.com.ui.home.notifications.NotificationAdapter;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NotesData> list;
    private boolean isLoadingMore = false;

    public NotesAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (i) {
            case R.layout.item_notes:
                return new NotesVH(ItemNotesBinding.inflate(
                        LayoutInflater.from(viewGroup.getContext()), viewGroup, false
                ));
            default:
                return new ItemLoadMoreVH(ItemLoadMoreBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                        viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof NotesVH) {
            NotesVH notesVH = (NotesVH) viewHolder;
            notesVH.binding.setModel(list.get(notesVH.getAdapterPosition()));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (isLoadingMore && position == list.size() - 1)
                ? R.layout.item_load_more : R.layout.item_notes;
    }

    public void addNotes(ArrayList<NotesData> data) {
        for (NotesData d : data) {
            add(d);
        }
    }

    public void add(NotesData notesData) {
        list.add(notesData);
        notifyItemInserted(list.size() - 1);
    }

    public boolean getLoading() {
        return isLoadingMore;
    }

    public void setIsLoading(boolean value) {
        isLoadingMore = value;

        if (value) {
            add(new NotesData());
            notifyItemInserted(list.size());
        } else {
            list.remove(list.size() - 1);
            notifyItemRemoved(list.size() - 1);
        }


    }


    static class NotesVH extends RecyclerView.ViewHolder {
        private ItemNotesBinding binding;

        public NotesVH(@NonNull ItemNotesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
