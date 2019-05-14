package com.example.android.androidservices;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DownloadService extends IntentService {

    DownloadActivity downloadActivity;
    public static final String TRANSACTION_COMPLETE = "Download successful";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     **/

    public DownloadService() {
        super("my_intent_service_thread");
    }

    //Perform any long running task here.
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d("Download File", "Started");

        //retrieve all strings from array.
        ArrayList<String> listOfUrls = intent.getStringArrayListExtra("urlList");

        for (int a = 0; a < listOfUrls.size(); a++) {
            Log.d("Downloading File: \t", listOfUrls.get(a));
            downloadFile(listOfUrls.get(a));

            //send update
            Intent intentUpdate = new Intent();
            intentUpdate.setAction("com.example.android.androidservices.DownloadActivity.UPDATE");
            intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
            intentUpdate.putExtra("downloadUpdate", listOfUrls.get(a));
            sendBroadcast(intentUpdate);

        }

        Intent intent1 = new Intent(TRANSACTION_COMPLETE);
        DownloadService.this.sendBroadcast(intent1);
    }

    public static String getFileNameFromUrl(URL url) {
        String urlPath = url.getPath();
        return urlPath.substring(urlPath.lastIndexOf('/') + 1);
    }

    protected void downloadFile(String fileOneUrl) {
        try {
            URL url1 = new URL(fileOneUrl);
            String fileName = getFileNameFromUrl(url1);

            File SDCardRoot = Environment.getExternalStorageDirectory();
            File file = new File(SDCardRoot, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = is.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "Download process started", Toast.LENGTH_LONG);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Download process destroyed", Toast.LENGTH_LONG);
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}


