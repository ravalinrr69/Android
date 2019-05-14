package com.example.android.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insertItem(View view) {
        Intent intent = new Intent(MainActivity.this, ItemActivity.class);
        startActivity(intent);
    }

    public void search(View view) {
        Intent intent = new Intent(this,SearchItem.class);
        startActivity(intent);
    }
}
