package com.example.byblosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {
    TextView usernameText;
    TextView roleNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Intent intent = getIntent();

        String username = intent.getStringExtra("username");
        String roleName = intent.getStringExtra("roleName");

        usernameText = findViewById(R.id.username);
        usernameText.setText(String.valueOf(username));

        roleNameText = findViewById(R.id.roleWelcome);
        roleNameText.setText(String.valueOf(roleName));

        LinearLayout visibility = findViewById(R.id.adminVisibility);
        if(String.valueOf(roleName).equals("Administrator")) {
            visibility.setVisibility(View.VISIBLE);
        }
    }



}