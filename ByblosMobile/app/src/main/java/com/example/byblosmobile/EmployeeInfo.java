package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeInfo extends AppCompatActivity {
        String username;
        String roleName;


        //two ListView
        ListView currentService;
        ListView currentAvailability;
        //three TextView

        TextView branchName;
        TextView branchAddress;
        TextView branchNumber;

        DatabaseReference databaseReference;

        String name;
        String address;
        String phoneNumber;
        FirebaseDatabase db;

        //array for availability and branch service
        ArrayList<String> availability;
        ArrayList<String> serviceOffered;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_employee_checkmenu);

            Intent intent = getIntent();
            this.username = intent.getStringExtra("username");
            this.roleName = intent.getStringExtra("roleName");

            currentService = (ListView)findViewById(R.id.currentTimeSlotlist);
            currentAvailability = (ListView)findViewById(R.id.currentTimeSlotlist);

            branchName = (TextView) findViewById(R.id.branchname);
            branchAddress=(TextView) findViewById(R.id.branchaddress);
            branchNumber =(TextView)findViewById(R.id.branchnumber);

           // databaseReference = db.getInstance().getReference("users").child("Employee");
            availability = new ArrayList<>();


            databaseReference = FirebaseDatabase.getInstance().getReference();//get system data

            //Retrieve data from database to display
            databaseReference.child("users").child("Employee").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    showBranchInfo(dataSnapshot);
                    showServiceData(dataSnapshot);
                    showAvailabilityData(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    private void showBranchInfo(DataSnapshot dataSnapshot) {
        //Sets up the textViews
        for (DataSnapshot ds : dataSnapshot.getChildren()) { //dataSnapshot is get the employee username
            if (ds.getValue().toString().equals(username)) {
                 name = ds.child("branchName").getValue().toString();
                branchName.setText(name);

                address = ds.child("branchAddress").getValue().toString();
                branchAddress.setText(address);

                phoneNumber = ds.child("branchPhoneNumber").getValue().toString();
                branchNumber.setText(phoneNumber);

            }
        }
    }

    private void showServiceData(DataSnapshot dataSnapshot) {
       // serviceOffered.clear();//refresh data
        if(username != null){
            String branchName = username;
            // child("users").child("Employee").child(branchName).child("branchServices").
            for(DataSnapshot ds : dataSnapshot.child(branchName).child("branchService").getChildren()){
                serviceOffered.add(ds.getValue().toString());
            }

          //  ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, serviceOffered);
            //currentService.setAdapter(adapter);

        }



    }

    private void showAvailabilityData(DataSnapshot dataSnapshot) {
         //   availability.clear();//refresh data
            TimeSlot newTimeSlot;
        for(DataSnapshot ds : dataSnapshot.child("Employee").child(username).child("availability").getChildren()){
            //ds is at availability attribute, getChildren will give date
            String day = ds.child("day").getValue().toString();
            String endhour = ds.child("endHour").getValue().toString();
            String starthour = ds.child("startHour").getValue().toString();



            int end = Integer.valueOf(endhour); //get int value
            int start = Integer.valueOf(starthour);
            newTimeSlot = new TimeSlot(day,start,end);
            availability.add(newTimeSlot.toString());

        }
      //  ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, availability);
      //  currentAvailability.setAdapter(adapter);
    }
    //button click method
    public void goBackToEmployeeWelcome(View view){
        Intent backToWelcome = new Intent(this, EmployeeWelcomePage.class);
        startActivity(backToWelcome);
    }

    public void goToAddService(View view){
        Intent backToWelcome = new Intent(this, EmployeeServiceAdd.class);
        startActivity(backToWelcome);

    }

    public void goToDeleteService(View view){
        Intent backToWelcome = new Intent(this, EmployeeServiceDelete.class);
        startActivity(backToWelcome);

    }
    public void goToSetTimeSlot(View view){
        Intent backToWelcome = new Intent(this, EmployeeServiceAvailability.class);
        startActivity(backToWelcome);

    }


}
