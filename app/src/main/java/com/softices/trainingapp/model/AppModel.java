package com.softices.trainingapp.model;

public class AppModel {

    private String ContactName;
    private String UserName;
    private String UserEmail;
    private String UserNumber;
    private byte[] UserImages;
    private String UserPassword;
    private String ContactNumber;

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getUserNumber() {
        return UserNumber;
    }

    public void setUserNumber(String userNumber) {
        UserNumber = userNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String username) {
        UserName = username;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public byte[] getUserImages(String string) {
        return UserImages;
    }

    public void setUserImages(byte[] userImages) {
        UserImages = userImages;
    }
}