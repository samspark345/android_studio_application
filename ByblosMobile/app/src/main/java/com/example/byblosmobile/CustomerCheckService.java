package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


//after customer search and select particular branch
//then jump to here to check service
//once picked service in here
//then jump to CustomerMakeRequests

public class CustomerCheckService extends AppCompatActivity {
    DatabaseReference databaseService;
    DatabaseReference databaseRequests;
    List<String> services; //all services for that branch
    ListView serviceList;
    String username;
    String branchName;
    String serviceName;

    ArrayAdapter<String> displayView;

    TextView branch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.branchName = intent.getStringExtra("branchName");


        setContentView(R.layout.activity_customer_check_service);

        branch = (TextView)findViewById(R.id.selectedBranch);
        serviceList = (ListView) findViewById(R.id.services);

        branch.setText(branchName);
        databaseRequests = FirebaseDatabase.getInstance().getReference("requests");
        databaseService = FirebaseDatabase.getInstance().getReference("users/Employee/").child(branchName).child("branchServices");
        services = new ArrayList<>();

        getListOfServices();

        serviceList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String service = services.get(i);
                showForm(service);
                return true;
            }
        });



    }



    private void getListOfServices() {
        //from users/Employee
        databaseService.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){ // loop through children of Employee
                    String s = ds.getKey();
                    services.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showService(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    private void showService(DataSnapshot dataSnapshot) {
        services.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){ // loop through children of Employee
            String branchService = ds.getValue().toString();
            services.add(branchService);
        }

        displayView = new ArrayAdapter(this, android.R.layout.simple_list_item_1,services);
        serviceList.setAdapter(displayView);
    }



    //display overall service in system
    private void showForm(String serviceName) {
        Intent switchToSumbit = new Intent(this, CustomerMakeRequests.class);
        switchToSumbit.putExtra("username", username);
        switchToSumbit.putExtra("branchName",branchName);
        switchToSumbit.putExtra("serviceName",serviceName);
        startActivity(switchToSumbit);

    }

    public void fromRequestToCustomerWelcome(View view){
        setContentView(R.layout.activity_customer_welcome_page);
    }

}
