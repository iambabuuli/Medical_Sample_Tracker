package com.medicalsampletracker.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterSamplesHome extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    RegSamplesListAdapter adapter;
    ArrayList<RegisteredSamplesData> list;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_samples_home);

        initialize();

        tryLoadingData();

        swipeRefreshLayout.setOnRefreshListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());

    }

    public void initialize(){

        recyclerView = findViewById(R.id.list_registered_samples);
        swipeRefreshLayout = findViewById(R.id.refresh_registered_samples_SwipeRefreshLayout);
        toolbar = findViewById(R.id.register_samples_toolbar);
        setSupportActionBar(toolbar);

        list = new ArrayList<>();
        adapter = new RegSamplesListAdapter(list, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    public void onRefresh() {

        list.clear();
        loadRecords();
        swipeRefreshLayout.setRefreshing(false);

    }

    private void loadRecords() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading records...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.view_reg_sample_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);

                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                JSONArray jsonArray = jsonObject.getJSONArray("registered_samples");

                if(success.equals("1")){

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String sampleId = jsonObject1.getString("sample_id");
                        String patientsIDNumber = jsonObject1.getString("patient_id");
                        String patientsSurname = jsonObject1.getString("first_name");
                        String patientsFirstName = jsonObject1.getString("last_name");
                        String patientsDateOfBirth = jsonObject1.getString("date_of_birth");
                        String patientsGender = jsonObject1.getString("gender");
                        String patientsAddress = jsonObject1.getString("address");
                        String patientsAge = jsonObject1.getString("age");
                        String hospitalOrFolioNumber = jsonObject1.getString("hospital_number");
                        String patientsPhoneNumber = jsonObject1.getString("phone_number");
                        String patientsEmail = jsonObject1.getString("email");
                        String hospitalPatient = jsonObject1.getString("hospital_patient");
                        String suspectedDisease = jsonObject1.getString("suspected_disease");
                        String sampleType = jsonObject1.getString("sample_type");
                        String nextOfKin = jsonObject1.getString("next_of_kin");
                        String registeredBy = jsonObject1.getString("registered_by");
                        String isSampleReceived = jsonObject1.getString("is_sample_received");
                        String result = jsonObject1.getString("results");
                        String expectedDateOfReturn = jsonObject1.getString("expected_date_of_return");
                        String clinicalOrDrugInformation = jsonObject1.getString("clinical_information");
                        String date = jsonObject1.getString("date_registered");
                        String paid = jsonObject1.getString("paid");
                        String receivedBy = jsonObject1.getString("received_by");
                        String labNotes = jsonObject1.getString("lab_notes");
                        String registers_phone_number = jsonObject1.getString("registers_phone_number");
                        String receivers_phone_number = jsonObject1.getString("receivers_phone_number");

                        list.add(new RegisteredSamplesData(sampleId,patientsIDNumber, patientsSurname, patientsFirstName, patientsDateOfBirth, patientsGender, patientsAddress, patientsAge, hospitalOrFolioNumber, patientsPhoneNumber, patientsEmail, hospitalPatient, suspectedDisease, nextOfKin, sampleType, clinicalOrDrugInformation, expectedDateOfReturn, isSampleReceived, result, date, registeredBy, paid, receivedBy, labNotes, registers_phone_number, receivers_phone_number));

                    }
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();

                }else if(success.equals("0")){

                    progressDialog.dismiss();
                    displayAlertMessage(message);
                }

            } catch (JSONException e) {

                e.printStackTrace();
                progressDialog.dismiss();
                tryAgainAlertDialog("Internal error."," Do you want to try loading list again ?");
                Log.e("TAG", response);
            }
        }, error -> {

            progressDialog.dismiss();
            tryAgainAlertDialog("Cannot display list."," Do you want to try loading list again ?");
            Log.e("TAG", error.getMessage());
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.view_registered_samples_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.search_registered_samples);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(adapter != null){
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.register_create_new_record__menu_item){
            startActivity(new Intent(RegisterSamplesHome.this, RegisterSamples.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void tryLoadingData(){

        try{

            list.clear();
            loadRecords();

        }catch (Exception e){

            tryAgainAlertDialog("Error.","Failed to load list. Do you want to try again ?");
        }
    }

    public void tryAgainAlertDialog(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false);

        builder.setPositiveButton("Retry", (dialog, which) -> tryLoadingData());

        builder.setNegativeButton("Cancel", (dialog, which) -> finish());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
