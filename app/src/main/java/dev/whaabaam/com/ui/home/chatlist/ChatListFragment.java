package dev.whaabaam.com.ui.home.chatlist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.whaabaam.com.BR;
import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.FragmentChatListBinding;
import dev.whaabaam.com.quickblox.QuickbloxUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    private ChatListViewModel viewModel;

    public ChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentChatListBinding binding = FragmentChatListBinding.inflate(inflater, container, false);
        viewModel = new ChatListViewModel(getContext());
        binding.setVariable(BR.viewModel, viewModel);
        viewModel.initChatDialogList(binding.chatList);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.fetchAllChatDialogs();

    }
}
