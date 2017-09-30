package com.example.han.newtravel30;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.han.newtravel30.AR.useAPI;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListContentActivity extends AppCompatActivity {
    private TextView textCont;
    private ImageView imageContent;
    private ImageButton imgBtnMap;
    private ImageButton imgBtnFB;
    private String name;
    private String longitude;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);

        init_view();

        registerBtnHandle();

        // Receive request from Hot.java
        final Bundle bundle = this.getIntent().getExtras();

        name = bundle.getString("name");
        latitude = bundle.getString("Nlat");
        longitude = bundle.getString("Elong");

        // Load Image from URL
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap image = getImageFromURL(bundle.getString("imageURL"));

                // Display image
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageContent.setImageBitmap(image);
                    }
                });
            }
        }).start();

        // To scroll text view
        textCont.setMovementMethod(new ScrollingMovementMethod());

        textCont.setText(bundle.getString("introduction"));
    }

    private void registerBtnHandle() {
        imgBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location locat =useAPI.getMyLocation(ListContentActivity.this);
                String target = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr="
                                 + locat.getLatitude() + "," + locat.getLongitude();

                Uri uri = Uri.parse(target);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);

                startActivity(intent);
            }
        });

        imgBtnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://hashtag/" + name));
                    startActivity(intent);
                } catch (Exception e) { // has no Facebook app
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/hashtag/" + name));
                    startActivity(intent);
                }
            }
        });
    }

    private void init_view() {
        textCont = (TextView) findViewById(R.id.textContent);
        imageContent = (ImageView) findViewById(R.id.imageContent);
        imgBtnMap = (ImageButton) findViewById(R.id.imageButtonMap);
        imgBtnFB = (ImageButton) findViewById(R.id.imageButtonFBLike);
    }

    public Bitmap getImageFromURL(String src) {

        try {
            URL url = new URL(src);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream input = conn.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            e.printStackTrace();
            /* Default Image */
            return BitmapFactory.decodeResource(getResources(), R.drawable.pingtung);
        }
    }
}
