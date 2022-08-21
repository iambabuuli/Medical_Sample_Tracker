package com.medicalsampletracker.app;

public class UserAccountInformation {

    String first_name;
    String last_name;
    String email;
    String phone_number;
    String accountType;
    String profile_picture_path;
    String profile_picture_address;

    public UserAccountInformation(String first_name, String last_name, String email, String phone_number, String accountType, String profile_picture_path, String profile_picture_address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.accountType = accountType;
        this.profile_picture_path = profile_picture_path;
        this.profile_picture_address = profile_picture_address;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getProfile_picture_path() {
        return profile_picture_path;
    }

    public void setProfile_picture_path(String profile_picture_path) {
        this.profile_picture_path = profile_picture_path;
    }

    public String getProfile_picture_address() {
        return profile_picture_address;
    }

    public void setProfile_picture_address(String profile_picture_address) {
        this.profile_picture_address = profile_picture_address;
    }
}
