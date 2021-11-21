package com.example.byblosmobile;

import java.util.ArrayList;

public class Employee extends User{

    private String branchName;
    private String branchAddress;
    private String branchPhoneNumber;
    private ArrayList<String> service;
    private ArrayList<String> availability;
    private ArrayList<String> requests;


    public Employee(String userName, String password, String role) {
        super(userName, password, role);
        branchName = userName;
        branchAddress = null;
        branchPhoneNumber = null;
    }

    public Employee(String userName,String password, String role, String address, String phoneNumber){
        super(userName,password,role);
        this.branchAddress = address;
        this.branchPhoneNumber = phoneNumber;
        this.branchName = userName;
    }


        public Employee(String userName,String password, String role, String address, String phoneNumber, ArrayList<String> serviceList,ArrayList<String> availability,ArrayList<String> requests){
        super(userName,password,role);
        branchName = userName;
        this.branchPhoneNumber = phoneNumber;
        this.branchAddress = address;
        this.service = serviceList;
        this.availability = availability;
        this.requests = requests;
    }


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






}
