package com.example.android.androidlifecycle.util;

import android.util.Log;

/**
 * Created by ravalin on 2/14/18.
 */

public class CounterTracker {

    public static int counter = 0;
    private static CounterTracker ourInstance = new CounterTracker();

    public static CounterTracker getInstance(){

        return ourInstance;
    }

    public int setCounter(){
        counter = counter+1;
        Log.d("counter","counter value = "+counter);

        return counter;
    }
}
