package com.example.han.newtravel30.AR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.han.newtravel30.R;

public class ARContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_content);

        Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("name").toString(), Toast.LENGTH_LONG).show();
    }
}
