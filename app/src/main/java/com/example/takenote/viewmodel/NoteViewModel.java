package com.example.takenote.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.takenote.database.Note;
import com.example.takenote.repository.NoteRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mNoteRepository;
    private LiveData<List<Note>> notes;


    public NoteViewModel(@NonNull @NotNull Application application) {
        super(application);

        mNoteRepository = new NoteRepository(application);
        notes = mNoteRepository.getAllNotes();
    }

    public void insert(Note note){
        mNoteRepository.insert(note);
    }


    public void update(Note note){
        mNoteRepository.update(note);
    }

    public void delete(Note note){
        mNoteRepository.delete(note);
    }

    public LiveData<List<Note>> getAllNotes(){
        return mNoteRepository.getAllNotes();
    }
}
