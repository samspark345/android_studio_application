package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


//after customer search and select particular branch
//then jump to here to check service
//once picked service in here
//then jump to CustomerMakeRequests

public class CustomerCheckService extends AppCompatActivity {
    DatabaseReference databaseService;
    DatabaseReference databaseRequests;
    List<Service> services; //all services in system
    ListView serviceList;
    String username;
    String branchName;
    String serviceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.branchName = intent.getStringExtra("branchName");

        setContentView(R.layout.activity_customer_check_service);

        databaseRequests = FirebaseDatabase.getInstance().getReference("requests");
        databaseService = FirebaseDatabase.getInstance().getReference("users/Employee/branchName/services");
        services = new ArrayList<>();
        serviceList = (ListView) findViewById(R.id.services);

        serviceList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Service service = services.get(i);
                showForm(service.getName());
                return true;
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseService.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                services.clear();

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){

                    Service service = postSnapshot.getValue(Service.class);

                    services.add(service);

                    ServiceList servicesAdapater = new ServiceList(CustomerCheckService.this,services);

                    serviceList.setAdapter(servicesAdapater);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }
        });
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
