package com.example.byblosmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private boolean checked = false;


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
    public void loginUser(View view){
        //Validate Login Info
        if(!validateUsername()||!validatePassword()){
            return;
        }
        if(!checked){
            password.setError("Check one of the buttons");
            return;
        }
        checkAccount();

    }

    private void checkAccount(){
        String enteredUsername = username.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();
        // String role = role.getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(role).child(enteredUsername).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String passwordData = String.valueOf(dataSnapshot.child("password").getValue());

                        if(enteredPassword.equals(passwordData)){
                            successfulLogin();
                        }else{
                            password.setError("incorrect password");
                        }


                    }else{
                        username.setError("user account doesn't exist!");
                    }
                }
            }
        });

    }

    public void successfulLogin(){
       Intent switchToWelcome = new Intent(this, WelcomePage.class);
       switchToWelcome.putExtra("roleName", role);
       switchToWelcome.putExtra("username", username.getText().toString().trim());
       startActivity(switchToWelcome);
    }


    public void onSignup(View view){
        Intent switchToSignup = new Intent(this, SignupActivity.class);
        startActivity(switchToSignup);
    }

    public void onRoleButtonClicked(View view) {
        // Is the button now checked?
        checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.customerRoleBtn:
                if (checked)
                    // customer role selected
                    role = "Customer";
                break;
            case R.id.employeeRoleBtn:
                if (checked)
                    // employee role selected
                    role = "Employee";
                break;
            case R.id.adminRoleBtn:
                if (checked)
                    // employee role selected
                    role = "Administrator";
                break;
        }
    }




}