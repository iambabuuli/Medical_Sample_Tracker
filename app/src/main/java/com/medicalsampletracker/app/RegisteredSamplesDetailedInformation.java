package com.medicalsampletracker.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisteredSamplesDetailedInformation extends AppCompatActivity {

    TextView sampleID, patientsIDNumber, surname, firstName, dateOfBirth, gender, address, age, hospitalNumber, phoneNumber,
            email, hospitalPatient, suspectedDisease, nextOfKin, sampleType, dateOfReturn, isSampleReceived, results, paid,
    dateRegistered, registeredBy, receivedBy, clinicalInformation, labNotes;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_samples_detailed_information);

        initialized();
        intentData();

        toolbar.setNavigationOnClickListener(v -> finish());

    }

    public void initialized(){

        sampleID = findViewById(R.id.show_detailed_sample_id_text);
        patientsIDNumber = findViewById(R.id.show_detailed_patients_id_number_text);
        surname = findViewById(R.id.show_detailed_patients_surname_text);
        firstName = findViewById(R.id.show_detailed_patients_firstName_text);
        dateOfBirth = findViewById(R.id.show_detailed_patients_date_of_birth_text);
        gender = findViewById(R.id.show_detailed_patients_gender_text);
        address = findViewById(R.id.show_detailed_patients_address_text);
        age = findViewById(R.id.show_detailed_patients_age_text);
        hospitalNumber = findViewById(R.id.show_detailed_hospital_or_folio_number_text);
        phoneNumber= findViewById(R.id.show_detailed_patients_phone_number_text);
        email = findViewById(R.id.show_detailed_patients_email_text);
        hospitalPatient = findViewById(R.id.show_detailed_hospital_patient_text);
        suspectedDisease = findViewById(R.id.show_detailed_suspected_disease_text);
        nextOfKin = findViewById(R.id.show_detailed_next_of_kin_text);
        sampleType = findViewById(R.id.show_detailed_sample_type_text);
        dateOfReturn = findViewById(R.id.show_detailed_expected_date_of_return_text);
        isSampleReceived = findViewById(R.id.show_detailed_is_sample_received_text);
        results = findViewById(R.id.show_detailed_results_text);
        paid = findViewById(R.id.show_detailed_paid_text);
        dateRegistered = findViewById(R.id.show_detailed_date_registered_text);
        registeredBy = findViewById(R.id.show_detailed_registered_by_text);
        receivedBy = findViewById(R.id.show_detailed_received_by_text);
        clinicalInformation = findViewById(R.id.show_detailed_clinical_or_drug_information_text);
        labNotes = findViewById(R.id.show_detailed_lab_notes_text);

        toolbar = findViewById(R.id.patients_information_toolbar);
        setSupportActionBar(toolbar);
    }

    public void intentData(){

        Intent intent = getIntent();

        sampleID.setText(intent.getStringExtra("sample_id"));
        patientsIDNumber.setText(intent.getStringExtra("patients_id_number"));
        surname.setText(intent.getStringExtra("patients_surname"));
        firstName.setText(intent.getStringExtra("patients_firstName"));
        dateOfBirth.setText(intent.getStringExtra("patients_date_of_birth"));
        gender.setText(intent.getStringExtra("patients_gender"));
        address.setText(intent.getStringExtra("patients_address"));
        age.setText(intent.getStringExtra("patients_age"));
        hospitalNumber.setText(intent.getStringExtra("hospital_or_folio_number"));
        phoneNumber.setText(intent.getStringExtra("patients_phone_number"));
        email.setText(intent.getStringExtra("patients_email"));
        hospitalPatient.setText(intent.getStringExtra("hospital_patient"));
        dateOfReturn.setText(intent.getStringExtra("expected_date_of_return"));
        clinicalInformation.setText(intent.getStringExtra("clinical_or_drug_information"));
        suspectedDisease.setText(intent.getStringExtra("suspected_disease"));
        nextOfKin.setText(intent.getStringExtra("next_of_kin"));
        sampleType.setText(intent.getStringExtra("sample_type"));
        registeredBy.setText(intent.getStringExtra("registered_by"));
        receivedBy.setText(intent.getStringExtra("received_by"));
        dateRegistered.setText(intent.getStringExtra("date_registered"));
        labNotes.setText(intent.getStringExtra("lab_notes"));
        results.setText(intent.getStringExtra("results"));
        paid.setText(intent.getStringExtra("paid"));
        isSampleReceived.setText(intent.getStringExtra("is_sample_received"));


    }
}
