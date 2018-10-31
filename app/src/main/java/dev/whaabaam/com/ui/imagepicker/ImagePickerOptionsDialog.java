package dev.whaabaam.com.ui.imagepicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.databinding.LayoutImagePickerOptionBinding;

public class ImagePickerOptionsDialog extends BottomSheetDialogFragment implements ImagePickerViewModel.OnDialogOptionSelected {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutImagePickerOptionBinding binding = LayoutImagePickerOptionBinding.inflate(inflater, container, false);
        binding.setModel(new ImagePickerViewModel(getContext(), this,
                (AppConstants.IMAGE_PICKER_SOURCE) getArguments().get(AppConstants.IMAGE_SOURCE)));
        binding.setShowRemove(getArguments().getBoolean(AppConstants.SHOW_REMOVE_OPTION));
        return binding.getRoot();
    }

    @Override
    public void dismissDialog() {
        if (getDialog() != null && getDialog().isShowing())
            getDialog().dismiss();
    }
}
