package com.example.byblosmobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class ServiceActivityForAdmin extends AppCompatActivity {

    EditText requiredInfo;
    EditText serviceName;
    EditText rate;


    Button addButton;
    Button deleteEditButton;
    Button backToAdminMain;

    ListView listViewService;
    DatabaseReference databaseService;

    List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        serviceName = (EditText) findViewById(R.id.editServiceName);
        requiredInfo = (EditText) findViewById(R.id.editTextRequiredInfo);
        rate = (EditText) findViewById(R.id.editTextHourRate);

        listViewService = (ListView) findViewById(R.id.listServices);
        services = new ArrayList<>();


        addButton = findViewById(R.id.AddServiceInfo);
        deleteEditButton = findViewById(R.id.DeleteEditService);
        backToAdminMain = findViewById(R.id.backToMain);
        databaseService = FirebaseDatabase.getInstance().getReference("services");


        //adding an onclicklistener to button
       addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

        //back to admin menu
        backToAdminMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ServiceActivityForAdmin.this, AdminWelcomePage.class);
                startActivity(i);
            }
        });


        listViewService.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getName());
                return true;
            }
        });
    }
    public void goBackToAdminWelcome(View view){
        Intent backToWelcome = new Intent(this, AdminWelcomePage.class);
        startActivity(backToWelcome);
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseService.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                services.clear();

                //iterate through all the nodes
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    //getting product
                    Service service = postSnapshot.getValue(Service.class);

                    //adding product to the list
                    services.add(service);

                    //creating adapter
                    ServiceList servicesAdapater = new ServiceList(ServiceActivityForAdmin.this,services);
                    //attaching adapter to the listview
                    listViewService.setAdapter(servicesAdapater);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }
        });
    }


    private void showUpdateDeleteDialog(String serviceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_service, null);
        dialogBuilder.setView(dialogView);

        final EditText nameService = (EditText) dialogView.findViewById(R.id.nameService);
        final EditText infoService  = (EditText) dialogView.findViewById(R.id.infoService);
        final EditText rateService = (EditText) dialogView.findViewById(R.id.rateEdit);

        final Button updateServiceButton = (Button) dialogView.findViewById(R.id.updateServiceButton);
        final Button deleteServiceButton = (Button) dialogView.findViewById(R.id.deleteServiceButton);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        updateServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameService.getText().toString().trim();
                String info = infoService.getText().toString().trim();
                String r = rateService.getText().toString().trim();



                if (!(info.isEmpty())&& !(r.isEmpty()) && !(name.isEmpty())) {
                    updateService(name,r,info);
                    b.dismiss();
                }
            }
        });

        deleteServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceName);

                b.dismiss();
            }
        });
    }


    //update product on the database
    private void updateService(String name, String rate,String info) {
        //getting the specific product reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(name);
        //updating product
        Service service = new Service(name,rate,info,"branch");
        dR.setValue(service);
        Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();
    }


    public boolean deleteService(String name){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(name);
        dR.removeValue();
        Toast.makeText(getApplicationContext(),"Service Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    //add a product to the database
    private void addProduct() {

        if(!correctInput()){  // Checking if the input for the fields are valid
            return;
        }

        //getting the value to save
        String name = serviceName.getText().toString().trim();
        String info = requiredInfo.getText().toString().trim();
        String r = rate.getText().toString().trim();

        //checking if the value is provided
        if(!(info.isEmpty()||r.isEmpty()||name.isEmpty())){
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the primary key for our product
            String id = name;

            //creating a product Object
            Service service = new Service (name,r,info,"branch");

            //saving the Product
            databaseService.child(id).setValue(service);

            //setting edittext to blank again
            requiredInfo.setText("");
            serviceName.setText("");
            rate.setText("");

            Toast.makeText(this,"Service added",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "(PLEASE ENTER VALID INFORMATION!)", Toast.LENGTH_LONG).show();
        }

    }

    private boolean correctInput(){
        //validation check
        if(serviceName.getText().toString().trim().matches("-?\\d+")){  // If the name is just a number than error
            serviceName.setError("Name cannot just be a number");
            return false;
        }else if(!rate.getText().toString().trim().matches("^\\d*\\.\\d+|\\d+\\.\\d*|\\d*$")){  // If the price is not a positive number than error
            rate.setError("Rate has to be a positive number");
            return false;
        }//else if(info validation){}

        return true;
    }
}
