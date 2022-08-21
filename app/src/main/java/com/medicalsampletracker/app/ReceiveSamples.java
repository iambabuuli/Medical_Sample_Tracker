package com.medicalsampletracker.app;

import android.app.AlertDialog;
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

import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ReceiveSamples extends AppCompatActivity implements View.OnClickListener{

    EditText sample_id, lab_notes;
    MaterialSpinner sampleStatus, results;
    Button receive_samples_button, updateReceivedSamplesButton, scanCode;
    SharedPreferences sharedPreferences;
    IntentIntegrator  intentIntegrator;

    Toolbar toolbar;

    String sampleId;
    String sampleStatusString;
    String resultsString;
    String labNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_samples);

        initialize();

        //IntentIntegrator method for the barcode scanner
        intentIntegrator();

        intentData();

        scanCode.setOnClickListener(this);
        updateReceivedSamplesButton.setOnClickListener(this);
        receive_samples_button.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());

    }


    public void initialize(){

        sample_id = findViewById(R.id.receive_sample_id_text);
        sampleStatus = findViewById(R.id.receive_sample_status_materialSpinner);
        results = findViewById(R.id.receive_results_materialSpinner);
        lab_notes = findViewById(R.id.receive_lab_notes_text);

        scanCode = findViewById(R.id.scan_bar_code_button_to_receive_id);
        sharedPreferences = getSharedPreferences("LOGIN_PREFERENCES", MODE_PRIVATE);

        updateReceivedSamplesButton = findViewById(R.id.update_receive_sample_button);
        receive_samples_button = findViewById(R.id.receive_sample_button);

        toolbar = findViewById(R.id.receive_samples_toolbar);
        setSupportActionBar(toolbar);
    }

    public void intentData(){

        Intent intent = getIntent();

        if(intent.hasExtra("sample_id")){

            updateReceivedSamplesButton.setVisibility(View.VISIBLE);
            receive_samples_button.setVisibility(View.GONE);

            sample_id.setText(intent.getStringExtra("sample_id"));
            lab_notes.setText(intent.getStringExtra("lab_notes"));

        }

    }

    public void intentIntegrator(){

        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scan barcode");

    }

    public void receiveSamplesAction(){

        try{

            sampleId = sample_id.getText().toString();
            sampleStatusString = sampleStatus.getSelectedItem().toString();
            resultsString = results.getSelectedItem().toString();
            labNotes = lab_notes.getText().toString();

            receiveSamples(sampleId, sampleStatusString, resultsString, labNotes);

        }catch (Exception e){

            Toast.makeText(ReceiveSamples.this, "All the necessary fields must be selected.", Toast.LENGTH_LONG).show();
        }

    }

    public void updateSamplesAction(){

        try{

            sampleId = sample_id.getText().toString();
            sampleStatusString = getIntent().getStringExtra("sample_status");
            resultsString = results.getSelectedItem().toString();
            labNotes = lab_notes.getText().toString();

            updateReceiveSamples(sampleId, sampleStatusString, resultsString, labNotes);

        }catch (Exception e){

            Toast.makeText(ReceiveSamples.this, "All the necessary options must be selected.", Toast.LENGTH_LONG).show();
        }


    }

    private void updateReceiveSamples(String sampleId, String sampleStatus, String resultsText, String labNotes) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.update_received_sample, response -> {

            Log.e("Response Error ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    displayAlertMessage(message);

                }else if(success.equals("0")){

                    displayAlertMessage(message);
                }
            } catch (JSONException e) {

                displayAlertMessage("Internal system error.");
                Log.e("Error", response);
            }

        }, error -> displayAlertMessage("System error occurred.")){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("sample_id", sampleId);
                params.put("sample_status", sampleStatus);
                params.put("received_by", sharedPreferences.getString("last_name", ""));
                params.put("results", resultsText);
                params.put("lab_notes", labNotes);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void receiveSamples(String sampleId, String sampleStatus, String resultsText, String labNotes) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.receive_sample_url, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    displayAlertMessage(message);

                }else if(success.equals("0")){

                    displayAlertMessage(message);
                    Log.e("TAG", response);
                }
            } catch (JSONException e) {

                displayAlertMessage("Internal system error.");
                Log.e("Error", response);
            }

        }, error -> displayAlertMessage("System error occurred.")){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("sample_id", sampleId);
                params.put("sample_status", sampleStatus);
                params.put("received_by", sharedPreferences.getString("last_name", ""));
                params.put("receivers_phone_number", sharedPreferences.getString("phone_number", ""));
                params.put("results", resultsText);
                params.put("lab_notes", labNotes);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if( result != null){

            if (result.getContents() == null){

                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();

            }else {

                sample_id.setText(result.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.scan_bar_code_button_to_receive_id){
            intentIntegrator.initiateScan();
        }

        if(v.getId() == R.id.update_receive_sample_button){

            updateSamplesAction();
        }

        if(v.getId() == R.id.receive_sample_button){
            receiveSamplesAction();
        }
    }
}
