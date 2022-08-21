package com.medicalsampletracker.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class StatisticalData extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView total_samples, negative_samples, positive_samples, pending_samples, data_based_on_location_text;
    Button get_data_based_on_location_button, get_Pie_Chart_data_based_on_location_button;
    MaterialSpinner get_location_Spinner, get_suspected_disease_Spinner, get_results_Spinner,
    get_Pie_Chart_location_Spinner, get_Pie_Chart_results_location_Spinner;
    ArrayList<String> list_Address, list_Suspected_Diseases;

    PieChart pieChart_address;
    PieData pieData_address;
    PieDataSet pieDataSet_address;

    static List<PieEntry> pieEntries = null;
    ArrayList pieEntryLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical_data);

        initialize();

        tryLoadingData();

        toolbar.setNavigationOnClickListener(v -> finish());

        get_data_based_on_location_button.setOnClickListener(this);
        get_Pie_Chart_data_based_on_location_button.setOnClickListener(this);

    }

    private void initialize() {

        total_samples = findViewById(R.id.statistical_data_total_samples_texView);
        pending_samples = findViewById(R.id.statistical_data_pending_samples_texView);
        negative_samples = findViewById(R.id.statistical_data_negative_samples_texView);
        positive_samples = findViewById(R.id.statistical_data_positive_samples_texView);
        data_based_on_location_text = findViewById(R.id.statistical_data_based_on_location_texView);
        get_data_based_on_location_button = findViewById(R.id.statistical_data_get_data_based_on_location_button);
        get_location_Spinner = findViewById(R.id.statistical_data_get_address_Spinner);
        get_suspected_disease_Spinner = findViewById(R.id.statistical_data_get_suspected_disease_Spinner);
        get_results_Spinner = findViewById(R.id.statistical_data_get_results_Spinner);
        get_Pie_Chart_data_based_on_location_button = findViewById(R.id.statistical_data_pieChart_get_data_based_on_location_button);
        get_Pie_Chart_location_Spinner = findViewById(R.id.statistical_data_pieChart_get_location_Spinner);
        get_Pie_Chart_results_location_Spinner = findViewById(R.id.statistical_data_pieChart_get_results_Spinner);


        list_Address = new ArrayList<>();
        list_Suspected_Diseases = new ArrayList<>();

        pieEntries = new ArrayList<>();
        pieEntryLabels = new ArrayList();

        pieChart_address = findViewById(R.id.pieChart);

        toolbar = findViewById(R.id.statistical_data_toolbar);
        setSupportActionBar(toolbar);

    }

    private void loadDataForTotalSamples() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.total_samples_number,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");

                        if (success.equals("1")) {

                            total_samples.setText(message);

                        }else if(success.equals("0")){

                            total_samples.setText(message);
                        }

                    } catch (JSONException e) {

                        total_samples.setText("Error.");
                        Log.d("TAG", response);
                    }

                }, error -> {

            total_samples.setText("...");
            Log.d("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void loadDataForPendingSamples() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.pending_samples_number,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");

                        if (success.equals("1")) {

                            pending_samples.setText(message);

                        }else if(success.equals("0")){

                            pending_samples.setText(message);

                        }

                    } catch (JSONException e) {

                        pending_samples.setText("Error.");
                        Log.d("TAG", response);
                    }

                }, error -> {

            pending_samples.setText("...");
            Log.d("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadDataForNegativeSamples() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.negative_samples_number,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");

                        if (success.equals("1")) {

                            negative_samples.setText(message);

                        }else if(success.equals("0")){

                            negative_samples.setText(message);

                        }

                    } catch (JSONException e) {

                        negative_samples.setText("Error.");
                        Log.d("TAG", response);
                    }

                }, error -> {

            negative_samples.setText("...");
            Log.d("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadDataForPositiveSamples() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.positive_samples_number,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");

                        if (success.equals("1")) {

                            positive_samples.setText(message);

                        }else if(success.equals("0")){

                            positive_samples.setText(message);

                        }

                    } catch (JSONException e) {

                        positive_samples.setText("Error.");
                        Log.d("TAG", response);
                    }

                }, error -> {

            positive_samples.setText("...");
            Log.d("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadDataForAddressToSpinner() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.get_all_existing_addresses,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");
                        JSONArray jsonArray = jsonObject.getJSONArray("address");
                        String[] address_values = new String[jsonArray.length()];

                        if (success.equals("1")) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                address_values[i] = object.getString("address");

                            }

                            get_location_Spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.material_spinner_layout, address_values));
                            get_Pie_Chart_location_Spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.material_spinner_layout, address_values));
                        }

                        else if (success.equals("0")) {

                           get_location_Spinner.setError(message);
                        }

                    } catch (JSONException e) {

                        get_location_Spinner.setError("Internal error.");
                        Log.d("TAG", response);
                    }

                }, error -> {

            get_location_Spinner.setError("Cannot load list.");
            Log.d("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadDataForSuspectedDisease() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.get_all_existing_suspected_diseases,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");
                        JSONArray jsonArray = jsonObject.getJSONArray("suspected_disease");
                        String[] values_suspected_disease = new String[jsonArray.length()];

                        if (success.equals("1")) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                values_suspected_disease[i] = object.getString("suspected_disease");
                            }

                            get_suspected_disease_Spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.material_spinner_layout, values_suspected_disease));
                        }

                        else if (success.equals("0")) {

                           get_suspected_disease_Spinner.setError(message);
                        }

                    } catch (JSONException e) {

                        get_suspected_disease_Spinner.setError("Internal error.");
                        Log.d("TAG", response);
                    }

                }, error -> {

            get_suspected_disease_Spinner.setError("Cannot load data.");
            Log.d("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void displayAlertMessage(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.statistical_data_get_data_based_on_location_button)
            verify_and_get_data_based_on_location();

        if(v.getId() == R.id.statistical_data_pieChart_get_data_based_on_location_button){

            pieEntries.clear();
            verify_and_get_Pie_Chart_data_based_on_location();

        }

    }

    private void verify_and_get_Pie_Chart_data_based_on_location() {

        try{

            get_Pie_Chart_location_Spinner.getSelectedItem().toString();
            get_Pie_Chart_results_location_Spinner.getSelectedItem().toString();

            get_Data_Entries_For_PieChart_Address();

        }catch (Exception e){

            Toast.makeText(StatisticalData.this, "Select all the fields.", Toast.LENGTH_LONG).show();
        }
    }

    private void verify_and_get_data_based_on_location() {

        try{

            get_suspected_disease_Spinner.getSelectedItem().toString();
            get_location_Spinner.getSelectedItem().toString();
            get_results_Spinner.getSelectedItem().toString();

            get_data_based_on_location_action();

        }catch (Exception e){

            Toast.makeText(StatisticalData.this, "Select all the required fields.", Toast.LENGTH_LONG).show();
        }
    }

    private void get_data_based_on_location_action() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.get_count_for_results_and_diseases_in_location,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");

                        if (success.equals("1")) {

                            progressDialog.dismiss();
                            data_based_on_location_text.setText(message);

                        } else if (success.equals("0")) {

                            progressDialog.dismiss();
                            data_based_on_location_text.setText(message);
                        }

                    } catch (JSONException e) {

                        progressDialog.dismiss();
                        data_based_on_location_text.setText("Error.");
                        Log.d("TAG", response);
                    }

                }, error -> {

            progressDialog.dismiss();
            data_based_on_location_text.setText("...");
            Log.d("TAG", error.getMessage());
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("results", get_results_Spinner.getSelectedItem().toString());
                params.put("suspected_disease", get_suspected_disease_Spinner.getSelectedItem().toString());
                params.put("address", get_location_Spinner.getSelectedItem().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

//    private void get_Pie_Chart_data(){
//
//        get_Data_Entries_For_PieChart_Address();
//
////        get_Testing();
//
//        pieDataSet_address = new PieDataSet(pieEntries, "");
//        pieData_address = new PieData(pieDataSet_address);
//        pieChart_address.setData(pieData_address);
//
//        pieDataSet_address.setColors(ColorTemplate.MATERIAL_COLORS);
//        pieDataSet_address.setSliceSpace(2f);
//        pieDataSet_address.setValueTextColor(Color.WHITE);
//        pieDataSet_address.setValueTextSize(10f);
//        pieDataSet_address.setSliceSpace(2f);
//
//    }

    public void get_Testing(){

//        pieEntries.add(new PieEntry(2f, "Man" ));
//        pieEntries.add(new PieEntry(5f , "Tree"));
//        pieEntries.add(new PieEntry(9f, "Goat"));
//        pieEntries.add(new PieEntry(3f, "Sheep"));
//        pieEntries.add(new PieEntry(6f, "Dog"));
//        pieEntries.add(new PieEntry(4f, "Pot"));

        for(int i = 0; i < 5; i++){

            pieEntries.add(new PieEntry(i, "Man" ));
        }
    }

    private void get_Data_Entries_For_PieChart_Address(){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.get_Data_Entries_For_PieChart,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");
                        JSONArray jsonArray = jsonObject.getJSONArray("suspected_disease");

                        String[] suspected_disease_values = new String[jsonArray.length()];
                        int[] address_counts_values = new int[jsonArray.length()];

                        if (success.equals("1")) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                suspected_disease_values[i] = object.getString("suspected_disease");
                                address_counts_values[i] = object.getInt("address_counts");

                                pieEntries.add(new PieEntry(address_counts_values[i], suspected_disease_values[i]));

                            }

                            pieDataSet_address = new PieDataSet(pieEntries, "");
                            pieData_address = new PieData(pieDataSet_address);
                            pieChart_address.setData(pieData_address);

                            pieDataSet_address.setColors(ColorTemplate.MATERIAL_COLORS);
                            pieDataSet_address.setSliceSpace(2f);
                            pieDataSet_address.setValueTextColor(Color.WHITE);
                            pieDataSet_address.setValueTextSize(10f);

                            progressDialog.dismiss();

                            displayAlertMessage("Tap screen to display chart. \n\nIf nothing displays then no data is available.");

                        }
                        else if (success.equals("0")) {

                            progressDialog.dismiss();
                            displayAlertMessage(message);
                            pieChart_address.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {

                        progressDialog.dismiss();
                        displayAlertMessage("Internal error. Cannot load chart.");
                        Log.d("TAG", response);
                    }

                }, error -> {

            progressDialog.dismiss();
            displayAlertMessage("Cannot load chart.");
            Log.d("TAG", error.getMessage());

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("results", get_Pie_Chart_results_location_Spinner.getSelectedItem().toString());
                params.put("address", get_Pie_Chart_location_Spinner.getSelectedItem().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void tryLoadingData(){

        try{

            loadDataForPositiveSamples();
            loadDataForTotalSamples();
            loadDataForNegativeSamples();
            loadDataForPendingSamples();

            loadDataForAddressToSpinner();
            loadDataForSuspectedDisease();


        }catch (Exception e){

            tryAgainAlertDialog("System error.","Failed to load information. Do you want to try again ?");
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
