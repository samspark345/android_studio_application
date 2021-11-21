package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerRequest extends AppCompatActivity {
    DatabaseReference databaseService;
    DatabaseReference databaseRequests;
    List<Service> services; //all services in system
    ListView serviceList;

    EditText requestName;
    EditText customerInfo;
    EditText requestedService;

    FirebaseAuth curr;

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");

        setContentView(R.layout.activity_customer_sumbit_request);

        databaseRequests = FirebaseDatabase.getInstance().getReference("requests");
        databaseService = FirebaseDatabase.getInstance().getReference("services");
        services = new ArrayList<>();
        serviceList = (ListView) findViewById(R.id.services);
        curr = FirebaseAuth.getInstance();


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

                    ServiceList servicesAdapater = new ServiceList(CustomerRequest.this,services);

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_customer_add_request, null);
        requestName = (EditText) dialogView.findViewById(R.id.requestName);
        customerInfo = (EditText) dialogView.findViewById(R.id.customerInfo);
        requestedService = (EditText) dialogView.findViewById(R.id.requestedService);
        dialogBuilder.setView(dialogView);

        final Button buttonAdd = (Button) dialogView.findViewById(R.id.AddRequest);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.Cancel);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addRequest(view);

            }
        });

    }

    public void addRequest(View view){

        String rs = requestedService.getText().toString().trim();
        String ci = customerInfo.getText().toString().trim();
        String rn = requestName.getText().toString().trim();
        //checking if the value is provided
        if(!(rs.isEmpty()||ci.isEmpty()||rn.isEmpty())){
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the primary key for our product

            //creating a product Object
            Request request = new Request(rs,ci, rn, "null");

            //saving the Product
            databaseRequests.child(username).setValue(request);

            //setting edittext to blank again
            requestName.setText("");
            customerInfo.setText("");
            requestedService.setText("");

            Toast.makeText(this,"Request added",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "(PLEASE ENTER VALID INFORMATION!)", Toast.LENGTH_LONG).show();
        }
    }

}
