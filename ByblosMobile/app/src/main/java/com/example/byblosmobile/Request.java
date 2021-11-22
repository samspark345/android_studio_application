package com.example.byblosmobile;

public class Request {

    private String requestName;
    private String customerName;
    private String serviceName;
    private String status;

    public Request(String serviceName,String customerName,String status){
        this.customerName = customerName;
        this.serviceName = serviceName;
        this.status = status;
    }

    public String getName(){return requestName;}
    public String getCustomerName(){return customerName;}
    public String getServiceName(){return serviceName;}

}
