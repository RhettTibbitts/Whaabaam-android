package dev.whaabaam.com.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBChatDialog;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.quickblox.QuickbloxUtils;
import dev.whaabaam.com.ui.chat.ChatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));
        }
        MyApplication.context = this;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard();
    }

    public void launchIntent(Class<?> destination, Bundle dataToPass, boolean shouldFinish) {
        Intent intent = new Intent(this, destination);
        if (dataToPass != null)
            intent.putExtras(dataToPass);
        startActivity(intent);
        overridePendingTransition(R.anim.entry, R.anim.exit);

        if (shouldFinish)
            finish();
    }

    public void launchChatActivity(QBChatDialog chatDialog, int participantID, String name) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("chat_dialog", chatDialog);
        intent.putExtra("participant_id", participantID);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }
}
