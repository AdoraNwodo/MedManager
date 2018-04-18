package com.developer.nennenwodo.medmanager.model;

/**
 * User Model. Defines the ic_default_profile_image entity and the actions that can be performed on the model
 */
public class User {

    private String userID, userName, gender, birthday, notificationStatus;

    public User(){}

    public User(String userID, String userName, String gender, String birthday) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.birthday = birthday;
    }


    public User(String userID, String userName, String gender, String birthday, String notificationStatus) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.birthday = birthday;
        this.notificationStatus = notificationStatus;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String email) {
        this.birthday = email;
    }
}
