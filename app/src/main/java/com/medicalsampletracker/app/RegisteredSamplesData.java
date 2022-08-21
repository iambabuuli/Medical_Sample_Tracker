package com.medicalsampletracker.app;

public class RegisteredSamplesData {

    String sampleId,
            patientsIDNo,
            patientsSurname,
            patientsFirstName,
            patientsDateOfBirth,
            patientsGender,
            patientsAddress,
            patientsAge,
            hospitalOrFolioNumber,
            patientsPhoneNumber,
            patientsEmail,
            hospitalPatient,
            suspectedDisease,
            nextOfKin,
            sampleType,
            clinicalOrDrugInformation,
            expectedDateOfReturn,
            isSampleReceived,
            results,
            dateRegistered,
            registeredBy,
            paid,
            receivedBy,
            labNotes,
            registers_phone_number,
            receivers_phone_number;

    public RegisteredSamplesData(String sampleId, String patientsIDNo, String patientsSurname, String patientsFirstName, String patientsDateOfBirth, String patientsGender, String patientsAddress, String patientsAge, String hospitalOrFolioNumber, String patientsPhoneNumber, String patientsEmail, String hospitalPatient, String suspectedDisease, String nextOfKin, String sampleType, String clinicalOrDrugInformation, String expectedDateOfReturn, String isSampleReceived, String results, String dateRegistered, String registeredBy, String paid, String receivedBy, String labNotes, String registers_phone_number, String receivers_phone_number) {
        this.sampleId = sampleId;
        this.patientsIDNo = patientsIDNo;
        this.patientsSurname = patientsSurname;
        this.patientsFirstName = patientsFirstName;
        this.patientsDateOfBirth = patientsDateOfBirth;
        this.patientsGender = patientsGender;
        this.patientsAddress = patientsAddress;
        this.patientsAge = patientsAge;
        this.hospitalOrFolioNumber = hospitalOrFolioNumber;
        this.patientsPhoneNumber = patientsPhoneNumber;
        this.patientsEmail = patientsEmail;
        this.hospitalPatient = hospitalPatient;
        this.suspectedDisease = suspectedDisease;
        this.nextOfKin = nextOfKin;
        this.sampleType = sampleType;
        this.clinicalOrDrugInformation = clinicalOrDrugInformation;
        this.expectedDateOfReturn = expectedDateOfReturn;
        this.isSampleReceived = isSampleReceived;
        this.results = results;
        this.dateRegistered = dateRegistered;
        this.registeredBy = registeredBy;
        this.paid = paid;
        this.receivedBy = receivedBy;
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

    public String getPatientsIDNo() {
        return patientsIDNo;
    }

    public void setPatientsIDNo(String patientsIDNo) {
        this.patientsIDNo = patientsIDNo;
    }

    public String getPatientsSurname() {
        return patientsSurname;
    }

    public void setPatientsSurname(String patientsSurname) {
        this.patientsSurname = patientsSurname;
    }

    public String getPatientsFirstName() {
        return patientsFirstName;
    }

    public void setPatientsFirstName(String patientsFirstName) {
        this.patientsFirstName = patientsFirstName;
    }

    public String getPatientsDateOfBirth() {
        return patientsDateOfBirth;
    }

    public void setPatientsDateOfBirth(String patientsDateOfBirth) {
        this.patientsDateOfBirth = patientsDateOfBirth;
    }

    public String getPatientsGender() {
        return patientsGender;
    }

    public void setPatientsGender(String patientsGender) {
        this.patientsGender = patientsGender;
    }

    public String getPatientsAddress() {
        return patientsAddress;
    }

    public void setPatientsAddress(String patientsAddress) {
        this.patientsAddress = patientsAddress;
    }

    public String getPatientsAge() {
        return patientsAge;
    }

    public void setPatientsAge(String patientsAge) {
        this.patientsAge = patientsAge;
    }

    public String getHospitalOrFolioNumber() {
        return hospitalOrFolioNumber;
    }

    public void setHospitalOrFolioNumber(String hospitalOrFolioNumber) {
        this.hospitalOrFolioNumber = hospitalOrFolioNumber;
    }

    public String getPatientsPhoneNumber() {
        return patientsPhoneNumber;
    }

    public void setPatientsPhoneNumber(String patientsPhoneNumber) {
        this.patientsPhoneNumber = patientsPhoneNumber;
    }

    public String getPatientsEmail() {
        return patientsEmail;
    }

    public void setPatientsEmail(String patientsEmail) {
        this.patientsEmail = patientsEmail;
    }

    public String getHospitalPatient() {
        return hospitalPatient;
    }

    public void setHospitalPatient(String hospitalPatient) {
        this.hospitalPatient = hospitalPatient;
    }

    public String getSuspectedDisease() {
        return suspectedDisease;
    }

    public void setSuspectedDisease(String suspectedDisease) {
        this.suspectedDisease = suspectedDisease;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getClinicalOrDrugInformation() {
        return clinicalOrDrugInformation;
    }

    public void setClinicalOrDrugInformation(String clinicalOrDrugInformation) {
        this.clinicalOrDrugInformation = clinicalOrDrugInformation;
    }

    public String getExpectedDateOfReturn() {
        return expectedDateOfReturn;
    }

    public void setExpectedDateOfReturn(String expectedDateOfReturn) {
        this.expectedDateOfReturn = expectedDateOfReturn;
    }

    public String getIsSampleReceived() {
        return isSampleReceived;
    }

    public void setIsSampleReceived(String isSampleReceived) {
        this.isSampleReceived = isSampleReceived;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
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
