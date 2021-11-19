package com.example.byblosmobile;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class SignupActivity extends AppCompatActivity {
    private boolean customerRole;
    private boolean gottenRole;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.signUpName);
        password = findViewById(R.id.signUpPassword);
        gottenRole = false;
    }

    public void onLogin(View view){
        Intent switchToLogin = new Intent(this, LoginActivity.class);
        startActivity(switchToLogin);
    }

    public void onRoleButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.customerRoleBtn:
                if (checked)
                    // customer role selected
                    customerRole = true;
                    gottenRole = true;
                    break;
            case R.id.employeeRoleBtn:
                if (checked)
                    // employee role selected
                    customerRole = false;
                    gottenRole = true;
                    break;
        }
    }

    public void onSignUpButtonClicked(View view){
        if (username.getText().length() == 0 || username.getText().length() == 0 || !gottenRole){ 
            return;
        }
        String usernameString = username.getText().toString();
        String role;
        String _password = password.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // User create;
        if (customerRole){
            role = "Customer";
        // create = new User(username.getText().toString(), password.getText().toString(), "Customer");
            DatabaseReference newUser = database.getReference("users/"+role+"/"+usernameString+"/password");
            newUser.setValue(password.getText().toString());
        }else{
            role = "Employee";
            Employee employee = new Employee(usernameString,_password,role);
            DatabaseReference newUser = database.getReference("users").child("Employee").child("usernameString");
            newUser.setValue(employee);
        }

        Intent switchToLogin = new Intent(this, LoginActivity.class);
        startActivity(switchToLogin);
    }
}