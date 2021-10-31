package com.example.byblosmobile;

import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;



public class Admin extends AppCompatActivity {

    ArrayList<Service> servicesList = new ArrayList<Service>(50);
    ListView servicesListView = findViewById(R.id.servicesListView);
    BranchDatabase branchDatabaseBase = new BranchDatabase(this);



    public void addBranch(String branch){
        branchDatabaseBase.addBranch(branch);

    }

    public boolean deleteBranch(String branch){
        return branchDatabaseBase.deleteBranch(branch);
    }

}
