package com.medicalsampletracker.app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import fr.ganfra.materialspinner.MaterialSpinner;

public class RegisterSamples extends AppCompatActivity implements View.OnClickListener {

    MaterialSpinner sample_type, gender, hospital_patient;
    Button submitRegisteredSampleButton, updDateRegSamplesButton, scanBarCode;
    EditText sample_id,
            patients_ID_no,
            patients_Surname,
            patients_FirstName,
            patients_Date_of_Birth,
            patients_age,
            patients_address,
            hospital_or_folio_number,
            patients_phone_number,
            patients_email,
            suspected_disease,
            next_of_kin,
            clinical_Or_Drug_information,
            expected_date_of_return;


    SharedPreferences sharedPreferences;
    IntentIntegrator integrator;

    int year, month, dayOfMonth;

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
            expectedDateOfReturn ;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_samples);

        initialize();

        intentIntegrator();

        intentData();

        scanBarCode.setOnClickListener(this);
        expected_date_of_return.setOnClickListener(this);
        patients_Date_of_Birth.setOnClickListener(this);
        updDateRegSamplesButton.setOnClickListener(this);
        submitRegisteredSampleButton.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult results = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(results != null){

            if(results.getContents() == null){

                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();

            }else {

                sample_id.setText(results.getContents());
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void intentData(){

        Intent intent = getIntent();

        if(intent.hasExtra("sample_id")){

            sample_id.setText(intent.getExtras().getString("sample_id"));
            patients_ID_no.setText(intent.getStringExtra("patients_id_number"));
            patients_Surname.setText(intent.getStringExtra("patients_surname"));
            patients_FirstName.setText(intent.getStringExtra("patients_firstName"));
            patients_Date_of_Birth.setText(intent.getStringExtra("patients_date_of_birth"));
            patients_address.setText(intent.getStringExtra("patients_address"));
            patients_age.setText(intent.getStringExtra("patients_age"));
            hospital_or_folio_number.setText(intent.getStringExtra("hospital_or_folio_number"));
            patients_phone_number.setText(intent.getStringExtra("patients_phone_number"));
            patients_email.setText(intent.getStringExtra("patients_email"));
            suspected_disease.setText(intent.getStringExtra("suspected_disease"));
            next_of_kin.setText(intent.getStringExtra("next_of_kin"));
            expected_date_of_return.setText(intent.getStringExtra("expected_date_of_return"));
            clinical_Or_Drug_information.setText(intent.getStringExtra("clinical_or_drug_information"));

            gender.setEnabled(false);
            hospital_patient.setEnabled(false);
            sample_type.setEnabled(false);

            updDateRegSamplesButton.setVisibility(View.VISIBLE);
            submitRegisteredSampleButton.setVisibility(View.GONE);
        }

    }

    public void initialize(){

        sample_id = findViewById(R.id.register_sample_id_text);
        patients_ID_no = findViewById(R.id.register_patients_id_no_text);
        patients_Surname = findViewById(R.id.register_patients_surname_text);
        patients_FirstName = findViewById(R.id.register_patients_first_name_text);
        patients_Date_of_Birth = findViewById(R.id.register_patients_date_of_birth_text);
        gender = findViewById(R.id.register_patients_gender_Spinner);
        patients_address = findViewById(R.id.register_patients_address_text);
        patients_age = findViewById(R.id.register_patients_age_text);
        hospital_or_folio_number = findViewById(R.id.register_hospital_or_folio_number_text);
        patients_phone_number = findViewById(R.id.register_patients_phone_number_text);
        patients_email = findViewById(R.id.register_patients_email_text);
        hospital_patient = findViewById(R.id.register_hospital_patient_Spinner);
        suspected_disease = findViewById(R.id.register_suspected_disease_text);
        next_of_kin = findViewById(R.id.register_next_of_kin_text);
        sample_type = findViewById(R.id.register_sample_type_Spinner);
        clinical_Or_Drug_information = findViewById(R.id.register_clinical_or_drug_information_text);
        expected_date_of_return = findViewById(R.id.register_expected_date_of_return_text);

        updDateRegSamplesButton = findViewById(R.id.update_register_sample_button);
        submitRegisteredSampleButton = findViewById(R.id.submit_register_sample_button);
        scanBarCode = findViewById(R.id.scan_bar_code_button);

        sharedPreferences = getSharedPreferences("LOGIN_PREFERENCES", MODE_PRIVATE);

        toolbar = findViewById(R.id.register_samples_toolbar);

        setSupportActionBar(toolbar);
    }

    public void intentIntegrator(){

        integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);

    }

    public void verifyFieldsAndSubmitSamples(){

        sampleId = sample_id.getText().toString();
        patientsIDNo = patients_ID_no.getText().toString();
        patientsSurname = patients_Surname.getText().toString();
        patientsFirstName = patients_FirstName.getText().toString();
        patientsDateOfBirth = patients_Date_of_Birth.getText().toString();

        patientsAddress = patients_address.getText().toString();
        patientsAge = patients_age.getText().toString();
        hospitalOrFolioNumber = hospital_or_folio_number.getText().toString();
        patientsPhoneNumber = patients_phone_number.getText().toString();
        patientsEmail = patients_email.getText().toString();

        suspectedDisease = suspected_disease.getText().toString();
        nextOfKin = next_of_kin.getText().toString();

        clinicalOrDrugInformation = clinical_Or_Drug_information.getText().toString();
        expectedDateOfReturn = expected_date_of_return.getText().toString();

        if(sampleId.isEmpty()){

            displayAlertMessage("Please fill in the Sample Id.");
        }else if(patientsIDNo.isEmpty()){

            displayAlertMessage("Please fill in the Patient's ID Number.");

        }else if(patientsSurname.isEmpty()){

            displayAlertMessage("Please fill in the Patient's Surname.");

        }else if(patientsFirstName.isEmpty()){

            displayAlertMessage("Please fill in the Patient's First Name.");

        }else if(patientsDateOfBirth.isEmpty()){

            displayAlertMessage("Please fill in the Patient's date of Birth.");

        }else if(patientsAge.isEmpty()){

            displayAlertMessage("Please fill in the Patients Age.");

        }else if(patientsAddress.isEmpty()){

            displayAlertMessage("Please fill in the Patients Address.");

        }else if(hospitalOrFolioNumber.isEmpty()){

            displayAlertMessage("Please fill in the Hospital or Folio number.");

        }else if(patientsPhoneNumber.isEmpty()){

            displayAlertMessage("Please fill in the Patients phone number.");

        }else if(suspectedDisease.isEmpty()){

            displayAlertMessage("Please fill in the Suspected Disease.");

        }else if(nextOfKin.isEmpty()){

            displayAlertMessage("Please fill in the Next of Kin.");

        }else{

            try{

            sampleType = sample_type.getSelectedItem().toString();
            patientsGender = gender.getSelectedItem().toString();
            hospitalPatient = hospital_patient.getSelectedItem().toString();

            submitSample(sampleId, patientsIDNo, patientsSurname, patientsFirstName, patientsDateOfBirth, patientsGender, patientsAddress, patientsAge, hospitalOrFolioNumber, patientsPhoneNumber, patientsEmail, hospitalPatient, suspectedDisease, nextOfKin, sampleType, clinicalOrDrugInformation, expectedDateOfReturn);

            }catch (Exception e){

            Toast.makeText(RegisterSamples.this, "All necessary options must be selected.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void verifyFieldsAndUpdateSamples(){

        sampleId = sample_id.getText().toString();
        patientsIDNo = patients_ID_no.getText().toString();
        patientsSurname = patients_Surname.getText().toString();
        patientsFirstName = patients_FirstName.getText().toString();
        patientsDateOfBirth = patients_Date_of_Birth.getText().toString();
        patientsAddress = patients_address.getText().toString();
        patientsAge = patients_age.getText().toString();
        hospitalOrFolioNumber = hospital_or_folio_number.getText().toString();
        patientsPhoneNumber = patients_phone_number.getText().toString();
        patientsEmail = patients_email.getText().toString();
        suspectedDisease = suspected_disease.getText().toString();
        nextOfKin = next_of_kin.getText().toString();
        clinicalOrDrugInformation = clinical_Or_Drug_information.getText().toString();
        expectedDateOfReturn = expected_date_of_return.getText().toString();

        if(sampleId.isEmpty()){

            displayAlertMessage("Please fill in the Sample Id.");

        }else if(patientsIDNo.isEmpty()){

            displayAlertMessage("Please fill in the Patient's ID Number.");

        }else if(patientsSurname.isEmpty()){

            displayAlertMessage("Please fill in the Patient's Surname.");

        }else if(patientsFirstName.isEmpty()){

            displayAlertMessage("Please fill in the Patient's First Name.");

        }else if(patientsDateOfBirth.isEmpty()){

            displayAlertMessage("Please fill in the Patient's date of Birth.");

        }else if(patientsAge.isEmpty()){

            displayAlertMessage("Please fill in the Patients Age.");

        }else if(patientsAddress.isEmpty()){

            displayAlertMessage("Please fill in the Patients Address.");

        }else if(hospitalOrFolioNumber.isEmpty()){

            displayAlertMessage("Please fill in the Hospital or Folio number.");

        }else if(patientsPhoneNumber.isEmpty()){

            displayAlertMessage("Please fill in the Patients phone number.");

        }else if(suspectedDisease.isEmpty()){

            displayAlertMessage("Please fill in the Suspected Disease.");

        }else if(nextOfKin.isEmpty()){

            displayAlertMessage("Please fill in the Next of Kin.");

        }else{

            updateSample(sampleId, patientsIDNo, patientsSurname, patientsFirstName, patientsDateOfBirth, patientsAddress, patientsAge, hospitalOrFolioNumber, patientsPhoneNumber, patientsEmail, suspectedDisease, nextOfKin, clinicalOrDrugInformation, expectedDateOfReturn);

            startActivity(new Intent(RegisterSamples.this, RegisterSamplesHome.class));
            finish();

        }

    }

    private void updateSample(String sampleId, String patientsIDNo, String patientsSurname, String patientsFirstName, String patientsDateOfBirth, String patientsAddress, String patientsAge, String hospitalOrFolioNumber, String patientsPhoneNumber, String patientsEmail, String suspectedDisease, String nextOfKin, String clinicalOrDrugInformation, String expectedDateOfReturn) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.update_registered_sample, response -> {

            Log.e("Response Error", response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                }else if(success.equals("0")){

                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {

                Toast.makeText(this, "Internal system error.", Toast.LENGTH_LONG).show();
                Log.e("ERROR", response);
            }

        }, error -> {

            Toast.makeText(this, "System failed to update information.", Toast.LENGTH_LONG).show();
            Log.d("TAG", error.getMessage());

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("sample_id", sampleId);
                params.put("patients_Id_number", patientsIDNo);
                params.put("patients_surname", patientsSurname);
                params.put("patients_firstName", patientsFirstName);
                params.put("patients_date_of_birth", patientsDateOfBirth);
                params.put("patients_phone_number", patientsPhoneNumber);
                params.put("patients_email", patientsEmail);
                params.put("hospital_or_folio_number", hospitalOrFolioNumber);
                params.put("patients_address", patientsAddress);
                params.put("patients_age", patientsAge);
                params.put("suspected_disease", suspectedDisease);
                params.put("next_of_kin", nextOfKin);
                params.put("clinical_or_drug_information", clinicalOrDrugInformation);
                params.put("expected_date_of_return", expectedDateOfReturn);
                params.put("update_registered_samples", "update_registered_samples");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void submitSample(String sampleId, String patientsIDNo, String patientsSurname, String patientsFirstName, String patientsDateOfBirth, String patientsGender, String patientsAddress, String patientsAge, String hospitalOrFolioNumber, String patientsPhoneNumber, String patientsEmail, String hospitalPatient, String suspectedDisease, String nextOfKin, String sampleType, String clinicalOrDrugInformation, String expectedDateOfReturn ) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.register_sample_url, response -> {

            Log.e("Response Error", response);

            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                }else if(success.equals("0")){

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {

                displayAlertMessage("Internal system error.");
                Log.e("TAG", response);
            }

        }, error -> {

            displayAlertMessage("Cannot create record.");
            Log.e("TAG", error.getMessage());

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("sample_id", sampleId);
                params.put("patients_Id_number", patientsIDNo);
                params.put("patients_surname", patientsSurname);
                params.put("patients_firstName", patientsFirstName);
                params.put("patients_date_of_birth", patientsDateOfBirth);
                params.put("patients_gender", patientsGender);
                params.put("patients_phone_number", patientsPhoneNumber);
                params.put("patients_email", patientsEmail);
                params.put("hospital_or_folio_number", hospitalOrFolioNumber);
                params.put("hospital_patient", hospitalPatient);
                params.put("patients_address", patientsAddress);
                params.put("patients_age", patientsAge);
                params.put("suspected_disease", suspectedDisease);
                params.put("sample_type", sampleType);
                params.put("next_of_kin", nextOfKin);
                params.put("clinical_or_drug_information", clinicalOrDrugInformation);
                params.put("expected_date_of_return", expectedDateOfReturn);
                params.put("registered_by", sharedPreferences.getString("last_name", ""));
                params.put("registers_phone_number", sharedPreferences.getString("phone_number", ""));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void displayAlertMessage(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", (dialog, which) -> builder.setCancelable(true));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {

        if(v == scanBarCode)
            integrator.initiateScan();


        if(v == updDateRegSamplesButton)
            verifyFieldsAndUpdateSamples();


        if(v == submitRegisteredSampleButton)
            verifyFieldsAndSubmitSamples();

        if(v == patients_Date_of_Birth){

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> patients_Date_of_Birth.setText(year +"-"+ (month + 1) + "-" + dayOfMonth), year, month, dayOfMonth);

            datePickerDialog.show();
        }

        if( v == expected_date_of_return){

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> expected_date_of_return.setText(year +"-"+ (month + 1) + "-" + dayOfMonth), year, month, dayOfMonth);
            datePickerDialog.show();
        }
    }

}
