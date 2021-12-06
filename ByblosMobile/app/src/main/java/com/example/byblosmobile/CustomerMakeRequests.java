package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerMakeRequests extends AppCompatActivity {
    String username;
    String roleName;
    String branchName;
    String serviceName;
    //display extra requirements
    TextView extraRequirements;

    //customer input section
    EditText firstName;
    EditText lastName;
    EditText address;
    EditText email;
    EditText birthDay;
    EditText extraInfo;

    //submit button
    Button submitButton;

    DatabaseReference db;


    List<Request> customerRequestList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_make_requests);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");
        this.branchName = intent.getStringExtra("branchName");
        this.serviceName = intent.getStringExtra("serviceName");

        db = FirebaseDatabase.getInstance().getReference("requests").child("username");
        customerRequestList = new ArrayList<>();

        //refer to the value
        extraRequirements = (TextView) findViewById(R.id.extraRequirements);

        firstName =(EditText) findViewById(R.id.firstNameInput);
        lastName=(EditText) findViewById(R.id.lastNameInput);
        address =(EditText) findViewById(R.id.addressInput);
        email =(EditText) findViewById(R.id.email);
        birthDay =(EditText) findViewById(R.id.dateBirthInput);
        extraInfo =(EditText) findViewById(R.id.extraInfoInput);

        submitButton =(Button) findViewById(R.id.sumbitButton);

        //method for sumbit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeRequests();
                backToCustomerMenu(view);

            }
        });



    }

    private void getCustomerRequestsList() {
        DatabaseReference customerRequests = FirebaseDatabase.getInstance().getReference("requests/" + username);
        customerRequestList.clear();

        //loop through to get the request

        customerRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //under the customer, there has list of requests, id should be digital number
                //looping through to get value
                for(DataSnapshot ds : snapshot.getChildren()){
                    Request request = ds.getValue(Request.class); //get the requests as object
                    customerRequestList.add(request);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void storeRequests() {
        String customerFirstName = firstName.getText().toString();
        String customerLastName = lastName.getText().toString();
        String customerAddress = address.getText().toString();
        String customerEmail = email.getText().toString();
        String customerBirthday = birthDay.getText().toString();
        String customerExtra = extraInfo.getText().toString();


        //validation

        if (customerFirstName.equals("") || customerLastName.equals("") ||customerEmail.equals("") ||customerAddress.equals("") || customerBirthday.equals("") ||customerExtra.equals("")) {
            setContentView(R.layout.activity_customer_make_requests);
            Toast.makeText(getApplicationContext(),"content require to fill!",Toast.LENGTH_LONG).show();
        }else if (!customerBirthday.matches("[a-zA-Z0-9 ]+") || !customerEmail.matches("^[a-z0-9_.]+@[a-z0-9]+\\.[a-z0-9]+$")||!customerAddress.matches("[a-zA-Z0-9 ]+")) {
            Toast.makeText(getApplicationContext(), "Phone number can only be 0 to 9, and Address can not include any symbols!", Toast.LENGTH_LONG).show();
        }else{

            //update the basic infomation in customer database child
            DatabaseReference basicInfo = FirebaseDatabase.getInstance().getReference("users/Customer/"+username+"basicInfo");
            basicInfo.child("firstName").setValue(customerFirstName);
            basicInfo.child("lastName").setValue(customerLastName);
            basicInfo.child("email").setValue(customerEmail);
            basicInfo.child("address").setValue(customerAddress);
            basicInfo.child("birthday").setValue(customerBirthday);

            Request newRequest = new Request(serviceName, username, branchName, customerExtra, null);
            getCustomerRequestsList();
            //add the request to database
            customerRequestList.add(newRequest);
            db.setValue(customerRequestList);
            Toast.makeText(this,"Request added",Toast.LENGTH_LONG).show();
        }

    }

    public void backToCustomerMenu(View view){
        Intent switchPage = new Intent(this,CustomerWelcomePage.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        switchPage.putExtra("branchName",branchName);
        startActivity(switchPage);
    }
}
