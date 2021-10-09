package com.example.byblosmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private String role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.loginName);
        password = findViewById(R.id.loginPassword);
        //initialize role

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
        String enteredUsername = username.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();
       // String role = role.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(role);
        Query checkUser = reference.orderByChild("username").equalTo(enteredUsername);
        //event listener
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String databasePassword = snapshot.child(enteredUsername).child("password").getValue(String.class);
                    if (databasePassword.equals(enteredPassword)) {
                        //sent name and role value to welcome page

                        successfulLogin();

                    } else {
                        password.setError("Wrong password!");
                    }

                } else {
                    username.setError("user account doesn't exist!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void successfulLogin(){
        Intent switchToLogin = new Intent(this, WelcomePage.class);
        startActivity(switchToLogin);
    }


    public void onSignup(View view){
        Intent switchToSignup = new Intent(this, SignupActivity.class);
        startActivity(switchToSignup);
    }

    public void onRoleButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.customerRoleBtn:
                if (checked)
                    // customer role selected
                    role = ((Button)findViewById(R.id.customerRoleBtn)).getText().toString();
                break;
            case R.id.employeeRoleBtn:
                if (checked)
                    // employee role selected
                    role = ((Button)findViewById(R.id.employeeRoleBtn)).getText().toString();
                break;
            case R.id.adminRoleBtn:
                if (checked)
                    // employee role selected
                    role = ((Button)findViewById(R.id.adminRoleBtn)).getText().toString();
                break;
        }
    }




}