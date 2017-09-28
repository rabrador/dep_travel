package com.example.han.newtravel30;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ListContentActivity extends AppCompatActivity {
    private TextView textCont;
    private ImageView imageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);

        init_view();

        // Receive request from Hot.java
        final Bundle bundle = this.getIntent().getExtras();

//        Toast.makeText(this, "imageURL" + bundle.getString("imageURL"), Toast.LENGTH_SHORT).show();

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

    private void init_view() {
        textCont = (TextView) findViewById(R.id.textContent);
        imageContent = (ImageView) findViewById(R.id.imageContent);
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
        }

        return null;
    }
}
