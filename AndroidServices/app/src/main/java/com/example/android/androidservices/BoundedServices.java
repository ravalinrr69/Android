package com.example.android.androidservices;

/**
 * Created by ravalin on 3/19/18.
 */

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BoundedServices extends Service {
    public BoundedServices() {
    }

    private Binder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        BoundedServices getService() {
            return BoundedServices.this;
        }
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
    }

    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return binder;
    }

    public void downloadFile(final String urlStr) {
        BackTask backTask = new BackTask();
        backTask.execute(urlStr);
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }

    // A separate process to download a file from internet
    private class BackTask extends AsyncTask<String, Integer, Void> {
        protected void onPreExecute() {
        }

        public String getFileNameFromUrl(URL url) {
            String urlPath = url.getPath();
            return urlPath.substring(urlPath.lastIndexOf('/') + 1);
        }

        protected Void doInBackground(String... params) {
            URL url;
            int count;
            HttpURLConnection con = null;
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                url = new URL(params[0]);
                try {
                    con = (HttpURLConnection) url.openConnection();
                    is = con.getInputStream();
                    String fileName = getFileNameFromUrl(url);
                    File SDCardRoot = Environment.getExternalStorageDirectory();
                    File file = new File(SDCardRoot, fileName);
                    fos = new FileOutputStream(file);
                    int lenghtOfFile = con.getContentLength();
                    byte data[] = new byte[1024];
                    while ((count = is.read(data)) != -1) {
                        fos.write(data, 0, count);
                    }
                    fos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    String error_Message = e.getMessage();
                    if (error_Message != null) {
                        Log.e("BoundService", error_Message);
                    }

                } finally {
                    if (is != null)
                        try {
                            is.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    if (fos != null)
                        try {
                            fos.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                }


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), "Download finished", Toast.LENGTH_LONG).show();
        }

    }
}