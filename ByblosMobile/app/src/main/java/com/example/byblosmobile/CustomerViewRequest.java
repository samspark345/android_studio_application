package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerViewRequest extends AppCompatActivity {
    String username;
    String roleName;


    //display all the requests made by this customer
    //long click service can jump to the rate branch page
    ListView requests;
    DatabaseReference databaseRequests;

    List<String> customerRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_request);//set the connected interface

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        requests = (ListView) findViewById(R.id.customerRequestList);
        databaseRequests = FirebaseDatabase.getInstance().getReference("requests/"+username);//go to the database find customer's name
        //next child should be the number of the request

        customerRequestList = new ArrayList<>();

        getCurrCustomerRequests();

    }

    private void getCurrCustomerRequests() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("requests" + username);
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> list= new GenericTypeIndicator<List<String>>() {};

                customerRequestList = snapshot.child("branchServices").getValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    public void backToCustomerMenu(View view){
        Intent switchPage = new Intent(this,CustomerWelcomePage.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        startActivity(switchPage);
    }



}
