package com.example.byblosmobile;

public class Request {

    private String customerName;
    private String serviceName;
    private String status;

    public Request(){}

    public Request(String serviceName,String customerName,String status){
        this.customerName = customerName;
        this.serviceName = serviceName;
        this.status = status;
    }

    public String getServiceName(){return serviceName;}
    public String getCustomerName(){return customerName;}
    public String getStatus(){return status;}
    public void setStatus(String status){
        this.status = status;
    }

    public String toString(){
        String output = getServiceName() + "Requests from " +getCustomerName() + " Status: " + getStatus();
        return output;
    }

}
