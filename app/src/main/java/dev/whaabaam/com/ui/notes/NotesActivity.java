package dev.whaabaam.com.ui.notes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.databinding.ActivityNotesBinding;

public class NotesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotesBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_notes);
        NotesViewModel notesViewModel = new NotesViewModel(this, Objects.requireNonNull(getIntent().getExtras())
                .getString("other_user_id"));
        binding.setViewModel(notesViewModel);
        notesViewModel.initNotesList(findViewById(R.id.notesList));
    }
}
