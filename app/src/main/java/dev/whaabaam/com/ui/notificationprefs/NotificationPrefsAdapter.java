package dev.whaabaam.com.ui.notificationprefs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dev.whaabaam.com.data.model.other.FilterData;
import dev.whaabaam.com.databinding.ItemNotificationPrefsBinding;

public class NotificationPrefsAdapter extends RecyclerView.Adapter<NotificationPrefsAdapter.NotificationPrefsVH> {

    private ArrayList<FilterData> dataList;
    private OnNotificationPrefsCallback callback;


    NotificationPrefsAdapter(OnNotificationPrefsCallback callback) {
        dataList = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public NotificationPrefsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NotificationPrefsVH(ItemNotificationPrefsBinding.inflate(((LayoutInflater.from(viewGroup.getContext())))
                , viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationPrefsVH notificationPrefsVH, int i) {
        FilterData data = dataList.get(notificationPrefsVH.getAdapterPosition());
        notificationPrefsVH.binding.setModel(data);
        notificationPrefsVH.binding.itemPrefs.setOnClickListener(view ->
                callback.onPrefsSelected(dataList.get(notificationPrefsVH.getAdapterPosition()),
                        notificationPrefsVH.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(ArrayList<FilterData> list) {
        for (FilterData data : list) {
            add(data);
        }
    }

    public void add(FilterData data) {
        dataList.add(data);
        notifyItemInserted(dataList.size());
    }

    public List<Integer> getCheckedPrefs() {
        List<Integer> checkedPrefs = new ArrayList<>();

        for (FilterData d : dataList) {
            if (d.getSelected().equals("true"))
                checkedPrefs.add(d.getId());
        }
        return checkedPrefs;
    }

    static class NotificationPrefsVH extends RecyclerView.ViewHolder {
        private ItemNotificationPrefsBinding binding;

        NotificationPrefsVH(@NonNull ItemNotificationPrefsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
