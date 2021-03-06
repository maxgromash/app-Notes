package com.example.note.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.note.data.Note;

import java.util.List;

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY dayOfWeek")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE FROM notes")
    void deleteAllNotes();

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);
}
