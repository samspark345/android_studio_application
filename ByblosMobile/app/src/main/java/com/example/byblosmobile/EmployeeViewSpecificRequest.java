package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeViewSpecificRequest extends AppCompatActivity {
    /*work flow
    once employee long click to the request in the request list
    then it jump to this activity

    it will shows the customer name, service name and it status
    status first will display null

    if employee click accept button, status change to "accept" database should load attribute branchRequest and the id is customer name
    if employee click reject button, status change to "reject" database would be change

    after click one of button, it should jump backTo RequestList view
     */
    String username;
    String roleName;

    TextView customerName;
    TextView serviceName;
    TextView requestStatus;

    Button acceptButton;
    Button rejectButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requestlist);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        customerName = (TextView) findViewById(R.id.CustomerInfo);
        serviceName=(TextView) findViewById(R.id.RequestedService);
        requestStatus =(TextView)findViewById(R.id.RequestStatus);

        acceptButton = (Button)findViewById(R.id.Cancel);
        rejectButton =(Button)findViewById(R.id.)


    }


    public void backToRequestList(View view){
        Intent backToWelcome = new Intent(this, EmployeeDealRequest.class);
        startActivity(backToWelcome);
    }
}
