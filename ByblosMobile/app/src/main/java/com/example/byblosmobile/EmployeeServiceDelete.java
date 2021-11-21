package com.example.byblosmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onStart() {
        super.onStart();

        databaseService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showBranchService(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }
    //display all the request for that employee






    private void showDeleteDialog(String service) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_employee_delete_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAdd = (Button) dialogView.findViewById(R.id.add);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancel);

        dialogBuilder.setTitle(service);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(branchServicesList != null) {
                    if (!branchServicesList.contains(service)) { //check whether the service is exist
                        Toast.makeText(getApplicationContext(), "ERROR! Service doesn't Exist", Toast.LENGTH_LONG).show();
                    } else {
                       deleteService(service);
                    }
                }else{ //if branch haven't got any service
                    Toast.makeText(getApplicationContext(), "ERROR! Branch doesn't offer any service yet!", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

    }

    private void deleteService(String service) {
        branchServicesList.remove(service);

        FirebaseUser user = curr.getCurrentUser();
        String branchName = user.getUid();//get branch name

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users/" + "Employee/" + branchName + "/branchServices");
        dR.setValue(branchServicesList); //replace list value to the branchService attribute

        Toast.makeText(getApplicationContext(), "Service has been deleted Successfully", Toast.LENGTH_SHORT).show();

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


    public void showBranchService(DataSnapshot dataSnapshot){
        branchServicesList.clear();
        FirebaseUser user = curr.getCurrentUser();
        String branchName = user.getUid();
        for(DataSnapshot ds : dataSnapshot.child("Users").child("Employee").child(branchName).child("branchServices").getChildren()){
            branchServicesList.add(ds.getValue().toString());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, branchServicesList);
        branchService.setAdapter(adapter);
    }





}
