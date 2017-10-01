package com.example.han.newtravel30;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.han.newtravel30.AR.ARActivity;
import com.example.han.newtravel30.HotList.Hot;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageButton bJump1;
    private ImageButton bJump2;
    private ImageButton bJump3;
    private ImageButton bJump4;
    private ImageButton bJump5;
    private ImageButton bJump6;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        JumpToHOt();
        JumpToNear();
        JumpToFriend();
        JumpToTraffic();
        JumpToFacebook();
        JumpToAR();
    }

    private void init_view() {
        bJump1 = (ImageButton) findViewById(R.id.imageButton);
        bJump2 = (ImageButton) findViewById(R.id.imageButton2);
        bJump3 = (ImageButton) findViewById(R.id.Btn_Chat);
        bJump4 = (ImageButton) findViewById(R.id.imageButton4);
        bJump5 = (ImageButton) findViewById(R.id.Btn_Facebook);
        bJump6 = (ImageButton) findViewById(R.id.Btn_AR);
    }


    public void JumpToHOt() {
        bJump1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Hot.class);
                startActivity(intent);
            }
        });
    }

    public void JumpToNear() {
        bJump2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void JumpToFriend() {
        bJump3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    public void JumpToTraffic() {
        bJump4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TrafficActivity.class);
                startActivity(intent);
            }
        });
    }

    public void JumpToFacebook() {
        bJump5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("facebook:/wall");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    public void JumpToAR() {
        bJump6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ARActivity.class);
                startActivity(intent);
            }
        });
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
                Log.d("TAG", "The response is :" + response);
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
