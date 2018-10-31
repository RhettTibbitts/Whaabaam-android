package dev.whaabaam.com.ui.family;
/*
 * Created by RahulGupta on 28/8/18
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michaelflisar.rxbus2.RxBus;

import java.util.Objects;

import dev.whaabaam.com.data.model.other.Relationships;
import dev.whaabaam.com.databinding.LayoutRelationOptionBinding;

public class RelationOptionsSheet extends BottomSheetDialogFragment implements RelationOptionAdapter.OnRelationSelectedCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LayoutRelationOptionBinding binding = LayoutRelationOptionBinding.inflate(inflater, container, false);

        RelationOptionAdapter adapter = new RelationOptionAdapter(
                Objects.requireNonNull(getArguments()).getParcelableArrayList("relations"), this);

        binding.relationList.setHasFixedSize(true);
        binding.relationList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.relationList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        binding.relationList.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onRelationSelected(Relationships data) {
        getDialog().dismiss();
        new Handler().postDelayed(() -> RxBus.get().withKey("relation").send(data), 1000);

    }
}
