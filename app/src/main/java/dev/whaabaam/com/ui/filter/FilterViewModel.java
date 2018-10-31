package dev.whaabaam.com.ui.filter;

import android.databinding.BaseObservable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.michaelflisar.rxbus2.RxBus;

import java.util.ArrayList;

import dev.whaabaam.com.data.model.other.FilterData;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.filterPreferences;

/**
 * @author rahul
 */
public class FilterViewModel extends BaseObservable implements OnFilterItemSelectedCallback {
    private FilterListAdapter filterListAdapter;

    FilterViewModel() {
        filterListAdapter = new FilterListAdapter(getList(), this);
    }

    public static ArrayList<FilterData> getList() {
        if (filterPreferences.isEmpty()) {
            filterPreferences.addAll(CommonUtils.getRawFilterPreference());
        }
        return filterPreferences;
    }

    public void bindFilterList(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(filterListAdapter);
    }

    public View.OnClickListener onApplyFiltersClick() {
        return view -> {
            RxBus.get().withKey("applyFilter").send("");
            FilterBottomSheet.getInstance().dismiss();
        };
    }

    public View.OnClickListener onClearFilters() {
        return view -> {
            filterListAdapter.clearList();
            filterListAdapter.addToList(CommonUtils.getRawFilterPreference());
            filterListAdapter.notifyDataSetChanged();
        };
    }
    /*
     * BEGIN: onCLick listeners
     * */

    // close dialog
//    public View.OnClickListener onCloseDialogClick() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (FilterBottomSheet.getInstance((AppCompatActivity)context)).isVisible())
//                    FilterBottomSheet.getInstance().dismiss();
//            }
//        };
//    }

    //----------------END-----------------------



    /*
     * BEGIN: Callback for an filter item is clicked
     * */

    @Override
    public void onFilterItemSelected(FilterData data, int position) {
        if (data.isChecked()) {
            data.setChecked(false);
        } else {
            data.setChecked(true);
        }
        filterListAdapter.notifyItemChanged(position);
    }
    //--------------END-------------------------
}
