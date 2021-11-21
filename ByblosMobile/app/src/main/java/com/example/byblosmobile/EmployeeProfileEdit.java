package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmployeeProfileEdit extends AppCompatActivity {
    Button backToEmployeeMain;
    EditText branchName;
    EditText branchAddress;
    EditText branchNumber;
    Button comfirmButton;
    Button cancelButton;

    DatabaseReference databaseReference;
    private FirebaseUser currUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        branchName = (EditText) findViewById(R.id.inputBranchNameField);
        branchAddress = (EditText) findViewById(R.id.inputBranchAddress);
        branchNumber = (EditText) findViewById(R.id.inputBranchNumber);

        backToEmployeeMain = findViewById(R.id.backToBranchMenuPage);
        comfirmButton = findViewById(R.id.comfirmEditProfile);
        cancelButton = findViewById(R.id.cancelEditProfile);

        //access to current account and employee data base
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child("Employee");
        currUser = FirebaseAuth.getInstance().getCurrentUser();


        //adding an onclicklistener to button
        backToEmployeeMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeProfileEdit.this, EmployeeWelcomePage.class);
                startActivity(i);
            }
        });

        //back to admin menu
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeProfileEdit.this, EmployeeWelcomePage.class);
                startActivity(i);
            }
        });


        comfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }

    private void update(){

        String nameContent = branchName.getText().toString();
        String addressContent = branchAddress.getText().toString();
        String numberContent = branchNumber.getText().toString();

        //validation
        if (nameContent.equals("") || addressContent.equals("") ||numberContent.equals("")) {
            setContentView(R.layout.activity_employee_profile);
            Toast.makeText(getApplicationContext(),"* content require to fill!",Toast.LENGTH_LONG).show();
        }else if (!addressContent.matches("[a-zA-Z0-9 ]+") || !numberContent.matches("[0-9]+")) {
            Toast.makeText(getApplicationContext(), "Phone number can only be 0 to 9, and Address can not include any symbols!", Toast.LENGTH_LONG).show();

        }else {
            //passed validation
            if (currUser != null) {
                String currId = currUser.getUid(); //return should be the name of the employee
                DatabaseReference currEmployee = databaseReference.child(currId);

                //edit branch name
                currEmployee.child("branchName").setValue(nameContent);
                //edit branch address
                currEmployee.child("branchAddress").setValue(addressContent);
                //edit branch phone number
                currEmployee.child("branchPhoneNumber").setValue(numberContent);

                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(EmployeeProfileEdit.this, EmployeeInfo.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(EmployeeProfileEdit.this, EmployeeInfo.class);
                startActivity(intent);
            }

        }
    }



}
