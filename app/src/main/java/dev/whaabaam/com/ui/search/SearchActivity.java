package dev.whaabaam.com.ui.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dev.whaabaam.com.R;
import dev.whaabaam.com.databinding.ActivitySearchBinding;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.utils.CommonUtils;

/**
 * @author rahul
 */
public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        SearchViewModel viewModel = new SearchViewModel(this);
        viewModel.initUsersList(binding.usersList);
        binding.setViewModel(viewModel);
        binding.editText4.setOnEditorActionListener((v, actionId, event) -> {

            switch (actionId) {
                case EditorInfo
                        .IME_ACTION_SEARCH:
                    if (!TextUtils.isEmpty(viewModel.searchText.get())) {
                        viewModel.requestSearchUserApi(false);
                    } else {
                        CommonUtils.toast(v.getContext(), "Enter valid search text.");
                    }
                    return true;
                default:
                    return false;
            }
        });
    }
}
