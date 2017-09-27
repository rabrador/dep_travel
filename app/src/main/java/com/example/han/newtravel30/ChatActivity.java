package com.example.han.newtravel30;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class ChatActivity extends AppCompatActivity {

    static final String APP_ID = "57486";
    static final String AUTH_KEY = "PBO-8V3tD-QbRRE";
    static final String AUTH_SECRET = "fa6vaGq2KMB3UGa";
    static final String ACCOUNT_KEY = "BqJYGKHqzd7ccv2hyqb3";

    Button btnLogin, btnSignup;
    EditText edtUser, edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        initializePramework();

        btnLogin = (Button) findViewById(R.id.main_btnLogin);
        btnSignup = (Button) findViewById(R.id.main_btnSignup);

        edtUser = (EditText) findViewById(R.id.main_editLogin);
        edtPassword = (EditText) findViewById(R.id.main_editPassword);

        btnSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, SignUpActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String user = edtUser.getText().toString();
                final String password = edtPassword.getText().toString();

                QBUser qbUser = new QBUser(user, password);

                QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        Toast.makeText(getBaseContext(), "Login successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ChatActivity.this, ChatDialogActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("password", password);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Toast.makeText(getBaseContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initializePramework() {
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}
