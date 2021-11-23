package com.example.byblosmobile;

import java.sql.Time;

public class TimeSlot {

    private String day;
    private int startHour;
    private int endHour;

    public TimeSlot(){

    }

    public TimeSlot(String day,int startHour, int endHour){
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getDay() {

        return day;
    }

    public void setDay(String day) {

        this.day = day;
    }

    public int getStartHour() {

        return startHour;
    }

    public void setStartHour(int startHour) {

        this.startHour = startHour;
    }

    public int getEndHour() {

        return endHour;
    }

    public void setFinishHour(int endHour) {

        this.endHour = endHour;
    }

    public String toString(){
        String output = getDay() + ", from: " + getStartHour() + " to " + getEndHour();
        return output;
    }
}
