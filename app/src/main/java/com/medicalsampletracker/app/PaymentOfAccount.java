package com.medicalsampletracker.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentOfAccount extends AppCompatActivity implements View.OnClickListener{

    EditText patientsIDNumber, idNo, surName, firstName, title, postalAddress, telephoneNumber, email, employer, medicalAidName, cashReceiptNumber;
    Button createPayment, updatePayments;
    String patientsIdNumber, identificationNumber, firstNameText, surnameText, titleText, postalAddressText, telephoneNumberText, emailText, employerText, medicalAidNameText, cashReceiptNumberText;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_of_account);

        initialize();

        intentDataForUpdating();

        createPayment.setOnClickListener(this);
        updatePayments.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());

    }

    public void initialize(){

        patientsIDNumber = findViewById(R.id.payment_patients_id_number_text);
        idNo = findViewById(R.id.payment_id_no_text);
        surName = findViewById(R.id.payment_surname_text);
        firstName = findViewById(R.id.payment_firstName_text);
        title = findViewById(R.id.payment_title_text);
        postalAddress = findViewById(R.id.payment_postal_address_text);
        telephoneNumber =findViewById(R.id.payment_telephone_number_text);
        email = findViewById(R.id.payment_email_text);
        employer = findViewById(R.id.payment_employer_text);
        medicalAidName = findViewById(R.id.payment_medical_aid_name_text);
        cashReceiptNumber = findViewById(R.id.payment_cash_receipt_number_text);

        createPayment = findViewById(R.id.submit_payments_button);
        updatePayments = findViewById(R.id.update_payments_button);

        toolbar = findViewById(R.id.payment_of_account_toolbar);

    }

    public void intentDataForUpdating(){

        Intent intent = getIntent();
        if(intent.hasExtra("patients_id_number")){

            updatePayments.setVisibility(View.VISIBLE);
            createPayment.setVisibility(View.GONE);

            patientsIDNumber.setText(intent.getStringExtra("patients_id_number"));
            idNo.setText(intent.getStringExtra("id_no"));
            surName.setText(intent.getStringExtra("surname"));
            firstName.setText(intent.getStringExtra("first_name"));
            title.setText(intent.getStringExtra("title"));
            postalAddress.setText(intent.getStringExtra("postal_address"));
            telephoneNumber.setText(intent.getStringExtra("telephone_number"));
            email.setText(intent.getStringExtra("email"));
            employer.setText(intent.getStringExtra("employer"));
            medicalAidName.setText(intent.getStringExtra("medical_aid_name"));
            cashReceiptNumber.setText(intent.getStringExtra("cash_receipt_number"));

        }
    }

    public void makePayments(){

        patientsIdNumber = patientsIDNumber.getText().toString();
        identificationNumber = idNo.getText().toString();
        firstNameText = firstName.getText().toString();
        surnameText = surName.getText().toString();
        titleText = title.getText().toString();
        postalAddressText = postalAddress.getText().toString();
        telephoneNumberText = telephoneNumber.getText().toString();
        emailText = email.getText().toString();
        employerText = employer.getText().toString();
        medicalAidNameText =   medicalAidName.getText().toString();
        cashReceiptNumberText = cashReceiptNumber.getText().toString();

        if(patientsIdNumber.isEmpty()){

            displayAlertMessage("Patients ID number is required");

        }else if(identificationNumber.isEmpty()){

            displayAlertMessage("Identification number is required");

        }else if(firstNameText.isEmpty()){

            displayAlertMessage("First Name is required");

        }else if(surnameText.isEmpty()){

            displayAlertMessage("Surname is required");

        }else if(telephoneNumberText.isEmpty()){

            displayAlertMessage("Telephone number is required");

        }else if(cashReceiptNumberText.isEmpty()){

            displayAlertMessage("Cash receipt number is required");
        }else if(postalAddressText.isEmpty()){

            displayAlertMessage("Postal Address is required");
        }else {

            createPaymentAction(patientsIdNumber, identificationNumber, firstNameText, surnameText, titleText, postalAddressText, telephoneNumberText, emailText, employerText, medicalAidNameText, cashReceiptNumberText);
        }
        }

    public void updatePayments(){

        patientsIdNumber = patientsIDNumber.getText().toString();
        identificationNumber = idNo.getText().toString();
        firstNameText = firstName.getText().toString();
        surnameText = surName.getText().toString();
        titleText = title.getText().toString();
        postalAddressText = postalAddress.getText().toString();
        telephoneNumberText = telephoneNumber.getText().toString();
        emailText = email.getText().toString();
        employerText = employer.getText().toString();
        medicalAidNameText =   medicalAidName.getText().toString();
        cashReceiptNumberText = cashReceiptNumber.getText().toString();

        if(patientsIdNumber.isEmpty()){

            displayAlertMessage("Patients ID number is required");

        }else if(identificationNumber.isEmpty()){

            displayAlertMessage("Identification number is required");

        }else if(firstNameText.isEmpty()){

            displayAlertMessage("First Name is required");

        }else if(surnameText.isEmpty()){

            displayAlertMessage("Surname is required");

        }else if(telephoneNumberText.isEmpty()){

            displayAlertMessage("Telephone number is required");

        }else if(cashReceiptNumberText.isEmpty()){

            displayAlertMessage("Cash receipt number is required");
        }else if(postalAddressText.isEmpty()){

            displayAlertMessage("Postal Address is required");
        }else {

            updatePaymentAction(patientsIdNumber, identificationNumber, firstNameText, surnameText, titleText, postalAddressText, telephoneNumberText, emailText, employerText, medicalAidNameText, cashReceiptNumberText);
        }
    }

    private void updatePaymentAction(String patientsIdNumber, String identificationNumber, String firstNameText, String surnameText, String titleText, String postalAddressText, String telephoneNumberText, String emailText, String employerText, String medicalAidNameText, String cashReceiptNumberText) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating payments record...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.update_payment_of_account_url, response -> {
            try{

                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    progressDialog.dismiss();
                    Toast.makeText(PaymentOfAccount.this, message, Toast.LENGTH_LONG).show();

                }else if(success.equals("0")){

                    progressDialog.dismiss();
                    displayAlertMessage(message);

                }
            }catch (JSONException e){

                progressDialog.dismiss();
                displayAlertMessage("Internal system error.");
                Log.e("Error", response);
            }
        }, error -> {

            progressDialog.dismiss();
            displayAlertMessage("System error.");
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("patients_id_number", patientsIdNumber);
                params.put("id_number", identificationNumber);
                params.put("surname", surnameText);
                params.put("first_name", firstNameText);
                params.put("title", titleText);
                params.put("postal_address", postalAddressText);
                params.put("telephone_number", telephoneNumberText);
                params.put("email", emailText);
                params.put("employer", employerText);
                params.put("medical_aid_name", medicalAidNameText);
                params.put("cash_receipt_number", cashReceiptNumberText);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void createPaymentAction(String patientsIdNumber, String identificationNumber, String firstNameText, String surnameText, String titleText, String postalAddressText, String telephoneNumberText, String emailText, String employerText, String medicalAidNameText, String cashReceiptNumberText) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating payments record...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.payment_of_account_url, response -> {
            try{

                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    progressDialog.dismiss();
                    Toast.makeText(PaymentOfAccount.this, message, Toast.LENGTH_LONG).show();

                }else if(success.equals("0")){

                progressDialog.dismiss();
                displayAlertMessage(message);

                }
            }catch (JSONException e){

                progressDialog.dismiss();
                displayAlertMessage("Cannot create record.");
                Log.e("ERROR", response);
            }
        }, error -> {

            progressDialog.dismiss();
            displayAlertMessage("System error.");
            Log.e("ERROR", error.getMessage());
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("patients_id_number", patientsIdNumber);
                params.put("id_number", identificationNumber);
                params.put("surname", surnameText);
                params.put("first_name", firstNameText);
                params.put("title", titleText);
                params.put("postal_address", postalAddressText);
                params.put("telephone_number", telephoneNumberText);
                params.put("email", emailText);
                params.put("employer", employerText);
                params.put("medical_aid_name", medicalAidNameText);
                params.put("cash_receipt_number", cashReceiptNumberText);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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

        if(v.getId() == R.id.submit_payments_button){
            makePayments();
        }

        if(v.getId() == R.id.update_payments_button){
            updatePayments();
        }
    }
}


