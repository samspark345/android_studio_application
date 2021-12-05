package com.example.byblosmobile;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;

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

            DatabaseReference basicInfo = database.getReference("users/"+role+"/"+usernameString+"/BasicInfo");
            basicInfo.child("firstName").setValue("");
            basicInfo.child("lastName").setValue("");
            basicInfo.child("email").setValue("");
            basicInfo.child("address").setValue("");
            basicInfo.child("birthday").setValue("");


        }else{
            role = "Employee";
            ArrayList<String> serviceList = new ArrayList();
            ArrayList<String> availability = new ArrayList();

            Employee employee = new Employee(usernameString, _password, role, "null", "null",null,null,null);
            DatabaseReference newUser = database.getReference("users/"+role+"/"+usernameString);
            newUser.setValue(employee);

            DatabaseReference passwordData = database.getReference("users/"+role+"/"+usernameString+"/password");
            passwordData.setValue(password.getText().toString());

            DatabaseReference dates = database.getReference("users/"+role+"/"+usernameString+"/availability");
            dates.child("Monday").child("day").setValue("Monday");
            dates.child("Monday").child("startHour").setValue(0);
            dates.child("Monday").child("endHour").setValue(0);
            dates.child("Tuesday").child("day").setValue("Tuesday");
            dates.child("Tuesday").child("startHour").setValue(0);
            dates.child("Tuesday").child("endHour").setValue(0);
            dates.child("Wednesday").child("day").setValue("Wednesday");
            dates.child("Wednesday").child("startHour").setValue(0);
            dates.child("Wednesday").child("endHour").setValue(0);
            dates.child("Thursday").child("day").setValue("Thursday");
            dates.child("Thursday").child("startHour").setValue(0);
            dates.child("Thursday").child("endHour").setValue(0);
            dates.child("Friday").child("day").setValue("Friday");
            dates.child("Friday").child("startHour").setValue(0);
            dates.child("Friday").child("endHour").setValue(0);
            dates.child("Saturday").child("day").setValue("Saturday");
            dates.child("Saturday").child("startHour").setValue(0);
            dates.child("Saturday").child("endHour").setValue(0);
            dates.child("Sunday").child("day").setValue("Sunday");
            dates.child("Sunday").child("startHour").setValue(0);
            dates.child("Sunday").child("endHour").setValue(0);


        }


        Intent switchToLogin = new Intent(this, LoginActivity.class);
        startActivity(switchToLogin);
    }
}