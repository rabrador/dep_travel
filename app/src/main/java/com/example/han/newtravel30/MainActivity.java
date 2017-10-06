package com.example.han.newtravel30;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.han.newtravel30.AR.ARActivity;
import com.example.han.newtravel30.AR.useAPI;
import com.example.han.newtravel30.HotList.Hot;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.location.LocationListener;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private ImageButton imageButtonHot;
    private ImageButton imageButtonChat;
    private ImageButton imageButtonTraffic;
    private ImageButton imageButtonAR;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    public static Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        useAPI.preCheckPermission(MainActivity.this);

        new connectEDU().execute("http://140.115.197.16/?school=nptu&app=pingtungtravel&year=106");

        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-101894677-1");
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("submit")
                .build());


        init_view();
        registerBtnHandle();
    }

    private void registerBtnHandle() {
        imageButtonHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Hot.class);
                startActivity(intent);
            }
        });

        imageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (useAPI.checkNetWork(MainActivity.this)) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ChatSelectActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please check the device network connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageButtonTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (useAPI.checkNetWork(MainActivity.this)) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, TrafficActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please check the device network connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageButtonAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ARActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private void init_view() {
        imageButtonHot = (ImageButton) findViewById(R.id.imageButtonHot);
        imageButtonChat = (ImageButton) findViewById(R.id.imageButtonChat);
        imageButtonTraffic = (ImageButton) findViewById(R.id.imageButtonTraffic);
        imageButtonAR = (ImageButton) findViewById(R.id.imageButtonAR);
    }

    private class connectEDU extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
//                Log.d("TAG", "The response is :" + response);
            } catch (Exception e) {
                //Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }
}
