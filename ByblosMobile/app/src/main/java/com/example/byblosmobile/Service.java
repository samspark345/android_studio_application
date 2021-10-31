package com.example.byblosmobile;

public class Service {


    private String categories;
    private int price;
    private String location;

    //constructor
    public Service(String categories,int price,String location){
        this.categories = categories;
        this.price = price;
        this.location = location;
    }

    //setter and getter

    public void setCategories(String categories){
        this.categories = categories;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getCategories(){
        return categories;
    }
    public int getPrice(){
        return price;
    }

    public String getLocation(){
        return location;
    }

}
