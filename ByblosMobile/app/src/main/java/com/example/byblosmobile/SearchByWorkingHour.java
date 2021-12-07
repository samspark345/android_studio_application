package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

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
    List<String> timeslot;
    List<String> services;

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
        services = new ArrayList<>();
        databaseServices = FirebaseDatabase.getInstance().getReference("users").child("Employee");

        branches = new ArrayList<>();


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
                String a = timeslot.get(i);//looping through
                String b = branches.get(i);
                transfer(b);
            }
        });
    }



    public void goBackToCustomerMenu(View view) {
        Intent backToWelcome = new Intent(this, CustomerWelcomePage.class);
        backToWelcome.putExtra("username", username);
        backToWelcome.putExtra("roleName", roleName);
        startActivity(backToWelcome);
    }

    //@Override
    protected void onStart() {
        super.onStart();
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showAddress(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });


    }
    private void showAddress(DataSnapshot dataSnapshot) {
        timeslot.clear();
        branches.clear();


        for(DataSnapshot ds : dataSnapshot.getChildren()){ // loop through children of Employee
            String s = ds.child("branchUsername").getValue().toString();
            //String branchServices = ds.child("branchServices").getChildren();
            for(DataSnapshot time: ds.child("availability").getChildren()) {
                String end = time.child("endHour").getValue().toString();
                String start = time.child("startHour").getValue().toString();
                String date = time.child("day").getValue().toString();
                String display = date + " Start Hour: " + start + " End hour: " + end;
                if(Integer.valueOf(end)!=0 && Integer.valueOf(start)!=0){
                    timeslot.add(display);
                    branches.add(s);

                }


            }


            /*for(DataSnapshot service: ds.child("branchServices").getChildren()) {
                String serviceName = service.getValue().toString();
                services.add(serviceName);

            }*/

        }
        displayView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timeslot);
        listTimeslot.setAdapter(displayView);

    }

    private void transfer(String branchName) {
        Intent switchToSumbit = new Intent(this, CustomerCheckService.class);
        switchToSumbit.putExtra("username", username);
        switchToSumbit.putExtra("branchName",branchName);
        //switchToSumbit.putExtra("services",services);
        startActivity(switchToSumbit);

    }


}
