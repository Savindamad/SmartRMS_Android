package com.group12.smartrms.smartrms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends Activity {

    EditText etUsername, etPassword;
    Button bLogin;

    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bLogin = (Button) findViewById(R.id.bLogin);
    }

    public void Login(View view) {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        Intent intent = new Intent(LoginActivity.this, TableTypeActivity.class);

        LoginRequest loginRequest = new LoginRequest(this);
        loginRequest.setIntent(intent);
        loginRequest.execute(username, password);
        String userID = loginRequest.user_ID;

    }
}
