package dev.whaabaam.com.ui.home.closecontacts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import dev.whaabaam.com.data.model.other.CalendarTabData;
import dev.whaabaam.com.databinding.ItemCalendarTabBinding;

public class CalendarTabAdapter extends RecyclerView.Adapter<CalendarTabAdapter.CalendarTabVH> {
    private ArrayList<CalendarTabData> list;
    private OnCalendarTabSelectedCallback callback;

    CalendarTabAdapter(OnCalendarTabSelectedCallback callback) {
        this.list = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public CalendarTabAdapter.CalendarTabVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CalendarTabVH(ItemCalendarTabBinding.inflate(((AppCompatActivity) viewGroup.getContext())
                .getLayoutInflater(), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CalendarTabAdapter.CalendarTabVH viewHolder, int i) {
        viewHolder.binding.setModel(list.get(viewHolder.getAdapterPosition()));
        viewHolder.itemView.setOnClickListener(view -> callback.onCalendarTabSelected(list.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ArrayList<CalendarTabData> getList() {
        return list;
    }

    public void clearTail() {
        int size = list.size();
        if (size > 5) {
            list.subList(size - 5, size - 1).clear();
            notifyItemRangeRemoved(size - 5, 5);
        }
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void clearHead() {
        if (list.size() > 5) {
            list.subList(0, 4).clear();
            notifyItemRangeRemoved(0, 5);
        }
    }

    public void setSelection(int position) {
        callback.onCalendarTabSelected(list.get(position), position);
    }

    public CalendarTabData getItemAtPosition(int pos) {
        return list.get(pos);
    }

    public Date getLastDate() {
        return list.get(list.size() - 1).getDate();
    }

    public Date getFirstDate() {
        return list.get(0).getDate();
    }

    public void addList(ArrayList<CalendarTabData> aList) {
        for (CalendarTabData d : aList) {
            add(d);
        }
    }

    public void add(CalendarTabData data) {
        list.add(data);
        notifyItemInserted(list.size());
    }

    static class CalendarTabVH extends RecyclerView.ViewHolder {
        private ItemCalendarTabBinding binding;

        CalendarTabVH(@NonNull ItemCalendarTabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
