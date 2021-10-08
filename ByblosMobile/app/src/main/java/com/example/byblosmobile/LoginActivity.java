package com.example.byblosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.loginName);
        password = findViewById(R.id.loginPassword);
    }

    public void onSignup(View view){
        Intent switchToSignup = new Intent(this, SignupActivity.class);
        startActivity(switchToSignup);
    }
}