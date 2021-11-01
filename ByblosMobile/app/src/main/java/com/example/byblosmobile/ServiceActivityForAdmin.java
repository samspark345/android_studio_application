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
    Button deleteEditButton;
    Button backToAdminMain;

    DatabaseHelper db;
    ArrayList<String> services;
    ArrayAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        serviceName = (EditText)findViewById(R.id.editServiceName);
        requiredInfo = (EditText)findViewById(R.id.editTextRequiredInfo);
        rate = (EditText)findViewById(R.id.editTextHourRate);


        listServices = (ListView) findViewById(R.id.listServices);
        services = new ArrayList<>();

        addButton = findViewById(R.id.AddServiceInfo);
        deleteEditButton = findViewById(R.id.DeleteEditService);
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
                String r = rate.getText().toString().trim();

                if(!(info.isEmpty()||r.isEmpty()||name.isEmpty())){
                    Boolean insert = db.addService(new Service(name,r,null,info));
                    if(insert){
                        Toast.makeText(ServiceActivityForAdmin.this,"add to database",Toast.LENGTH_LONG).show();
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

        deleteEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDeleteDialog(); // in oder to work in same databas
            }
        });

    }

    private void showUpdateDeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_service, null);
        dialogBuilder.setView(dialogView);

        final EditText nameService = (EditText) dialogView.findViewById(R.id.nameService);
        final EditText infoService  = (EditText) dialogView.findViewById(R.id.infoService);
        final EditText rateService = (EditText) dialogView.findViewById(R.id.rateEdit);
        final Button updateServiceButton = (Button) dialogView.findViewById(R.id.updateServiceButton);
        final Button deleteServiceButton = (Button) dialogView.findViewById(R.id.deleteServiceButton);

        //dialogBuilder.setTitle(ServiceName);
        final AlertDialog tmpScreen = dialogBuilder.create();
        tmpScreen.show();

        updateServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateService(nameService.getText().toString(),infoService.getText().toString(),rateService.getText().toString());
                Toast.makeText(ServiceActivityForAdmin.this, "sucess", Toast.LENGTH_LONG).show();
                services.clear();
                viewData();
                tmpScreen.dismiss();
            }
        });

        deleteServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteService(nameService.getText().toString());
                Toast.makeText(ServiceActivityForAdmin.this, "sucess", Toast.LENGTH_LONG).show();
                services.clear();
                viewData();
                tmpScreen.dismiss();
            }
        });

    }


    private void viewData(){
        Cursor cursor = db.viewData();

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                services.add(cursor.getString(0)+" (required Infomation:"+cursor.getString(1)+")(rate: "+cursor.getString(2)+")(Branches: "+cursor.getString(3)+")");
                //index 0 is the name of the service + Required info + rate of service + branch Name
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, services);
            listServices.setAdapter(adapter);
        }
    }

    /*public void removeService(View view){
        DatabaseHelper dbHandler = new DatabaseHelper(this);
        dbHandler.deleteService(serviceName.getText().toString());
    }

    public void newService(View view){
        String name = serviceName.getText().toString();
        String info = requiredInfo.getText().toString();
        String r = rate.getText().toString();

        Service service = new Service(name,r,null,info);
        DatabaseHelper databaseH = new DatabaseHelper(this);
        databaseH.addService(service);

        serviceName.setText("");
        requiredInfo.setText("");
        rate.setText("");

        Toast.makeText(this,"added service",Toast.LENGTH_SHORT).show();
    }*/


    //Validation implement


}
