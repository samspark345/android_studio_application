package com.example.byblosmobile;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerRateBranch extends AppCompatActivity {
    String username;
    String roleName;
    String branchName;




    public void backToCustomerMenu(View view){
        Intent switchPage = new Intent(this,CustomerWelcomePage.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        startActivity(switchPage);
    }

}
