package com.example.byblosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onSignup(View view){
        Intent switchToSignup = new Intent(this, SignupActivity.class);
        startActivity(switchToSignup);
    }

    public void onLogin(View view){
        Intent switchToLogin = new Intent(this, LoginActivity.class);
        startActivity(switchToLogin);
    }
}