package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchByService extends AppCompatActivity {
    String username;
    String roleName;

    List<Service> services;

    ListView listService;
    SearchView searchService;
    DatabaseReference databaseService;

    ArrayAdapter<String> displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_by_workinghour);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.roleName = intent.getStringExtra("roleName");

        listService = (ListView) findViewById(R.id.workinghourlistview);
        searchService = (SearchView) findViewById(R.id.workinghourSearchView);
        services = new ArrayList<>();

        databaseService = FirebaseDatabase.getInstance().getReference("services");

        searchService.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                displayView.getFilter().filter(s);
                return false;
            }
        });


        listService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service s = services.get(i);//looping through
                pickedService(s.getName());
            }
        });
    }

    private void pickedService(String service) {
        //show list  service offered by this branch
        Intent switchToCheck = new Intent(this, CustomerCheckBranches.class);
        switchToCheck.putExtra("username", username);
        switchToCheck.putExtra("roleName", roleName);
        switchToCheck.putExtra("service",service);
        startActivity(switchToCheck);
    }
    //display all the service offered by system
    @Override
    protected void onStart() {
        super.onStart();

        databaseService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showServices(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }
    private void showServices(DataSnapshot dataSnapshot) {
        getListOfServices(dataSnapshot);
        displayView = new ArrayAdapter(this, android.R.layout.simple_list_item_1,services);
        listService.setAdapter(displayView);
    }

    private void getListOfServices(DataSnapshot dataSnapshot) {
        databaseService.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                services.clear();


                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){

                    Service service = postSnapshot.getValue(Service.class);

                    services.add(service);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }
        });



    }

    public void goBackToCustomerMenu(View view){
        Intent backToWelcome = new Intent(this, CustomerWelcomePage.class);
        backToWelcome.putExtra("username", username);
        backToWelcome.putExtra("roleName", roleName);
        startActivity(backToWelcome);
    }
}
