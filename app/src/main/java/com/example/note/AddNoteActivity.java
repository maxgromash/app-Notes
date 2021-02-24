package com.example.note;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.note.data.Note;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle = null;
    private EditText editTextDescription;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null )
            actionBar.hide();

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

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
            Note note = new Note(title, description, day, priority);
            mainViewModel.insertNote(note);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, R.string.warning_fill_fileds, Toast.LENGTH_SHORT).show();
    }

    private boolean isFilled(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }

}