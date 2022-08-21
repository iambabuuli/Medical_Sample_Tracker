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

public class ReceiveSamplesHome extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ReceivedSamplesAdapter adapter;
    ArrayList<ReceivedSamplesData> list;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_samples_home);

        initialize();

        loadRecords();

        swipeRefreshLayout.setOnRefreshListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());

    }

    public void initialize(){

        swipeRefreshLayout = findViewById(R.id.refresh_received_samples_SwipeRefreshLayout);
        recyclerView = findViewById(R.id.list_received_samples);
        toolbar = findViewById(R.id.received_samples_toolbar);
        list = new ArrayList<>();
        adapter = new ReceivedSamplesAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        setSupportActionBar(toolbar);

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.view_received_sample_url, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                JSONArray jsonArray = jsonObject.getJSONArray("received_samples");

                if(success.equals("1")){

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String sampleId = jsonObject1.getString("sample_id");
                        String patientsIDNumber = jsonObject1.getString("patient_id");
                        String disease = jsonObject1.getString("suspected_disease");
                        String sampleType = jsonObject1.getString("sample_type");
                        String registered_by = jsonObject1.getString("registered_by");
                        String received_by = jsonObject1.getString("received_by");
                        String sample_status = jsonObject1.getString("sample_status");
                        String results = jsonObject1.getString("results");
                        String received_on = jsonObject1.getString("date_received");
                        String lab_notes = jsonObject1.getString("lab_notes");
                        String registers_phone_number = jsonObject1.getString("registers_phone_number");
                        String receivers_phone_number = jsonObject1.getString("receivers_phone_number");

                        list.add(new ReceivedSamplesData(sampleId, patientsIDNumber, disease, sampleType, registered_by, received_by, sample_status, results, received_on, lab_notes, registers_phone_number, receivers_phone_number));

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
                displayAlertMessage("Internal error\nCannot display any list\nThis could be there are no registered records.");
                Log.e("TAG", response);
            }
        }, error -> {

            progressDialog.dismiss();
            displayAlertMessage("Cannot display list.");
            Log.e("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.view_received_samples_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.search_received_samples);
        SearchView searchText = (SearchView) searchMenuItem.getActionView();

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        if(item.getItemId() == R.id.receive_create_new_record__menu_item){
            startActivity(new Intent(ReceiveSamplesHome.this, ReceiveSamples.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayAlertMessage(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", (dialog, which) -> builder.setCancelable(true));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}

