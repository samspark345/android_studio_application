package com.example.byblosmobile;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerViewRequest extends AppCompatActivity {
    //display all the requests made by this customer
    //long click service can jump to the rate branch page
    ListView requests;





    public void backToCustomerMenu(View view){
        Intent switchPage = new Intent(this,CustomerWelcomePage.class);
        startActivity(switchPage);
    }



}
