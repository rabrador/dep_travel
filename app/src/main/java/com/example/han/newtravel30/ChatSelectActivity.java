package com.example.han.newtravel30;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class ChatSelectActivity extends AppCompatActivity {
    private ImageButton imageButtonMsg;
    private ImageButton imageButtonLogin;
    private ImageButton imageButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_select);

        init_view();
        registerBtnHandle();
    }

    private void init_view() {
        imageButtonMsg = (ImageButton) findViewById(R.id.imageButtonMessage);
        imageButtonLogin = (ImageButton) findViewById(R.id.imageButtonLogin);
        imageButtonRegister = (ImageButton) findViewById(R.id.imageButtonRegister);
    }

    private void registerBtnHandle() {
        imageButtonMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatSelectActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        imageButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatSelectActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        imageButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatSelectActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}
