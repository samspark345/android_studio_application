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




public class AdminWelcomePage extends AppCompatActivity {
    Button deleteCustomerButton;
    Button deleteBranchButton;
    Button deleteServiceButton;
    Button addServiceButton;
    Button editServiceButton;
    //ListView servicesListView = findViewById(R.id.servicesListView);
    //BranchDatabase branchDatabaseBase = new BranchDatabase(this);

   /* public void addBranch(String branch){
        branchDatabaseBase.addBranch(branch);
    }
    public boolean deleteBranch(String branch){
        return branchDatabaseBase.deleteBranch(branch);
    }*/
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome_page);
        addServiceButton = findViewById(R.id.AddService);
        deleteServiceButton = findViewById(R.id.DeleteService);
        editServiceButton = findViewById(R.id.editService);
        deleteBranchButton = findViewById(R.id.deleteBranch);
        deleteCustomerButton = findViewById(R.id.deleteCustomer);



        addServiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openServiceActivity();
            }
        });

        deleteServiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openServiceActivity();
            }
        });

    }

    public void openServiceActivity(){
        Intent intent = new Intent(this,ServiceActivityForAdmin.class);
        startActivity(intent);

    }



}
