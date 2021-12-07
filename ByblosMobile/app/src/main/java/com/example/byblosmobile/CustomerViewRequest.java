package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerViewRequest extends AppCompatActivity {

    //show the list of customer made list --> long click to rate the
    String username;
    String roleName;


    //display all the requests made by this customer
    //long click service can jump to the rate branch page
    ListView requests;
    DatabaseReference databaseRequests;

    List<String> customerRequestList;
    List<String> branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_request);//set the connected interface

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        requests = (ListView) findViewById(R.id.customerRequestList);
        databaseRequests = FirebaseDatabase.getInstance().getReference("requests").child(username);//go to the database find customer's name
        //next child should be the number of the request

        customerRequestList = new ArrayList<>();
        branch = new ArrayList<>();
        getListOfRequests();
        requests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String r = customerRequestList.get(i);//looping through
                String branchn = branch.get(i);
                rate(branchn);
            }
        });
    }

    private void rate(String branch) {
        Intent switchPage = new Intent(this,CustomerRateBranch.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        switchPage.putExtra("branchName",branch);
        startActivity(switchPage);
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showCustomerRequests(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    private void showCustomerRequests(DataSnapshot dataSnapshot) {
        customerRequestList.clear();
        branch.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){ // loop through children of customer
            Request r = ds.getValue(Request.class);
            String name = ds.child("branchName").getValue().toString();
            customerRequestList.add(r.toString());
            branch.add(name);
        }


        ArrayAdapter show =  new ArrayAdapter(this, android.R.layout.simple_list_item_1,customerRequestList);
        requests.setAdapter(show);
    }


    private void getListOfRequests() {
        customerRequestList.clear();
        branch.clear();
        databaseRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Request r = ds.getValue(Request.class);
                    String name = ds.child("branchName").getValue().toString();
                    customerRequestList.add(r.toString());
                    branch.add(name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void backToCustomerMenu(View view){
        Intent switchPage = new Intent(this,WelcomePage.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        startActivity(switchPage);
    }



}
