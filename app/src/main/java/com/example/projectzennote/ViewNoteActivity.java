package com.example.projectzennote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ViewNoteActivity extends AppCompatActivity {
    Realm realm;
    TextView createdTimeTV;
    TextView text;

    final Calendar selectedDate= Calendar.getInstance();
    RealmResults<NoteModel> notes;
    ImageButton viewNextBtn;
    int counter=0;
    //  for the mood bar buttons
    private int rating = 0;
    private ImageView moodImage1;
    private ImageView moodImage2;
    private ImageView moodImage3;
    private ImageView moodImage4;
    private ImageView moodImage5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        text=findViewById(R.id.text);
        createdTimeTV=findViewById(R.id.createdTime);




        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .modules(new Module())
                .schemaVersion(1) // Must be bumped when the schema changes
                .migration(new Migration()) // Migration to run instead of throwing an exception
                .deleteRealmIfMigrationNeeded()
                .allowWritesOnUiThread(true)
                .build();
        Realm.removeDefaultConfiguration();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
        Log.i("RealmViewHistory",realm.getPath());
        selectedDate.set(Calendar.HOUR_OF_DAY,7);
        selectedDate.set(Calendar.MINUTE,0);
        selectedDate.set(Calendar.SECOND,0);
        selectedDate.set(Calendar.MILLISECOND, 0);
        long sendingTime=selectedDate.getTimeInMillis();//put in today's date 7am long--> no need to put in any extra info inside notification
        String sendingTimeString = String.format("%d", sendingTime);
        Log.i("sendingTimeVN",sendingTimeString);
        notes= realm.where(NoteModel.class).equalTo("sendingTime",sendingTime).findAll();
        Log.i("notesSize",Integer.toString(notes.size()));
        for (NoteModel note:notes){
            Log.i("noteStringVNnote",note.toString());
        }
       displayCurrentNote();
        //      START OF MOOD BAR CHUNK
        moodImage1 = findViewById(R.id.mood_image1);
        moodImage2 = findViewById(R.id.mood_image2);
        moodImage3 = findViewById(R.id.mood_image3);
        moodImage4 = findViewById(R.id.mood_image4);
        moodImage5 = findViewById(R.id.mood_image5);

        moodImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = 1;
                updateMoodForCurrentNote();
            }
        });

        moodImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = 2;
                updateMoodForCurrentNote();
            }
        });

        moodImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = 3;
                updateMoodForCurrentNote();
            }
        });

        moodImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = 4;
                updateMoodForCurrentNote();
            }
        });

        moodImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = 5;
                updateMoodForCurrentNote();
            }
        });
//      END OF MOOD BAR CHUNK

        viewNextBtn=findViewById(R.id.viewNextBtn);
        viewNextBtn.setImageResource(R.drawable.arrow);
        viewNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating=0;
                counter+=1;
                displayCurrentNote();
                updateMoodForCurrentNote();
                if (counter== notes.size()-1){
                    viewNextBtn.setVisibility(View.GONE);
                }
            }
        });

    }

    public void displayCurrentNote(){
        String sendingFormatedTime = DateFormat.getDateTimeInstance().format(notes.get(counter).getCreatedTime());
        text.setText(notes.get(counter).getText());
        createdTimeTV.setText("You wrote this note on "+sendingFormatedTime);

    }

    public void updateMoodForCurrentNote(){
        realm.executeTransaction(r -> {
            notes.get(counter).setMoodAfter(rating);
        });


        Log.i("updateMoodTest",notes.get(0).toString());
    }
}