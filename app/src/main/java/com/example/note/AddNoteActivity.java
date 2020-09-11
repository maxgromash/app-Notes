package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle = null;
    private EditText editTextDescription;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinner = findViewById(R.id.spinnerDaysOfWeek);
        radioGroup = findViewById(R.id.radioGroupPriority);
    }

    public void onClickSaveNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String day = spinner.getSelectedItem().toString();
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        if (isFilled(title, description)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, day);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, R.string.warning_fill_fileds, Toast.LENGTH_SHORT).show();
    }

    private boolean isFilled(String title, String descripition) {
        return !title.isEmpty() && !descripition.isEmpty();
    }

}