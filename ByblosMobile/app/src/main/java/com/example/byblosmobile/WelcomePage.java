package com.example.byblosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {
    TextView usernameText;
    TextView roleNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();

        String username = intent.getStringExtra("username");
        String roleName = intent.getStringExtra("roleName");



//        LinearLayout visibility = findViewById(R.id.adminVisibility);
        if(String.valueOf(roleName).equals("Administrator")) {
//            visibility.setVisibility(View.VISIBLE);
            setContentView(R.layout.activity_admin_welcome_page);
        }else if(String.valueOf(roleName).equals("Employee")){
            setContentView(R.layout.activity_employee_welcome_page);
        }else{ setContentView(R.layout.activity_customer_welcome_page);}


        usernameText = findViewById(R.id.username);
        usernameText.setText(String.valueOf(username));

        roleNameText = findViewById(R.id.roleWelcome);
        roleNameText.setText(String.valueOf(roleName));

    }

    public void openServiceActivity(View view){
        Intent switchToServiceActivity = new Intent(this, ServiceActivityForAdmin.class);
        startActivity(switchToServiceActivity);
    }

    public void openEmployeeProfile(View view){
        Intent switchToEmployeeActivity = new Intent(this, EmployeeProfileEdit.class);
        startActivity(switchToEmployeeActivity);
    }

    public void openBranchInfo(View view){
        Intent switchToEmployeeActivity = new Intent(this,EmployeeInfo.class);
        startActivity(switchToEmployeeActivity);
    }

    public void openBranchServiceAdd(View view){
        Intent switchToEmployeeActivity = new Intent(this,EmployeeServiceAdd.class);
        startActivity(switchToEmployeeActivity);
    }

    public void openBranchServiceDelete(View view){
        Intent switchToEmployeeActivity = new Intent(this,EmployeeServiceDelete.class);
        startActivity(switchToEmployeeActivity);
    }

    public void openBranchServiceAvailability(View view){
        Intent switchToEmployeeActivity = new Intent(this,EmployeeServiceAvailability.class);
        startActivity(switchToEmployeeActivity);
    }

    public void openBranchRequests(View view){
        Intent switchToEmployeeActivity = new Intent(this,EmployeeDealRequest.class);
        startActivity(switchToEmployeeActivity);
    }
}