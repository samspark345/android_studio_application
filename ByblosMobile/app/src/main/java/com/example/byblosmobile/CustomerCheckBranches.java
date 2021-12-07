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

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


//after customer search and select particular service
//then jump to here to check branch
//once picked branch in here
//then jump to CustomerMakeRequests
public class CustomerCheckBranches extends AppCompatActivity {
    String username;
    String service;

    DatabaseReference db;

    ListView listBranches;
    TextView servicen;
    List<String> branches;
    List<String> branchesname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.service = intent.getStringExtra("service");

        setContentView(R.layout.activity_customer_check_branches);

        db = FirebaseDatabase.getInstance().getReference("users").child("Employee");
        branches = new ArrayList<>();
        branchesname = new ArrayList<>();
        listBranches = (ListView) findViewById(R.id.branches);
        servicen = (TextView)findViewById(R.id.servicena);

        servicen.setText(service);
        getListOfBranches();

        listBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String b = branches.get(i);//selected branch
                showForm(b);
                return true;
            }
        });
    }



    //add adapter

    //getcurrent branches list

    @Override
    protected void onStart() {
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showBranches(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }
    private void showBranches(DataSnapshot dataSnapshot) {
        branches.clear();
        branchesname.clear();

        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) { // list of branch

            String b = postSnapshot.child("branchUsername").getValue().toString();
            String name = postSnapshot.child("branchName").getValue().toString();
            for(DataSnapshot branchservice:dataSnapshot.child(b).child("branchServices").getChildren()){
                String s = branchservice.getValue().toString();
                if(s.equals(service)){
                    branchesname.add(name);

                    branches.add(b);
                }


            }
        }
        //create adapter of branches
        ArrayAdapter servicesAdapater = new ArrayAdapter(this,android.R.layout.simple_list_item_1, branchesname);

        listBranches.setAdapter(servicesAdapater);

    }


    private void getListOfBranches() {
        db.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                branches.clear();


                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){ // list of branch

                    String branchName = postSnapshot.getKey();
                    //check whether the branch service has the service
                    DatabaseReference branchService = FirebaseDatabase.getInstance().getReference("users/Employee/"+branchName).child("branchServices");
                    branchService.addValueEventListener(new ValueEventListener() { //check each branch's offered service
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {

                                String s = postSnapshot.getValue().toString();
                                if(s == service ){
                                    branches.add(branchName);
                                    break;
                                }

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }
        });



    }

    //display overall service in system
    private void showForm(String branchName) {
        Intent switchToSumbit = new Intent(this, CustomerMakeRequests.class);
        switchToSumbit.putExtra("username", username);
        switchToSumbit.putExtra("branchName",branchName);
        switchToSumbit.putExtra("serviceName",service);
        startActivity(switchToSumbit);

    }

}
