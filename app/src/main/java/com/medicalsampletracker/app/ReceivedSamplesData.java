package com.medicalsampletracker.app;

public class ReceivedSamplesData {

    String sampleId;
    String patientsIDNumber;
    String disease;
    String sampleType;
    String registeredBy;
    String receivedBy;
    String sampleStatus;
    String results;
    String dateReceived;
    String labNotes;
    String registers_phone_number;
    String receivers_phone_number;

    public ReceivedSamplesData(String sampleId, String patientsIDNumber, String disease, String sampleType, String registeredBy, String receivedBy, String sampleStatus, String results, String dateReceived, String labNotes, String registers_phone_number, String receivers_phone_number) {
        this.sampleId = sampleId;
        this.patientsIDNumber = patientsIDNumber;
        this.disease = disease;
        this.sampleType = sampleType;
        this.registeredBy = registeredBy;
        this.receivedBy = receivedBy;
        this.sampleStatus = sampleStatus;
        this.results = results;
        this.dateReceived = dateReceived;
        this.labNotes = labNotes;
        this.registers_phone_number = registers_phone_number;
        this.receivers_phone_number = receivers_phone_number;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getPatientsIDNumber() {
        return patientsIDNumber;
    }

    public void setPatientsIDNumber(String patientsIDNumber) {
        this.patientsIDNumber = patientsIDNumber;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getLabNotes() {
        return labNotes;
    }

    public void setLabNotes(String labNotes) {
        this.labNotes = labNotes;
    }

    public String getRegisters_phone_number() {
        return registers_phone_number;
    }

    public void setRegisters_phone_number(String registers_phone_number) {
        this.registers_phone_number = registers_phone_number;
    }

    public String getReceivers_phone_number() {
        return receivers_phone_number;
    }

    public void setReceivers_phone_number(String receivers_phone_number) {
        this.receivers_phone_number = receivers_phone_number;
    }
}
