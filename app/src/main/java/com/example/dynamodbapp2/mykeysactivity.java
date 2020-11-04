package com.example.dynamodbapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class mykeysactivity extends AppCompatActivity {

    ListView listView;
    Myadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mykeysactivity);

        listView = findViewById(R.id.mykeyslistview);
        myadapter = new Myadapter(mykeysactivity.this);

        listView.setAdapter(myadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HoldPosition.position=position;
            }
        });
    }
}