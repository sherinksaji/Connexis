package com.example.projectzennote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button addNoteBtn;
    Button viewHistoryBtn;
    Button viewNoteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addNoteBtn=findViewById(R.id.addNoteBtn);
        viewHistoryBtn=findViewById(R.id.viewHistoryBtn);
        viewNoteBtn=findViewById(R.id.viewNoteBtn);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,AddNoteActivity.class));
            }
        });

        viewHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ViewHistoryActivity.class));
            }
        });

        viewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ViewNoteActivity.class));
            }
        });
        viewNoteBtn.setVisibility(View.GONE);//make this visible when needed
    }


}