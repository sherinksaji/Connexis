package com.example.projectzennote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddNoteActivity extends AppCompatActivity {
    Realm realm;
    Button sendDatePkrBtn;
    EditText textInput;
    MaterialButton savebtn;
    final Calendar selectedDate= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        sendDatePkrBtn=findViewById(R.id.sendDatePkrBtn);
        textInput=findViewById(R.id.textInput);
        savebtn=findViewById(R.id.savebtn);


        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .modules(new Module())
                .schemaVersion(1) // Must be bumped when the schema changes
                .migration(new Migration()) // Migration to run instead of throwing an exception
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.removeDefaultConfiguration();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();
        Log.i("RealmAddNote","abd pull "+realm.getPath());


        sendDatePkrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    public void datePicker(){

        // Get instance of calendar
        // mCalendar will be set to current/today's date
        final Calendar mCalendar = Calendar.getInstance();

        // Creating a simple calendar dialog.
        // It was 9 Aug 2021 when this program was
        // developed.
        final DatePickerDialog mDialog
                = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(
                            android.widget.DatePicker view,
                            int mYear, int mMonth, int mDay)
                    {
                        mCalendar.set(Calendar.YEAR, mYear);
                        mCalendar.set(Calendar.MONTH,
                                mMonth);
                        mCalendar.set(Calendar.DAY_OF_MONTH,
                                mDay);
                        sendDatePkrBtn.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);
                        selectedDate.clear();
                        selectedDate.set(Calendar.YEAR, mYear);
                        selectedDate.set(Calendar.MONTH,
                                mMonth);
                        selectedDate.set(Calendar.DAY_OF_MONTH,
                                mDay);
                        selectedDate.set(Calendar.HOUR_OF_DAY,7);
                        selectedDate.set(Calendar.MINUTE,0);
                        selectedDate.getTimeInMillis();
                        Log.i("selectedDate",selectedDate.toString());
                    }
                },
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
        // Changing mCalendar date from current to
        // some random MIN day 15/08/2021 15 Aug 2021
        // If we want the same current day to be the MIN
        // day, then mCalendar is already set to today and
        // the below code will be unnecessary
//        final int minDay = 15;
//        final int minMonth = 8;
//        final int minYear = 2021;
//        mCalendar.set(minYear, minMonth - 1, minDay);
        mCalendar.add(Calendar.DAY_OF_MONTH,1);
        mDialog.getDatePicker().setMinDate(
                mCalendar.getTimeInMillis());

        // Changing mCalendar date from current to
        // some random MAX day 20/08/2021 20 Aug 2021
//        final int maxDay = 20;
//        final int maxMonth = 8;
//        final int maxYear = 2021;
//        mCalendar.set(maxYear, maxMonth - 1, maxDay);

//        mDialog.getDatePicker().setMaxDate(
//                mCalendar.getTimeInMillis());

        // Display the calendar dialog
        mDialog.show();

        //https://www.geeksforgeeks.org/set-min-and-max-selectable-dates-in-datepicker-dialog-in-android/?ref=rp
    }


    public void saveNote(){
        String text = textInput.getText().toString();
        long createdTime = System.currentTimeMillis();

        realm.beginTransaction();
        NoteModel noteModel = realm.createObject(NoteModel.class);
        noteModel.setCreatedTime(createdTime);
        noteModel.setText(text);
        noteModel.setSendingTime(selectedDate.getTimeInMillis());
        noteModel.setMoodBefore(1);
        noteModel.setMoodAfter(5);
        realm.commitTransaction();
        Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
        finish();
    }
}