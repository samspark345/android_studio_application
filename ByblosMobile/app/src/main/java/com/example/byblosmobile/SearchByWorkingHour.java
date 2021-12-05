package com.example.byblosmobile;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SearchByWorkingHour extends AppCompatActivity {

    String username;
    String roleName;
    public void goBackToCustomerMenu(View view){
        Intent backToWelcome = new Intent(this, CustomerWelcomePage.class);
        backToWelcome.putExtra("username", username);
        backToWelcome.putExtra("roleName", roleName);
        startActivity(backToWelcome);
    }
}
