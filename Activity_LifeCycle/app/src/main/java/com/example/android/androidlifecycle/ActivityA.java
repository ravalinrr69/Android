package com.example.android.androidlifecycle;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import com.example.android.androidlifecycle.util.CounterTracker;

/**
 * Created by ravalin on 2/11/18.
 */

public class ActivityA extends Activity {

    CounterTracker activityCounter = CounterTracker.getInstance();
    TextView threadCounterTextview;
    int counter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        threadCounterTextview=(TextView)findViewById(R.id.thread_counter);

    }

    @Override
    public void onStart(){
    super.onStart();
    Log.d("lifecycle","..........onStart of ActivityA.........");

    }

    @Override
    public void onPause(){
    super.onPause();
    Log.d("Activity lifecycle",".......onPause of ActivityA called......");
        Intent intent = getIntent();//........


        counter=activityCounter.setCounter();
        int value = intent.getIntExtra("counter",counter);
        threadCounterTextview.setText(Integer.toString(value));
        Log.d("counter value", String.valueOf(value));

    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.d("lifecycle",".........onRestart of ActivityA..........");

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("lifecycle","..........onResume of ActivityA..........");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("lifecycle","............onDestroy of ActivityA...........");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("lifecycle","..........onStop of ActivityA.........");

    }

    public void startActivityB(View v) {
        Log.d("lifecycle", "****** .........oncLick startActivityB........... ******");
        Intent intent = new Intent(ActivityA.this, ActivityB.class);
        startActivity(intent);
    }

    public void startDialog(View view){
    Intent intent = new Intent(ActivityA.this,ActivityDialog.class);
    startActivity(intent);
    }

    public void closeApp(View view){
        ActivityA.this.finish();
    }

}
