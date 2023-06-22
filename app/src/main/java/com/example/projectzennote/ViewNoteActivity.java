package com.example.projectzennote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ViewNoteActivity extends AppCompatActivity {
    Realm realm;
    TextView createdTimeTV;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);


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
        Log.i("RealmViewHistory",realm.getPath());
        long createdTime=1687278423440L;//put in today's date 7am long--> no need to put in any extra info inside notification
        RealmResults<NoteModel> notes= realm.where(NoteModel.class).equalTo("createdTime",createdTime).findAll();
        //there will usually only be one item on the list, we will just get the first one if somehow there
        //is multiple records with the same createdTime (impossible)

        //text.setText(notes.get(0).getText());




    }
}