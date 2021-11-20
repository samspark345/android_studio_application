package com.example.byblosmobile;

import java.util.ArrayList;

public class Employee extends User{

    private String branchName;
    private String branchAddress;
    private String branchPhoneNumber;

    private ArrayList<Service> branchServices;
    public Employee(String userName, String password, String role) {
        super(userName, password, role);
        branchServices = new ArrayList<Service>();
        branchName = userName;
        branchAddress = null;
        branchPhoneNumber = null;
    }

    public Employee(String userName,String password, String role, String address, String phoneNumber){
        super(userName,password,role);
        this.branchAddress = address;
        this.branchPhoneNumber = phoneNumber;
        this.branchName = userName;
        branchServices = new ArrayList<Service>();
    }

    //not sure whether need method or not
    /*public Employee(String userName,String password, String role, String address, String phoneNumber,ArrayList<TimeSlot> availability){
        super(userName,password,role);
        branchServices = new ArrayList<Service>();



    }*/


    //getter & setter
    public String getUserType() {
        return "employee";
    }
    public String getBranchName(){return branchName;}
    public void setBranchName(String newName){this.branchName = newName;}

    public String getBranchAddress(){return branchAddress;}
    public void setBranchAddress(String newAddress){this.branchAddress = newAddress;}

    public String getBranchPhoneNumber(){return branchPhoneNumber;}
    public void setBranchPhoneNumber(String newPhoneNumber){this.branchPhoneNumber = newPhoneNumber;}

    public ArrayList<Service> getBranchServices() {return branchServices;}
    public void setBranchServices(ArrayList<Service> newBranchService) {
        this.branchServices = newBranchService;
    }



}
