package com.example.note.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase database;
    private static final String DB_NAME = "notes2.db";
    private static final Object LOCK = new Object();

    //Метод получения БД
    public static NotesDatabase getInstance(Context context) {
        //Синхронизация для 2-х поток, чтобы сработал if дважды
        synchronized (LOCK) {
            if (database == null) {
                //allowMainThreadQueries() - только для тестов, для истольховани БД из главного потока. На практике не используется
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME).allowMainThreadQueries().build();
            }
        }
        return database;
    }

    //Доступ к Dao
    public abstract NotesDao notesDao();
}
