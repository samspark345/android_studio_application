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

        //customerList = new ArrayList<>();
        customerName = new ArrayList<>();

        listCustomerAccount = (ListView) findViewById(R.id.listCustomer);
        db = FirebaseDatabase.getInstance().getReference("users").child("Customer");

        listCustomerAccount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Customer b = customerList.get(i);
                String customern = customerName.get(i);
                showDeleteDialog(customern);
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
    public void showBranch(DataSnapshot dataSnapshot){
        //customerList.clear();
        customerName.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            //we can get customer name here
            String customername = ds.getKey();
            //Toast.makeText(getApplicationContext(),customername,Toast.LENGTH_LONG).show();
            //Customer object = ds.getValue(Customer.class);

            //String username = String.valueOf(ds.child("username").getValue());
            //String password = String.valueOf(ds.child("password").getValue());

            //Customer customer = new Customer(username, password, "Customer");
            //customerList.add(object);
            customerName.add(customername);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, customerName);
        listCustomerAccount.setAdapter(adapter);

    }






    private void showDeleteDialog(String customerName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_employee_delete_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.DeleteButton);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.CancelButton);

        dialogBuilder.setTitle(customerName);
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
                deleteCustomer(customerName);
                dialog.dismiss();
            }
        });

    }

    private void deleteCustomer(String name) {
        customerName.remove(name);

        //String cn = customer.getUserName();//get branch name


        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users").child("Customer").child(name);
        dR.setValue(null); //replace list value to the branchService attribute

        Toast.makeText(getApplicationContext(), "customer has been deleted Successfully", Toast.LENGTH_SHORT).show();

    }




    public void goBackToAdminWelcome(View view){
        Intent backToWelcome = new Intent(this, AdminWelcomePage.class);
        startActivity(backToWelcome);
    }

}
