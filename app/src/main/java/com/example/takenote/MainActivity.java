package com.example.takenote;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.takenote.viewmodel.NoteViewModel;
import com.example.takenote.database.Note;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel mNoteViewModel;

    RecyclerView mRecyclerView;
    NoteAdapter mAdapter;

    ActivityResultLauncher<Intent> mIntentActivityResultLauncherFor_AddNote;
    ActivityResultLauncher<Intent> mIntentActivityResultLauncherFor_UpdateNote;

    public final static String NOTE_TITLE = "note_title";
    public final static String NOTE_BODY = "note_body";
    public final static String NOTE_ID = "note_id";
    public final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to register activity
        registerActivityForAddNote();
        registerActivityForUpdateNote();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new NoteAdapter();
        mRecyclerView.setAdapter(mAdapter);


        mNoteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update the recyclerview
                mAdapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT & ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNoteViewModel.delete(mAdapter.getNotes(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        mAdapter.SetOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(Note note) {
                Intent intent = new Intent(MainActivity.this, UpdateNoteActivity.class);
                intent.putExtra(NOTE_ID, note.getId());
                intent.putExtra(NOTE_TITLE, note.getTitle());
                intent.putExtra(NOTE_BODY, note.getDescription());
                mIntentActivityResultLauncherFor_UpdateNote.launch(intent);
            }
        });
    }

    public void registerActivityForAddNote() {

        mIntentActivityResultLauncherFor_AddNote
                = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();

                        if (resultCode == RESULT_OK && data != null) {
                            //            assert data != null;
                            mNoteViewModel.insert(
                                    new Note(data.getStringExtra(NOTE_TITLE),
                                            data.getStringExtra(NOTE_BODY)));


                        }


                    }
                });
    }


    public void registerActivityForUpdateNote() {
        mIntentActivityResultLauncherFor_UpdateNote
                = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();

                        if (resultCode == RESULT_OK && data != null) {
                            //            assert data != null;
                            mNoteViewModel.insert(
                                    new Note(data.getStringExtra(NOTE_TITLE),
                                            data.getStringExtra(NOTE_BODY)));

                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_menu:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
// deprecated
//                startActivityForResult(intent, REQUEST_CODE);

                mIntentActivityResultLauncherFor_AddNote.launch(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
////            assert data != null;
//            mNoteViewModel.insert(
//                    new Note(data.getStringExtra(NOTE_TITLE),
//                            data.getStringExtra(NOTE_BODY)));
//
//
//        }
//    }
}