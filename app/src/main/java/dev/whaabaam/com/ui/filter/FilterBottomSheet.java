package dev.whaabaam.com.ui.filter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.LayoutFilterBinding;

public class FilterBottomSheet extends BottomSheetDialogFragment {
    static FilterBottomSheet filterBottomSheet;

    public static FilterBottomSheet getInstance() {
        if (filterBottomSheet == null) {
            filterBottomSheet = new FilterBottomSheet();
        }
        return filterBottomSheet;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final LayoutFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_filter, null, false);
        FilterViewModel viewModel = new FilterViewModel();
        binding.setViewModel(viewModel);
        viewModel.bindFilterList(binding.filterItemList);
        dialog.setContentView(binding.getRoot());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet);
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
            coordinatorLayout.getParent().requestLayout();
        });

        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
