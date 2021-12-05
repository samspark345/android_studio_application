package com.example.byblosmobile;

public class Request {

    private String branchName;
    private String serviceName;
    private String status;
    private String extraInfo;


    //request need have customer name
    //customer info
    //addition info
    //branch
    //status

    public Request(String serviceName,String branchName,String extraInfo,String status){
        this.serviceName = serviceName;
        this.branchName = branchName;
        this.extraInfo = extraInfo;
        this.status = status;
    }

    public String getServiceName(){return serviceName;}
    public String getBranchName(){return branchName;}
    public String getStatus(){return status;}
    public String getExtraInfo(){return extraInfo;}
    public void setStatus(String status){
        this.status = status;
    }

    public String toString(){
        String output = getServiceName() + "Requests for " +getBranchName() + " Status: " + getStatus();
        return output;
    }

}
