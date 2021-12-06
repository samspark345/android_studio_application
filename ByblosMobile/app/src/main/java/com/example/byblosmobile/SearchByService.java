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

public class SearchByService extends AppCompatActivity {
    String username;
    String roleName;

    List<String> services;

    ListView listService;
    SearchView searchService;
    DatabaseReference databaseServices;

    ArrayAdapter<String> displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_by_service);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        listService = (ListView) findViewById(R.id.servicelistview);
        searchService = (SearchView) findViewById(R.id.serviceSearchView);
        services = new ArrayList<>();

        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        getListOfServices();


        searchService.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        listService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = services.get(i);//looping through
                pickedService(s);//passing service name
            }
        });
    }

    private void pickedService(String service) {
        //show list  service offered by this branch
        Intent switchToCheck = new Intent(this, CustomerCheckBranches.class);
        switchToCheck.putExtra("username", username);
        switchToCheck.putExtra("roleName", roleName);
        switchToCheck.putExtra("service",service);
        startActivity(switchToCheck);
    }
    //display all the service offered by system

    @Override
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
        services.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){ // loop through children of Employee

            String s = ds.child("name").getValue().toString();
            services.add(s);
        }

        displayView = new ArrayAdapter(this, android.R.layout.simple_list_item_1,services);
        listService.setAdapter(displayView);
    }


    private void getListOfServices() {
        //from users/Employee
        databaseServices.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){ // loop through children of Employee
                    String s = ds.child("name").getValue().toString();
                    services.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void goBackToCustomerMenu(View view){
        Intent backToWelcome = new Intent(this, CustomerWelcomePage.class);
        backToWelcome.putExtra("username", username);
        backToWelcome.putExtra("roleName", roleName);
        startActivity(backToWelcome);
    }
}
