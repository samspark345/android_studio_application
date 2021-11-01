package com.example.byblosmobile;

public class Service {

    private String name;
    private String rate;
    private String branch;
    private String requiredInfo;

    //constructor

    public Service(){}
    public Service(String name,String rate,String requiredInfo, String branch){
        this.name = name;
        this.rate = rate;
        this.branch = branch;
        this.requiredInfo = requiredInfo;
    }

    //setter and getter


    public void setName(String name){
        this.name = name;
    }

    public void setRate(String rate){
        this.rate = rate;
    }

    public void setBranch(String branch){
        this.branch = branch;
    }

    public void setRequiredInfo(String RequiredInfo){this.requiredInfo = requiredInfo; }

    public String getName(){
        return name;
    }

    public String getRate(){
        return rate;
    }

    public String getBranch(){
        return branch;
    }
    public String getRequiredInfo(){return requiredInfo;}

}
