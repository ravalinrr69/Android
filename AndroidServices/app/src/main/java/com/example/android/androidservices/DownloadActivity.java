package com.example.android.androidservices;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ravalin on 3/10/18.
 */

public class DownloadActivity extends Activity {
    Button downloadButton;
    EditText et_link1, et_link2, et_link3, et_link4, et_link5;
    BoundedServices myService;
    boolean isBound = false;
    Intent intent;
    public ArrayList<String> urlList;
    private MyBroadcastReceiver_Update myBroadcastReceiver_Update;

    //Push Notification Related
    private int currentNotificationID = 0;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_activity);
        downloadButton = (Button) findViewById(R.id.download_button);

        //Initialization.
        urlList = new ArrayList<String>();
        setDefaultUrls();

        //Register Broadcast Receiver to Received Download update
        myBroadcastReceiver_Update = new MyBroadcastReceiver_Update();
        IntentFilter intentFilter_update = new IntentFilter("com.example.android.androidservices.DownloadActivity.UPDATE");
        intentFilter_update.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadcastReceiver_Update, intentFilter_update);
        Log.d("DownloadActivity", "DownloadActivity:onCreate()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //un-register BroadcastReceiver
        unregisterReceiver(myBroadcastReceiver_Update);
    }

    public void setDefaultUrls() {
        urlList.add("https://www.cisco.com/c/dam/en_us/about/annual-report/2016-annual-report-full.pdf");
        urlList.add("https://www.cisco.com/c/dam/en_us/about/ac79/docs/innov/IoE_Economy.pdf");
        urlList.add("https://www.cisco.com/c/dam/en_us/solutions/industries/docs/gov/everything-for-cities.pdf");
        urlList.add("http://www.verizonenterprise.com/resources/reports/rp_DBIR_2017_Report_execsummary_en_xg.pdf");
        urlList.add("http://www.verizonenterprise.com/resources/reports/rp_DBIR_2017_Report_en_xg.pdf");

        et_link1 = (EditText) findViewById(R.id.pdf1_et);
        et_link2 = (EditText) findViewById(R.id.pdf2_et);
        et_link3 = (EditText) findViewById(R.id.pdf3_et);
        et_link4 = (EditText) findViewById(R.id.pdf4_et);
        et_link5 = (EditText) findViewById(R.id.pdf5_et);

        et_link1.setText(urlList.get(0));
        et_link2.setText(urlList.get(1));
        et_link3.setText(urlList.get(2));
        et_link4.setText(urlList.get(3));
        et_link5.setText(urlList.get(4));
    }

    public void getUrlsEntered() {
        urlList = new ArrayList<String>();
        urlList.add(0, et_link1.getText().toString());
        urlList.add(1, et_link2.getText().toString());
        urlList.add(2, et_link3.getText().toString());
        urlList.add(3, et_link4.getText().toString());
        urlList.add(4, et_link5.getText().toString());


    }

    public void download(View view) {
        getUrlsEntered();

        //Download Files Using IntentService.
        //Intent intent = new Intent(this, DownloadService.class);
        //intent.putStringArrayListExtra("urlList", (ArrayList<String>) urlList);
        //startService(intent);

        //Download Files using BoundedService
        intent = new Intent(this, BoundedServices.class);
        downloadFile(urlList);
    }


    private ServiceConnection MyConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundedServices.LocalBinder binder = (BoundedServices.LocalBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    protected void onStart() {
        super.onStart();
        //start service with binding for bounded services.
        intent = new Intent(this, BoundedServices.class);
        bindService(intent, MyConnection, Context.BIND_AUTO_CREATE);
    }

    public void downloadFile(ArrayList<String> urls) {

        for (int a = 0; a < urls.size(); a++) {
            Log.d("DownloadActivity", "BoundedServices Downloading File: " + urls.get(a));
            // Download file
            myService.downloadFile(urls.get(a));
            Toast toast = Toast.makeText(getApplicationContext(), "Download Complete: " + urls.get(a), Toast.LENGTH_LONG);
            toast.show();
            sendNotification(urls.get(a));
        }
    }

    private void sendNotification(String filename) {
        //Notification Channel
        CharSequence channelName = "SampleNotifications";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Sample", importance);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("AndroidServices")
                .setContentText("File Download Complete: " + filename);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;
        notificationManager.notify(notificationId, notification);
    }

    public class MyBroadcastReceiver_Update extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String update = intent.getStringExtra("downloadUpdate");
            Log.d("BroadcastReceiver OnReceive", "DownloadUpdate: " + update);
            Toast toast = Toast.makeText(getApplicationContext(), "Download Complete: " + update, Toast.LENGTH_LONG);
            toast.show();
            sendNotification(update);
        }
    }

}




