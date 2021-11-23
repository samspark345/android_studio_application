package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceAvailability extends AppCompatActivity {
    String username;
    String roleName;

    //three spinner to check day,start hour, end hour
    Spinner spinnerday;
    Spinner spinnerStart;
    Spinner spinnerEnd;


    ArrayAdapter<CharSequence> adapterday;
    ArrayAdapter<CharSequence> adapterStart;
    ArrayAdapter<CharSequence> adapterEnd;

    String day;
    int startHour;
    int endHour;
    ArrayList<TimeSlot> availabilityList;

    //Firebase
    DatabaseReference databaseService;
    DatabaseReference databaseReference;
    FirebaseDatabase db;
    FirebaseAuth curr;
    DatabaseReference user;
    String branchName;

    List<TimeSlot> listOfTimeSlot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_timeslot);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        db = FirebaseDatabase.getInstance();
        curr = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child("Employee");
        user = databaseReference.child(username);

        //https://developer.android.com/guide/topics/ui/controls/spinner#java reference
        spinnerday = findViewById(R.id.day);
        adapterday = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        adapterday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerStart = findViewById(R.id.startHour);
        adapterStart = ArrayAdapter.createFromResource(this, R.array.startHour, android.R.layout.simple_spinner_item);
        adapterStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEnd = findViewById(R.id.endHour);
        adapterEnd = ArrayAdapter.createFromResource(this, R.array.endHour, android.R.layout.simple_spinner_item);
        adapterEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerday.setAdapter(adapterday);
        spinnerStart.setAdapter(adapterStart);
        spinnerEnd.setAdapter(adapterEnd);
        if(user != null){
            branchName = user.toString();//get branch name
        }

        //Sets the day
        spinnerday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //start hour
        spinnerStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startHour = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //end hour
        spinnerEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endHour = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //click comfirmed button --> this method has implemented on xml onClick 
    public void onClickConfirmTime(View view){
        //Checks if the end time is before the start time
        if (endHour < startHour){
            Toast.makeText(getApplicationContext(), "Invalid Time, Please Check again!", Toast.LENGTH_SHORT).show();

        }
        else {
            TimeSlot newTime = new TimeSlot(day,startHour,endHour);
            databaseService = db.getReference("users/Employee/" + username).child("availability").child(newTime.getDay());
            //if there has availability then add, if not create a new attribute
            databaseService.child("startHour").setValue(newTime.getStartHour());
            databaseService.child("endHour").setValue(newTime.getEndHour());

            //the database should look like:
            /* users
                Employee
                    branchName
                    password
                    address
                    phoneNumber
                    availability
                        Monday
                            day
                            startHour
                            endHour
                         ...
             */

            Toast.makeText(getApplicationContext(), "Add Timeslot successfully", Toast.LENGTH_SHORT).show();

        }
    }

    public void goBackToEmployeeWelcome(View view){
        Intent backToWelcome = new Intent(this, WelcomePage.class);
        backToWelcome.putExtra("username", username);
        backToWelcome.putExtra("roleName", roleName);
        startActivity(backToWelcome);
    }

    public void goBackToEmployeeInfo(View view){
        Intent i = new Intent(this, EmployeeInfo.class);
        i.putExtra("username", username);
        i.putExtra("roleName", roleName);
        startActivity(i);
    }

}


