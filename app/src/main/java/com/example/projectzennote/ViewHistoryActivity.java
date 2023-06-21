package com.example.projectzennote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class ViewHistoryActivity extends AppCompatActivity {
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

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
        RealmResults<NoteModel> notesList = realm.where(NoteModel.class).findAll().sort("createdTime", Sort.DESCENDING);
        Log.i("notesListLength",String.valueOf(notesList.size()));
        for (NoteModel noteModel:notesList){
            Log.i("noteModel String",noteModel.toString());
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(myAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<NoteModel>>() {
            @Override
            public void onChange(RealmResults<NoteModel> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });

    }
}