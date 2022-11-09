package com.example.takenote;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.takenote.database.Note;
import com.example.takenote.viewmodel.NoteViewModel;

import static com.example.takenote.MainActivity.NOTE_BODY;
import static com.example.takenote.MainActivity.NOTE_TITLE;

public class AddNoteActivity extends AppCompatActivity {

    EditText mNoteTitle, mNoteBody;
    Button cancelBtn, saveBtn;

    NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_note);



//        mNoteViewModel = new NoteViewModel();

        mNoteTitle = findViewById(R.id.title_add_note);
        mNoteBody = findViewById(R.id.body_add_note);
        cancelBtn = findViewById(R.id.cancel_button_add_note);
        saveBtn = findViewById(R.id.save_button_add_note);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNoteActivity.this, "Nothing Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();

            }
        });
    }

    private void saveNote() {
//        Note note = new Note(noteTitle.getText().toString(), noteBody.getText().toString());

        String noteTitle = mNoteTitle.getText().toString();
        String noteBody = mNoteBody.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(NOTE_TITLE, noteTitle);
        intent.putExtra(NOTE_BODY, noteBody);
        setResult(RESULT_OK, intent);
        finish();

    }
}