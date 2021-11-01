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

    DatabaseHelper db;
    ArrayList<Service> servicesList = new ArrayList<Service>(50);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        db = new DatabaseHelper(this);

        Service carRental = new Service("Car Rental", 10, null, "Including customer first name,last name,date of birth, address,email, license type(G1,G2,G),preferred car type,pick up date & time, return data & time");
        Service truckRental = new Service("Truck Rental", 10, null, "Including customer first name,last name,date of birth, address,email, license type(G1,G2,G),pick up date & time, return data & time,max #km,area to use");
        Service movingAssitance = new Service("Moving Assistance", 20, null, "Including customer first name,last name,date of birth, address,email,destination,start location, #Movers,#Boxes");
    }
}
