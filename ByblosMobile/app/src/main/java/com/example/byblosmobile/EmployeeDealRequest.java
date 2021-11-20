package com.example.byblosmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
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

    DatabaseReference databaseService;
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
        branchRequestsList = new ArrayList<>();

        getCurrBranchRequest();

        listViewRequests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String request = branchRequestsList.get(i);
                showEditRequestsDialog(request);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth fbAuthRef = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fbAuthRef.getCurrentUser();
        DatabaseReference dbRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dbRequestsRef = dbRootRef.child("users").child(currentUser.getUid()).child("branchRequests");

        //Finds all the current services in the database and displays them in list view
        if (dbRequestsRef != null) {
            dbRequestsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    branchRequestsList.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void showEditRequestsDialog(String request) {


    }

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
                                request1
                                request2
                                request3
                     */
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else{}

    }

}