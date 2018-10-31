package dev.whaabaam.com.ui.myprofile;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.Relationships;
import dev.whaabaam.com.data.model.other.States;

public class RelationshipAdapter extends BaseAdapter {
    private ArrayList<Relationships> list;

    public RelationshipAdapter(ArrayList<Relationships> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text, viewGroup, false);
        ((TextView) view).setText(list.get(i).getName());
        return view;
    }

    public int getItemIndexById(int id) {
        int res = -1;
        for (Relationships item : list) {
            if (item.getId() == id) {
                res = list.indexOf(item);
                break;
            }
        }
        return res;
    }
}
