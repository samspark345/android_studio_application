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
    //workflow
    /*
    find the requests that matches with the branch
    display in the listview
     */


    String username; // current employee username
    String roleName;
    ListView listViewRequests;

    DatabaseReference databaseRequests;
    DatabaseReference databaseReference;
    FirebaseDatabase db;
    DatabaseReference currBranch;

    List<Request> requestsList; //stores all the request name
    List<String> key;


    TextView customerName;
    TextView serviceName;
    TextView requestStatus;

    Button acceptButton;
    Button rejectButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requestlist);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        listViewRequests = (ListView) findViewById(R.id.request);


        db = FirebaseDatabase.getInstance();
        databaseRequests = FirebaseDatabase.getInstance().getReference("requests");
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child("Employee");
        currBranch = databaseReference.child(username);//stores all the information about the branch
        requestsList = new ArrayList<>();

        getListOfRequests();
        listViewRequests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Request request = requestsList.get(i);
                String k = key.get(i);
                showDialog(request,k);
                return true;
            }
        });
    }


  private void showDialog(Request request,String key) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_employee_request, null);

        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

        final Button buttonAccept = (Button) alertDialog.findViewById(R.id.acceptButton);
        final Button buttonReject = (Button) alertDialog.findViewById(R.id.rejectButton);


        String requestCustomer = request.getCustomerName();
        String requestService = request.getServiceName();
        String status = request.getStatus();

        customerName = (TextView) alertDialog.findViewById(R.id.CustomerInfo);
        serviceName=(TextView) alertDialog.findViewById(R.id.RequestedService);
        requestStatus =(TextView)alertDialog.findViewById(R.id.RequestStatus);


        customerName.setText(requestCustomer);
        serviceName.setText(requestService);
        requestStatus.setText(status);

        //button click to change status of the request
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setStatus("Accept");
                // add this request to branch request section


                DatabaseReference branchrequest = currBranch.child("branchRequest");
                branchrequest.setValue(request);

                //update the request status to database
                DatabaseReference systemRequest = databaseRequests.child(requestCustomer);
                systemRequest.child(key).setValue(request);

                alertDialog.dismiss();
            }
        });

        buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setStatus("Delete");
                //update the request status to database
                DatabaseReference systemRequest = databaseRequests.child(requestCustomer);
                systemRequest.child(key).setValue(request);
                alertDialog.dismiss();
            }
        });

    }


    //display all the requests that signed to this branch in the list view
    @Override
    protected void onStart() {
        super.onStart();
        requestsList.clear();

        databaseRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showRequest(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void showRequest(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) { // loop through children of requests which is customer name
            String customerName = ds.getValue().toString();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("requests").child(customerName);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        Request r = postSnapshot.getValue(Request.class);
                        if (r.getBranchName() == username) {
                            requestsList.add(r);
                            key.add(postSnapshot.getKey());
                        }


                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            ArrayAdapter displayView = new ArrayAdapter(this, android.R.layout.simple_list_item_1, requestsList);
            listViewRequests.setAdapter(displayView);
        }
    }
    private void getListOfRequests(){
        databaseRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //first loop customer name
                for (DataSnapshot ds : snapshot.getChildren()) { // loop through children of requests which is customer name
                    String customer =  ds.getValue().toString();

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("requests").child(customer);
                    //nested loop to loop requests that customer have
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                Request r = postSnapshot.getValue(Request.class);
                                if (r.getBranchName() == username) {
                                    requestsList.add(r);
                                    key.add(postSnapshot.getKey());
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public void fromRequestListToEmployeeWelcomePage(View view){
        Intent backToWelcome = new Intent(this, WelcomePage.class);
        backToWelcome.putExtra("username", username);
        backToWelcome.putExtra("roleName", roleName);
        startActivity(backToWelcome);
    }

}
