package com.example.byblosmobile;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDeleteCustomerAccount extends AppCompatActivity {



    public void goBackToAdminWelcome(View view){
        Intent backToWelcome = new Intent(this, AdminWelcomePage.class);
        startActivity(backToWelcome);
    }

}
