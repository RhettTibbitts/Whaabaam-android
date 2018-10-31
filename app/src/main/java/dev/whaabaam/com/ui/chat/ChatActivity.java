package dev.whaabaam.com.ui.chat;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBChatDialog;

import dev.whaabaam.com.BR;
import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.databinding.ActivityChatBinding;
import dev.whaabaam.com.quickblox.QuickbloxUtils;
import dev.whaabaam.com.ui.base.BaseActivity;

public class ChatActivity extends BaseActivity {
    private ActivityChatBinding binding;
    private ChatActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        viewModel = new ChatActivityViewModel(this,
                (QBChatDialog) getIntent().getSerializableExtra("chat_dialog"),
                getIntent().getIntExtra("participant_id", 0));
        viewModel.chatUserName.set(getIntent().getStringExtra("name"));
        binding.setViewModel(viewModel);
        viewModel.initChatList(binding.chatHistoryList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.isOnChatScreen = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.isOnChatScreen = false;
    }
}
