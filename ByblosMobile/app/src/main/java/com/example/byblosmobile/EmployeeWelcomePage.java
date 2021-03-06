package com.example.byblosmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeWelcomePage extends Activity {
    Button branchProfileButton;
    Button branchInfoButton;
    Button branchAvailabilityButton;
    Button branchRequestsButton;
    Button branchAddServiceButton;
    Button branchDeleteServiceButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome_page);

        branchProfileButton = (Button) findViewById(R.id.completeProfile);
        branchAvailabilityButton = (Button)findViewById(R.id.availability);
        branchRequestsButton = (Button)findViewById(R.id.dealServiceRequest);
        branchAddServiceButton = (Button)findViewById(R.id.addServiceBranch);
        branchDeleteServiceButton = (Button)findViewById(R.id.deleteServiceBranch);


        branchAvailabilityButton.setOnClickListener(new View.OnClickListener(){ //switch to service editing class
            @Override
            public void onClick(View view){
                openBranchServiceAvailability(view);
            }
        });
        branchRequestsButton.setOnClickListener(new View.OnClickListener(){ //switch to service editing class
            @Override
            public void onClick(View view){
                openBranchRequests(view);
            }
        });
        branchAddServiceButton.setOnClickListener(new View.OnClickListener(){ //switch to service editing class
            @Override
            public void onClick(View view){
                openBranchServiceAdd(view);
            }
        });

        branchDeleteServiceButton.setOnClickListener(new View.OnClickListener(){ //switch to service editing class
            @Override
            public void onClick(View view){
                openBranchServiceDelete(view);
            }
        });
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