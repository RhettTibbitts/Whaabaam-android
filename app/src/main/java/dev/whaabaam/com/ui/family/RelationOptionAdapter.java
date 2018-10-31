package dev.whaabaam.com.ui.family;
/*
 * Created by RahulGupta on 28/8/18
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dev.whaabaam.com.data.model.other.Relationships;

public class RelationOptionAdapter extends RecyclerView.Adapter<RelationOptionAdapter.RelationVH> {
    private ArrayList<Relationships> list;
    private OnRelationSelectedCallback callback;

    RelationOptionAdapter(ArrayList<Relationships> list, OnRelationSelectedCallback callback) {
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RelationVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RelationVH(LayoutInflater.from(viewGroup.getContext()).inflate(
                android.R.layout.simple_list_item_1, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RelationVH relationVH, int i) {
        relationVH.textView.setText(list.get(relationVH.getAdapterPosition()).getName());
        relationVH.textView.setOnClickListener(view -> {
            callback.onRelationSelected(list.get(relationVH.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RelationVH extends RecyclerView.ViewHolder {
        private TextView textView;

        RelationVH(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    public interface OnRelationSelectedCallback {
        void onRelationSelected(Relationships data);
    }
}
