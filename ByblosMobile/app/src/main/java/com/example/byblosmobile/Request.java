package com.example.byblosmobile;

public class Request {

    private String requestName;
    private String customerName;
    private String serviceName;
    private String status;

    public Request(String requestName,String customerName,String serviceName,String status){
        this.customerName = customerName;
        this.serviceName = serviceName;
        this.requestName = requestName;
        this.status = status;
    }

    public String getName(){return requestName;}
    public String getCustomerName(){return customerName;}
    public String getServiceName(){return serviceName;}

}
