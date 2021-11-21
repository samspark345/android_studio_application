package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeServiceAdd extends AppCompatActivity {

    ListView serviceList;

    DatabaseReference databaseService;
    FirebaseDatabase db;
    FirebaseAuth curr;

    List<Service> services; //all services in system
    List<String> branchServicesList; //services offered by branch


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add_service);

        serviceList = (ListView) findViewById(R.id.serviceList);

        db = FirebaseDatabase.getInstance();
        curr = FirebaseAuth.getInstance();
        databaseService = db.getReference("service");

        services = new ArrayList<>();
        branchServicesList = new ArrayList<>();

        getCurrBranchServices();

        serviceList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Service service = services.get(i);
                showAddDialog(service.getName());
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Finds all services in the database and displays them in list view
        databaseService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                //iterate through all the nodes
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    //getting product
                    Service service = postSnapshot.getValue(Service.class);

                    //adding product to the list
                    services.add(service);

                    //creating adapter
                    ServiceList servicesAdapater = new ServiceList(EmployeeServiceAdd.this,services);
                    //attaching adapter to the listview
                    serviceList.setAdapter(servicesAdapater);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCurrBranchServices() { //check database
        FirebaseUser user = curr.getCurrentUser();
        if(user!=null){
            String branchName = user.getUid();
            DatabaseReference userData = db.getReference("users/" + "Employee/"+ branchName);
            userData.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<List<String>> list= new GenericTypeIndicator<List<String>>() {};

                    branchServicesList = snapshot.child("branchServices").getValue(list);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else{}
    }


    //display overall service in system
    private void showAddDialog(String serviceName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_employee_add_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAdd = (Button) dialogView.findViewById(R.id.add);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancel);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(branchServicesList != null) {
                    if (!branchServicesList.contains(serviceName)) { //check whether the service is exist
                        addServiceToBranch(serviceName);
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR! Service Already EXIST", Toast.LENGTH_LONG).show();
                    }
                }else{ //if branch haven't got any service, direct call add method
                    addServiceToBranch(serviceName);
                }
                dialog.dismiss();
            }
        });

    }


    //update current branch's service list
    private void addServiceToBranch(String serviceName){
        if(branchServicesList == null){
            branchServicesList = new ArrayList<String>(); //current branch haven't select any service yet
        }
        branchServicesList.add(serviceName);

        FirebaseUser user = curr.getCurrentUser();
        String branchName = user.getUid();//get branch name

        //create a new attribute (branchServices) for the branch
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("users/" + "Employee/" + branchName + "/branchServices");
        dr.setValue(branchServicesList); //add list value to the branchService attribute

        Toast.makeText(getApplicationContext(), "Service has been added Successfully", Toast.LENGTH_SHORT).show();

    }


    //button click method
    public void goBackToEmployeeWelcome(View view){
        Intent backToWelcome = new Intent(this, EmployeeWelcomePage.class);
        startActivity(backToWelcome);
    }

    public void goBackToEmployeeInfo(View view){
        Intent i = new Intent(this, EmployeeInfo.class);
        startActivity(i);
    }


}
