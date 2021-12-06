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

public class SearchByAddress extends AppCompatActivity {
    //search address --> find branch --> show list service offered by this branch --> click one of service
    //jump to the fill request form

    String username;
    String roleName;

    List<String> branches;
    List<String> address;
    List<String> serviceOffered; //By Selected Branch
    SearchView searchAddress;
    ListView listAddress;
    ArrayAdapter<String>displayView;

    DatabaseReference databaseServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_by_address);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        listAddress = (ListView) findViewById(R.id.addresslistview);
        searchAddress = (SearchView) findViewById(R.id.addressSearchView);
        branches = new ArrayList<String>();
        address = new ArrayList<>();
        databaseServices = FirebaseDatabase.getInstance().getReference("users/Employee");

        getListOfBranches();

        searchAddress.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        //getListOfBranches();
        listAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String a = address.get(i);//looping through
                String b = branches.get(i);
                pickedBranch(b);//get the branchName

            }
        });
    }

    private void getListOfBranches() {
        //from users/Employee
        databaseServices.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){ // loop through children of Employee
                    String branchname = ds.getKey();
                    String branchAddress = ds.child("branchAddress").getValue().toString();
                    address.add(branchAddress);
                    branches.add(branchname); //input the name of the Branch
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void pickedBranch(String branch) {
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
                showAddress(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    private void showAddress(DataSnapshot dataSnapshot) {
        branches.clear();
        address.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){ // loop through children of Employee
            String branchname = ds.getKey();
            String branchAddress = ds.child("branchAddress").getValue().toString();
            address.add(branchAddress);
            branches.add(branchname); //input the name of the Branch
        }

         displayView = new ArrayAdapter(this, android.R.layout.simple_list_item_1,address);
         listAddress.setAdapter(displayView);
        }

    }






