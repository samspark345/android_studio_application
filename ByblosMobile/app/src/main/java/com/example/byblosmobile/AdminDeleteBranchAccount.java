package com.example.byblosmobile;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDeleteBranchAccount extends AppCompatActivity {
    //show list of branch account in the system
    DatabaseReference db;

    List<Employee> branchList;
    List<String> branchName;
    ListView listBranchAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_branchaddelete);

        branchList = new ArrayList<>();
        branchName = new ArrayList<>();


        listBranchAccount = (ListView) findViewById(R.id.listBranch);
        db = FirebaseDatabase.getInstance().getReference("users").child("Employee");




        listBranchAccount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Employee b = branchList.get(i);
                showDeleteDialog(b);
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showBranch(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }
    //display all the request for that employee






    private void showDeleteDialog(Employee branch) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_employee_delete_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.DeleteButton);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.CancelButton);

        dialogBuilder.setTitle(branch.getBranchName());
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBranch(branch);
                dialog.dismiss();
            }
        });

    }

    private void deleteBranch(Employee branch) {
        branchList.remove(branch);

        String branchName = branch.getBranchUsername();//get branch name

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users/" + "Employee/" + branchName);

        dR.setValue(branchList); //replace list value to the branchService attribute


        Toast.makeText(getApplicationContext(), "Branch has been deleted Successfully", Toast.LENGTH_SHORT).show();

    }

    private void getCurrBranch(DataSnapshot dataSnapshot) { //check database

        branchList.clear();
        branchName.clear();

        // child("users").child("Employee").child(branchName)
        for(DataSnapshot ds : dataSnapshot.getChildren()){ // curr is one of  branch name in the system
            //add branch class
            Employee employee = ds.getValue(Employee.class);
            branchList.add(employee);
            branchName.add(employee.getBranchName());

        }
    }


    public void showBranch(DataSnapshot dataSnapshot){
           getCurrBranch(dataSnapshot);

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, branchName);
            listBranchAccount.setAdapter(adapter);

        }



    public void goBackToAdminWelcome(View view){
        Intent backToWelcome = new Intent(this, AdminWelcomePage.class);
        startActivity(backToWelcome);
    }
}
