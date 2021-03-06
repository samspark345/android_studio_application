package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    TextView user;

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
    DatabaseReference serviceInfo;


    List<Request> customerRequestList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_make_requests);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");
        this.branchName = intent.getStringExtra("branchName");
        this.serviceName = intent.getStringExtra("serviceName");

        db = FirebaseDatabase.getInstance().getReference("requests").child(username);
        serviceInfo = FirebaseDatabase.getInstance().getReference("services").child(serviceName).child("requiredInfo");

        customerRequestList = new ArrayList<>();

        //refer to the value
        extraRequirements = (TextView) findViewById(R.id.extraRequirements);
        user = (TextView) findViewById(R.id.usernamehere);

        user.setText(username);
        firstName =(EditText) findViewById(R.id.firstNameInput);
        lastName=(EditText) findViewById(R.id.lastNameInput);
        address =(EditText) findViewById(R.id.addressInput);
        email =(EditText) findViewById(R.id.email);
        birthDay =(EditText) findViewById(R.id.dateBirthInput);
        extraInfo =(EditText) findViewById(R.id.extraInfoInput);

        submitButton =(Button) findViewById(R.id.sumbitButton);

        serviceInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String requirements = snapshot.getValue().toString();
                extraRequirements.setText(requirements);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //method for sumbit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeRequests();
                backToCustomerMenu(view);
            }
        });



    }

   /*private void getCustomerRequestsList() {
        DatabaseReference cq = FirebaseDatabase.getInstance().getReference("requests").child(username);
        //loop through to get the request

        cq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //under the customer, there has list of requests, id should be digital number
                //looping through to get value
               for(DataSnapshot ds : snapshot.getChildren()){
                    Request request = ds.getValue(Request.class); //get the requests as object
                    customerRequestList.add(request);
                }

                //GenericTypeIndicator<List<Request>> list= new GenericTypeIndicator<List<Request>>() {};

                //customerRequestList = snapshot.child(username).getValue(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }*/

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
        }else if (!customerBirthday.matches("[0-9 ]+") || !customerEmail.matches("^[a-z0-9_.]+@[a-z0-9]+\\.[a-z0-9]+$")||!customerAddress.matches("[a-zA-Z0-9 ]+")) {
            Toast.makeText(getApplicationContext(), "Invalid!", Toast.LENGTH_LONG).show();
        }else{

            //update the basic infomation in customer database child
            DatabaseReference basicInfo = FirebaseDatabase.getInstance().getReference("users/Customer/"+username+"/BasicInfo");
            basicInfo.child("firstName").setValue(customerFirstName);
            basicInfo.child("lastName").setValue(customerLastName);
            basicInfo.child("email").setValue(customerEmail);
            basicInfo.child("address").setValue(customerAddress);
            basicInfo.child("birthday").setValue(customerBirthday);

            Request newRequest = new Request(serviceName, username, branchName, customerExtra, "null");
            String id = db.push().getKey();
            db.child(id).setValue(newRequest);

            //add the request to database
            Toast.makeText(this,"Request added",Toast.LENGTH_LONG).show();
        }

    }
    static String serviceRequestValueValidation(String firstName, String lastName, String email, String address, String birthday, String extra){
        if (firstName.equals("") || lastName.equals("") ||email.equals("") ||address.equals("") || birthday.equals("") ||extra.equals("")) {
            return "content require to fill!";
        }else if (!birthday.matches("[0-9 ]+") || !email.matches("^[a-z0-9_.]+@[a-z0-9]+\\.[a-z0-9]+$")||!address.matches("[a-zA-Z0-9 ]+")) {
            return "Invalid!";
        }else{
            return "Request added";
        }
    }

    public void backToCustomerMenu(View view){
        Intent switchPage = new Intent(this,WelcomePage.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        switchPage.putExtra("branchName",branchName);
        startActivity(switchPage);
    }
}
