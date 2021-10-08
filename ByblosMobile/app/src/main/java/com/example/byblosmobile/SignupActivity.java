package com.example.byblosmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class SignupActivity extends AppCompatActivity {
    private boolean customerRole;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.signUpName);
        password = findViewById(R.id.signUpPassword);
    }

    public void onLogin(View view){
        Intent switchToLogin = new Intent(this, LoginActivity.class);
        startActivity(switchToLogin);
    }

    public void onRoleButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.customerRoleBtn:
                if (checked)
                    // customer role selected
                    customerRole = true;
                    break;
            case R.id.employeeRoleBtn:
                if (checked)
                    // employee role selected
                    customerRole = false;
                    break;
        }
    }
}