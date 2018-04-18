package com.developer.nennenwodo.medmanager.model;


/**
 * Medication Model. Defines the medication entity and the actions that can be performed on the model
 */
public class Medication {
    private int id, interval;
    private String name, userID, description, startDate, startTime, endDate;

    public Medication(String userID, String name, String description, int interval, String startDate, String startTime, String endDate) {
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.interval = interval;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
    }


    public Medication(int id, String userID, String name, String description, int interval, String startDate, String startTime, String endDate) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.interval = interval;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Medication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
