package com.example.takenote.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;


    public abstract NoteDao noteDao();


    //get singleton instance
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {

            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NoteDatabase.class,
                            "note_database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }


    //initialize data with callback on background thread
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //deprecated
//            new PopulateDbAsyncTask(instance).execute();

            NoteDao noteDao = instance.noteDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    noteDao.insert(new Note("Title 1", "Description 1"));
                    noteDao.insert(new Note("Title 2", "Description 2"));
                    noteDao.insert(new Note("Title 3", "Description 3"));
                    noteDao.insert(new Note("Title 4", "Description 5"));
                    noteDao.insert(new Note("Title 6", "Description 6"));
                    noteDao.insert(new Note("Title 7", "Description 7"));
                    noteDao.insert(new Note("Title 8", "Description 8"));
                    noteDao.insert(new Note("Title 9", "Description 9"));
                    noteDao.insert(new Note("Title 10", "Description 10"));
                }
            });
        }
    };













    //deprecated
    //obsolete method of running query on background threads

//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        private NoteDao noteDao;
//
//        private PopulateDbAsyncTask(NoteDatabase database){
//            noteDao = database.noteDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            noteDao.insert(new Note("Title 1", "Description 1"));
//            noteDao.insert(new Note("Title 2", "Description 2"));
//            noteDao.insert(new Note("Title 3", "Description 3"));
//            noteDao.insert(new Note("Title 4", "Description 5"));
//            noteDao.insert(new Note("Title 6", "Description 6"));
//            noteDao.insert(new Note("Title 7", "Description 7"));
//            noteDao.insert(new Note("Title 8", "Description 8"));
//            noteDao.insert(new Note("Title 9", "Description 9"));
//            noteDao.insert(new Note("Title 10", "Description 10"));
//            return null;
//        }
//    }

}
