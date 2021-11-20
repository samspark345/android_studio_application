package com.example.byblosmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceDelete extends AppCompatActivity {
    ListView branchService;

    DatabaseReference databaseService;
    FirebaseDatabase db;
    FirebaseAuth curr;

    List<String> branchServicesList; //services offered by branch


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_delete_branchservice);

        branchService = (ListView) findViewById(R.id.listOfBranchService);

        db = FirebaseDatabase.getInstance();
        curr = FirebaseAuth.getInstance();
        databaseService = db.getReference("service");

        branchServicesList = new ArrayList<>();

        getCurrBranchServices();

        branchService.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String service = branchServicesList.get(i);
                showDeleteDialog(service);
                return true;
            }
        });

    }

    private void showDeleteDialog(String service) {
    }

    private void getCurrBranchServices() { //check database
        FirebaseUser user = curr.getCurrentUser();
        if(user!=null){
            String branchName = user.getUid();
            DatabaseReference userData = db.getReference("users/" + "Employee/"+ branchName);
            userData.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<List<String>> list= new GenericTypeIndicator<List<String>>() {};

                    branchServicesList = snapshot.child("branchServices").getValue(list);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else{}
    }





}
