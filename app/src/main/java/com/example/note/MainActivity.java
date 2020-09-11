package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter = null;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        getSupportActionBar().hide();

        /*
        if (notes.isEmpty()) {
            notes.add(new Note("Парикмахер", "Сделать прическу как у папы", "Понедельник", 2));
            notes.add(new Note("Магазин", "Купить изоленту", "Вторник", 3));
            notes.add(new Note("Универ", "Сдать реферат", "Пятница", 1));
            notes.add(new Note("Дом", "Сварить суп", "Четверг", 1));
            notes.add(new Note("Семья", "Помочь маме", "Вторник", 3));
            notes.add(new Note("Работа", "Доделать проект", "Суббота", 2));
        }
        for (Note note : notes) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }
        */

        getData();

        adapter = new NotesAdapter(notes);
        //Задаём расположение элементов
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);

        //Нажатия
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });

        //Свайпы
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Основное дествие при свайпе
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    private void remove(int position) {
        int id = notes.get(position).getId();
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        notes.clear();
        /* Выборка
        String selection = NotesContract.NotesEntry.COLUMN_PRIORITY + " < ?";
        String[] selectionArgs = new String[]{"3"};*/

        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null, NotesContract.NotesEntry.COLUMN_PRIORITY);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(title, description, dayOfWeek, priority, id);
            notes.add(note);
        }
        cursor.close();
    }
}