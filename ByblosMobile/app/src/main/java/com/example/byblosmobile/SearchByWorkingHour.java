package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchByWorkingHour extends AppCompatActivity {

    String username;
    String roleName;

    List<String> branches;
    List<TimeSlot> timeslot;

    ListView listTimeslot;
    SearchView searchTimeslot;
    DatabaseReference databaseServices;

    ArrayAdapter<String> displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_by_workinghour);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        listTimeslot = (ListView) findViewById(R.id.workinghourlistview);
        searchTimeslot = (SearchView) findViewById(R.id.workinghourSearchView);
        branches = new ArrayList<String>();
        timeslot = new ArrayList<>();
        databaseServices = FirebaseDatabase.getInstance().getReference("users/Employee");

        searchTimeslot.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                displayView.getFilter().filter(s);
                return false;
            }
        });


        listTimeslot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TimeSlot a = timeslot.get(i);//looping through
                String b = branches.get(i);
                pickedTimeSlot(b);
            }
        });
    }

    private void pickedTimeSlot(String branch) {
        //show list  service offered by this branch
        Intent switchToCheck = new Intent(this, CustomerCheckService.class);
        switchToCheck.putExtra("username", username);
        switchToCheck.putExtra("roleName", roleName);
        switchToCheck.putExtra("branchName",branch);
        startActivity(switchToCheck);
    }


    public void goBackToCustomerMenu(View view){
        Intent backToWelcome = new Intent(this, CustomerWelcomePage.class);
        backToWelcome.putExtra("username", username);
        backToWelcome.putExtra("roleName", roleName);
        startActivity(backToWelcome);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showBranches(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    private void showBranches(DataSnapshot dataSnapshot) {
        getListOfTimeslot(dataSnapshot);
        displayView = new ArrayAdapter(this, android.R.layout.simple_list_item_1,timeslot);
        listTimeslot.setAdapter(displayView);
    }


    private void getListOfTimeslot(DataSnapshot dataSnapshot) {
        branches.clear();
        timeslot.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){ // loop through children of Employee
            String branchname = ds.toString();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/Employee/" + branchname).child("availability");

            //loop all the timeslot contain in this branch
           db.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {

                       TimeSlot timeSlot = postSnapshot.getValue(TimeSlot.class);

                       if (timeSlot.getEndHour() != 0 && timeSlot.getStartHour() != 0) {
                           timeslot.add(timeSlot);
                           branches.add(branchname); //input the name of the Branch
                       }
                   }
               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
        }

    }
}
