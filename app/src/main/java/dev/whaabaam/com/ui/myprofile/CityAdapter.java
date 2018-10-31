package dev.whaabaam.com.ui.myprofile;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.Cities;

/**
 * @author rahul
 */
public class CityAdapter extends BaseAdapter {
    private ArrayList<Cities> list;

    public CityAdapter(ArrayList<Cities> list) {
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

    public void addCities(ArrayList<Cities> a)
    {
        list.addAll(a);
        notifyDataSetChanged();
    }
    public void clear()
    {
        list.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text, viewGroup, false);
        ((TextView) view).setText(list.get(i).getName());
        return view;
    }
    public int getItemIndexById(int id) {
        for (Cities item : list) {
            if (item.getId() == id) {
                return list.indexOf(item);
            }
        }
        return -1;
    }
}
