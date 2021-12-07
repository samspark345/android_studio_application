package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CustomerRateBranch extends AppCompatActivity {
    String username;
    String roleName;
    String branchName;


    TextView branchNameDisplay;
    EditText rate;

    Button submitButton;

    DatabaseReference db;
    DatabaseReference currBranch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rate_branch);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");
        this.branchName = intent.getStringExtra("branchName");

        branchNameDisplay = (TextView) findViewById(R.id.displayBranchName);
        rate = (EditText) findViewById(R.id.rate);
        submitButton = (Button) findViewById(R.id.submitRate);

        //access to current account and employee data base
        db = FirebaseDatabase.getInstance().getReference("users").child("Employee");
        currBranch = db.child(branchName);


        branchNameDisplay.setText(branchName);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRate(view);

            }
        });

    }
    public void sendRate(View view){
        String rating = rate.getText().toString();
        //validation
        if (rating.equals("")) {
            setContentView(R.layout.activity_customer_rate_branch);
            Toast.makeText(getApplicationContext(),"content require to fill!",Toast.LENGTH_LONG).show();
        }else if ( !rating.matches("[1-5]+")) {
            Toast.makeText(getApplicationContext(), "rating can only be 1 to 5!", Toast.LENGTH_LONG).show();

        }else {
           int currRate = Integer.valueOf(rating);
            currBranch.child("rating").setValue(currRate);


        }
        backToCustomerMenu(view);
    }


    public void backToCustomerMenu(View view){
        Intent switchPage = new Intent(this,CustomerViewRequest.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        startActivity(switchPage);
    }

}
