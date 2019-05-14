package com.example.android.androidlifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.android.androidlifecycle.util.CounterTracker;


public class ActivityDialog extends Activity {

    CounterTracker activityCounter = CounterTracker.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // to hide the title and display content in full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

    }

    public void finishDialog(View V){
        //onDestroy() method is called internally when calling finish()
        ActivityDialog.this.finish();
        Log.d("dialog","*******......inside finishDialog() method..........*****");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("Activity Lifecycle","........ActivityDialog onPause() called...........");
        activityCounter.setCounter();

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("lifecycle","..........onStart of ActivityDialog.........");

    }
}
