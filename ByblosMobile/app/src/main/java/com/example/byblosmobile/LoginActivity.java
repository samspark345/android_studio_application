package com.example.byblosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private Boolean validateUsername(){
        String tmp = username.getText().toString();
        if(tmp.isEmpty()){
            username.setError("Field cannot be empty");
            return false;
        }else{
            return true;
        }
    }

    private Boolean validatePassword(){
        String tmp = password.getText().toString();
        if(tmp.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else{
            return true;
        }

    }

    //Intent switchToWelcome = new Intent(this,WelcomePage.class);
    // startActivity(switchToWelcome);
    private void loginUser(View view){
        //Validate Login Info
        if(!validateUsername()|!validatePassword()){
            return;
        }else{
            checkAccount();
        }

    }

    private void checkAccount(){

    }

    public void onSignup(View view){
        Intent switchToSignup = new Intent(this, SignupActivity.class);
        startActivity(switchToSignup);
    }




}