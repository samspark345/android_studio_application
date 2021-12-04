package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerWelcomePage extends AppCompatActivity {

    String username;
    String roleName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_customer_welcome_page);
    }


    public void switchToCheckRequest(View view){
        Intent switchPage = new Intent(this,CustomerViewRequest.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        startActivity(switchPage);
    }


    public void switchToSearchByService(View view){
        Intent switchPage = new Intent(this,SearchByService.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        startActivity(switchPage);
    }

    public void switchToSearchByAddress(View view){
        Intent switchPage = new Intent(this,SearchByAddress.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        startActivity(switchPage);
    }

    public void switchToSearchByWorkingHour(View view){
        Intent switchPage = new Intent(this,SearchByWorkingHour.class);
        switchPage.putExtra("username", username);
        switchPage.putExtra("roleName", roleName);
        startActivity(switchPage);
    }
}
