package com.example.byblosmobile;

public class Customer extends User{

    //the attribute of customer need to have
    private String userName;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String address;


    public Customer(String userName, String password, String role) {
        super(userName, password, role);
    }


}
