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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDeleteCustomerAccount extends AppCompatActivity {
    //show list of branch account in the system
    DatabaseReference db;

    List<Customer> customerList;
    List<String> customerName;
    ListView listCustomerAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customerdelete);

        customerList = new ArrayList<>();
        customerName = new ArrayList<>();

        listCustomerAccount = (ListView) findViewById(R.id.listCustomer);
        db = FirebaseDatabase.getInstance().getReference("users").child("Customer");

        listCustomerAccount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Customer b = customerList.get(i);
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






    private void showDeleteDialog(Customer customer) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_employee_delete_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.DeleteButton);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.CancelButton);

        dialogBuilder.setTitle(customer.getUserName());
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
                deleteCustomer(customer);
                dialog.dismiss();
            }
        });

    }

    private void deleteCustomer(Customer customer) {
        customerList.remove(customer);

        String customerName = customer.getUserName();//get branch name

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users/" + "customer/" + customerName);
        dR.setValue(customerList); //replace list value to the branchService attribute

        Toast.makeText(getApplicationContext(), "customer has been deleted Successfully", Toast.LENGTH_SHORT).show();

    }

    private void getCurrCustomer(DataSnapshot dataSnapshot) { //check database
        customerList.clear();
        customerName.clear();


        // child("users").child("Employee").child(branchName)
        for(DataSnapshot ds : dataSnapshot.getChildren()){ // curr is one of  branch name in the system
            //add branch class
            Customer customer = ds.getValue(Customer.class);
            customerList.add(customer);
            customerName.add(customer.getUserName());

        }
    }


    public void showBranch(DataSnapshot dataSnapshot){
        getCurrCustomer(dataSnapshot);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, customerName);
        listCustomerAccount.setAdapter(adapter);

    }





    public void goBackToAdminWelcome(View view){
        Intent backToWelcome = new Intent(this, AdminWelcomePage.class);
        startActivity(backToWelcome);
    }

}
