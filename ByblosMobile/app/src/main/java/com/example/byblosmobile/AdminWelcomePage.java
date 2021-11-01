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

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome_page);

        addServiceButton = (Button) findViewById(R.id.AddService);
        deleteServiceButton = (Button)findViewById(R.id.DeleteService);
        editServiceButton = (Button)findViewById(R.id.editService);
        deleteBranchButton = (Button)findViewById(R.id.deleteBranch);
        deleteCustomerButton = (Button)findViewById(R.id.deleteCustomer);


        addServiceButton.setOnClickListener(new View.OnClickListener(){ // switch to another UI with Service class
            @Override
            public void onClick(View view){
                openServiceActivity(view);
            }
        });

        deleteServiceButton.setOnClickListener(new View.OnClickListener(){ // switch to another UI with Service class
            @Override
            public void onClick(View view){
                openServiceActivity(view);
            }
        });

        editServiceButton.setOnClickListener(new View.OnClickListener(){ //switch to service editing class
            @Override
            public void onClick(View view){
                openServiceActivity(view);
            }
        });

    }

    public void openServiceActivity(View view){
        Intent switchToServiceActivity = new Intent(this, ServiceActivityForAdmin.class);
        startActivity(switchToServiceActivity);

    }

}
