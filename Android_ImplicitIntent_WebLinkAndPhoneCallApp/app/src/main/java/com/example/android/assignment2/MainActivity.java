package com.example.android.assignment2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private boolean isTelephonyEnabled(){
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        return tm != null && tm.getSimState()==TelephonyManager.SIM_STATE_READY;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button launchButton = (Button) findViewById(R.id.launch_button);
        launchButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                EditText editText = (EditText) findViewById(R.id.url_value_et);
                                                String uriString = editText.getText().toString();
                                                Log.d("Weblink","is: "+uriString);
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
                                                startActivity(intent);
                                            }
                                        }
        );

        Button ringButton = (Button) findViewById(R.id.ring_button);
        ringButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Boolean check = isTelephonyEnabled();
                if (check) {
                    EditText editText = (EditText) findViewById(R.id.phone_value_et);
                    String phoneCall = "tel:"+editText.getText().toString();
                    Log.d("phone number", "=" + phoneCall);
                    Intent intent1 = new Intent(Intent.ACTION_CALL);
                    intent1.setData(Uri.parse(phoneCall));
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent1);
                }
                else
                {
                    Log.d("error","phone error");
                }
            }
        });
    }

    public void closeApp(View view) {

        this.finish();
    }
}
