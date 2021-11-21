package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeDealRequest extends AppCompatActivity {


   
    ListView listViewRequests;

    DatabaseReference databaseR;
    FirebaseDatabase db;
    FirebaseAuth curr;

    List<String> branchRequestsList; //stores all the request name
    List<String> requestName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requestlist);

        listViewRequests=(ListView) findViewById(R.id.requestList);

        db = FirebaseDatabase.getInstance();
        curr = FirebaseAuth.getInstance();
        databaseR = db.getReference();


        branchRequestsList = new ArrayList<>();

        getCurrBranchRequest();

        listViewRequests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String request = branchRequestsList.get(i);
                viewRequestInfo(request);
                return true;
            }
        });
    }


    private void viewRequestInfo(String request) {
        Intent intent = new Intent(this, EmployeeViewRequest.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showRequestData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }
    //display all the request for that employee


    private void getCurrBranchRequest() { //check database get all the request in curr branch
        FirebaseUser user = curr.getCurrentUser();

        if(user!=null){
            String branchName = user.getUid();
            DatabaseReference userData = db.getReference("users/" + "Employee/"+ branchName);
            userData.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<List<String>> list= new GenericTypeIndicator<List<String>>() {};


                    branchRequestsList = snapshot.child("branchRequests").getValue(list);
                    //it contains all the request that branch receives

                    //database look like-->
                    /* Employee
                        BranchName
                            ...
                            branchRequests
                                request1(String)
                                request2(Strng)
                                request3(String)
                     */
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else{}

    }

    public void showRequestData(DataSnapshot dataSnapshot){
        branchRequestsList.clear();
        FirebaseUser user = curr.getCurrentUser();
        String branchName = user.getUid();
        for(DataSnapshot ds : dataSnapshot.child("Users").child("Employee").child(branchName).child("branchRequests").getChildren()){
           branchRequestsList.add(ds.getValue().toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, branchRequestsList);
        listViewRequests.setAdapter(adapter);
    }

}