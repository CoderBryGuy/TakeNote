package com.example.takenote;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.takenote.MainActivity.*;

public class UpdateNoteActivity extends AppCompatActivity {

    EditText mNoteTitle, mNoteBody;
    Button cancelBtn, saveBtn;

    String mTitle, mBody;
    int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_update);

        mNoteTitle = findViewById(R.id.title_update_note);
        mNoteBody = findViewById(R.id.body_update_note);
        cancelBtn = findViewById(R.id.cancel_button_update_note);
        saveBtn = findViewById(R.id.save_button_update_note);

        getIntentData();


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateNoteActivity.this, "Nothing Upatede", Toast.LENGTH_SHORT).show();
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

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getIntExtra(NOTE_ID, -1);
            mTitle = intent.getStringExtra(NOTE_TITLE);
            mBody = intent.getStringExtra(NOTE_BODY);
        }

        mNoteTitle.setText(mTitle);
        mNoteBody.setText(mBody);
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