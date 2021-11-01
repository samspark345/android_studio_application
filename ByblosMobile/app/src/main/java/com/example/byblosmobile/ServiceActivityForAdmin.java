package com.example.byblosmobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceActivityForAdmin extends AppCompatActivity  {
    EditText requiredInfo;
    EditText serviceName;
    EditText rate;
    ListView listServices;

    Button addButton;
    Button deleteButton;
    Button backToAdminMain;

    DatabaseHelper db;
    ArrayList<String> services;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        serviceName = (EditText)findViewById(R.id.editServiceName);
        requiredInfo = (EditText)findViewById(R.id.editTextRequiredInfo);
        rate = (EditText)findViewById(R.id.editTextHourRate);


        listServices = (ListView) findViewById(R.id.listServices);
        services = new ArrayList<>();

        addButton = findViewById(R.id.AddServiceInfo);
        deleteButton = findViewById(R.id.DeleteService);
        backToAdminMain = findViewById(R.id.backToMain);

        db = new DatabaseHelper(this);

        Service carRental = new Service("Car Rental", "10", null, "Including customer first name,last name,date of birth, address,email, license type(G1,G2,G),preferred car type,pick up date & time, return data & time");
        Service truckRental = new Service("Truck Rental", "10", null, "Including customer first name,last name,date of birth, address,email, license type(G1,G2,G),pick up date & time, return data & time,max #km,area to use");
        Service movingAssitance = new Service("Moving Assistance", "20", null, "Including customer first name,last name,date of birth, address,email,destination,start location, #Movers,#Boxes");

        db.addService(carRental);
        db.addService(truckRental);
        db.addService(movingAssitance);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = serviceName.getText().toString().trim();
                String info = requiredInfo.getText().toString().trim();
                String price = rate.getText().toString().trim();

                if(!(info.isEmpty()||price.isEmpty()||name.isEmpty())){
                    Boolean insert = db.addService(new Service(name,price,null,info));
                    if(insert){
                        Toast.makeText(ServiceActivityForAdmin.this,"added to database",Toast.LENGTH_LONG).show();
                        services.clear();
                    }else {
                        Toast.makeText(ServiceActivityForAdmin.this,"data already exists",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ServiceActivityForAdmin.this,"Please enter all data", Toast.LENGTH_LONG).show();
                }

            }
        });


        backToAdminMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceActivityForAdmin.this, AdminWelcomePage.class);
                startActivity(i);
            }
        });

        listServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                showUpdateDeleteDialog();
                return true;
            }
        });
    }

    private void showUpdateDeleteDialog() {

    }

}
