package com.example.android.androidlifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.android.androidlifecycle.util.CounterTracker;

/**
 * Created by ravalin on 2/12/18.
 */

public class ActivityB extends Activity{

    CounterTracker activityCounter = CounterTracker.getInstance();

    ActivityA activityA;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Log.d("Activity lifecycle", "........inside onCreate of ActivityB.........");
    }

    public void finishB(View view){
        Log.d("Activity lifecycle","*****........inside finishB().......******");

        Intent intent = new Intent(ActivityB.this,ActivityA.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Activity lifecycle", ".........onStart of ActivityB...........");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("Activity lifecycle","......onPause of ActivityB called.........");
        int counter=activityCounter.setCounter();

        Intent intent = new Intent(ActivityB.this,ActivityA.class);
        intent.putExtra("counter",counter);
        startActivity(intent);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.d("Activity lifecycle","...........onRestart of ActivityB.........");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("Activity lifecycle","..........onResume of ActivityB........");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("Activity lifecycle","..........onDestroy of ActivityB...........");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("Activity lifecycle",".........onStop of ActivityB......");

    }

}
