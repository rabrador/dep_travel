package com.example.han.newtravel30;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListContentActivity extends AppCompatActivity {
    private TextView textCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);

        init_view();

        // Receive request from Hot.java
        Bundle bundle = this.getIntent().getExtras();

        // To scroll text view
        textCont.setMovementMethod(new ScrollingMovementMethod());

        textCont.setText(bundle.getString("introduction"));
    }

    private void init_view() {
        textCont = (TextView) findViewById(R.id.textContent);
    }
}
