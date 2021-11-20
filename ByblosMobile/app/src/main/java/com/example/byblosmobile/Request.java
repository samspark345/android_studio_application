package com.example.byblosmobile;

public class Request {

    private String requestName;
    private String customerName;
    private String serviceName;

    public Request(String requestName,String customerName,String serviceName){
        this.customerName = customerName;
        this.serviceName = serviceName;
        this.requestName = requestName;
    }

    public String getName(){return requestName;}
    public String getCustomerName(){return customerName;}
    public String getServiceName(){return serviceName;}

}
