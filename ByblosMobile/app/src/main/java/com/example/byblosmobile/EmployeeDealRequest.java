package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class EmployeeDealRequest extends AppCompatActivity {


    String username; // curr employee username
    String roleName;
    ListView listViewRequests;

    DatabaseReference databaseR;
    DatabaseReference databaseReference;
    FirebaseDatabase db;
    DatabaseReference currBranch;

    List<Request> requestsList; //stores all the request name
    List<String> requestName;


    TextView customerName;
    TextView serviceName;
    TextView requestStatus;

    Button acceptButton;
    Button rejectButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requestlist);

        listViewRequests=(ListView) findViewById(R.id.request);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        db = FirebaseDatabase.getInstance();
        databaseR = db.getReference();//get whole system database
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child("Employee");
        currBranch = databaseReference.child(username);//stores all the information about the branch

        requestsList = new ArrayList<>();
        getAllRequest();//get all the request from database

        listViewRequests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Request request = requestsList.get(i);
                showRequestDialog(request);
                return true;
            }
        });
    }

    private void showRequestDialog(Request request) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_employee_request, null);
        dialogBuilder.setView(dialogView);


        customerName = (TextView) findViewById(R.id.CustomerInfo);
        serviceName=(TextView) findViewById(R.id.RequestedService);
        requestStatus =(TextView)findViewById(R.id.RequestStatus);

        acceptButton = (Button)findViewById(R.id.acceptButton);
        rejectButton =(Button)findViewById(R.id.rejectButton);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        //display request information
        String requestCustomer = request.getCustomerName();
        String requestService = request.getServiceName();
        String status = request.getStatus();

        customerName.setText(requestCustomer);
        serviceName.setText(requestService);
        requestStatus.setText(status);

        //button click to change status of the request
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               request.setStatus("Accept");
                // add this request to branch request section
                DatabaseReference branchrequest = currBranch.child("branchRequest");
                branchrequest.child(request.getCustomerName()).setValue(request);

                //update the request status to database
                DatabaseReference systemRequest = databaseR.child("requests");
                systemRequest.child(request.getCustomerName()).setValue(request);

                dialog.dismiss();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setStatus("Delete");
                //update the request status to database
                DatabaseReference systemRequest = databaseR.child("requests");
                systemRequest.child(request.getCustomerName()).setValue(request);
                dialog.dismiss();
            }
        });

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


    private void getAllRequest() { //check database get all the request in curr branch
        if(username!=null){
            DatabaseReference requestsData = db.getReference("requests"); //get requests data
            requestsData.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<List<Request>> list= new GenericTypeIndicator<List<Request>>() {};
                    //create list of requests that store as Request class

                    requestsList = snapshot.getValue(list);
                    //it contains all the request that branch receives
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else{}

    }

    //display string
    public void showRequestData(DataSnapshot dataSnapshot){
        requestName.clear();
        String branchName = username;
        for(DataSnapshot ds : dataSnapshot.child("requests").getChildren()){ //the children is a Request object
            String request = ds.getValue().toString();
            requestName.add(request);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, requestName);
        listViewRequests.setAdapter(adapter);
    }

    public void fromRequestListToEmployeeWelcomePage(View view){
        Intent backToWelcome = new Intent(this, EmployeeWelcomePage.class);
        startActivity(backToWelcome);
    }

}