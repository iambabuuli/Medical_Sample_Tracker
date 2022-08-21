package com.medicalsampletracker.app;

public class PaymentOfAccountData {

    String patientsIdNumber;
    String idNumber;
    String surname;
    String firstName;
    String title;
    String postalAddress;
    String telephoneNumber;
    String email;
    String employer;
    String medicalAidName;
    String cashReceiptNumber;
    String date;

    public PaymentOfAccountData(String patientsIdNumber, String idNumber, String surname, String firstName, String title, String postalAddress, String telephoneNumber, String email, String employer, String medicalAidName, String cashReceiptNumber, String date) {
        this.patientsIdNumber = patientsIdNumber;
        this.idNumber = idNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.title = title;
        this.postalAddress = postalAddress;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.employer = employer;
        this.medicalAidName = medicalAidName;
        this.cashReceiptNumber = cashReceiptNumber;
        this.date = date;
    }

    public String getPatientsIdNumber() {
        return patientsIdNumber;
    }

    public void setPatientsIdNumber(String patientsIdNumber) {
        this.patientsIdNumber = patientsIdNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getMedicalAidName() {
        return medicalAidName;
    }

    public void setMedicalAidName(String medicalAidName) {
        this.medicalAidName = medicalAidName;
    }

    public String getCashReceiptNumber() {
        return cashReceiptNumber;
    }

    public void setCashReceiptNumber(String cashReceiptNumber) {
        this.cashReceiptNumber = cashReceiptNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
