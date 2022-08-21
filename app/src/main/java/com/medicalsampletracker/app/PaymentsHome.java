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

public class PaymentsHome extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    PaymentOfAccountAdapter paymentOfAccountAdapter;
    ArrayList<PaymentOfAccountData> list;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_home);

        initialize();

        loadListData();

        swipeRefreshLayout.setOnRefreshListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());

    }

    private void loadListData() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.view_payment_of_account, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");
                JSONArray jsonArray = jsonObject.getJSONArray("payment_of_account");

                if (success.equals("1")){

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String patientIDNumber = jsonObject1.getString("patient_id");
                        String IDNumber = jsonObject1.getString("national_id_number");
                        String surname = jsonObject1.getString("first_name");
                        String firstName = jsonObject1.getString("last_name");
                        String title = jsonObject1.getString("title");
                        String postalAddress = jsonObject1.getString("postal_address");
                        String telephoneNUmber = jsonObject1.getString("phone_number");
                        String email = jsonObject1.getString("email");
                        String employer = jsonObject1.getString("employer");
                        String medicalAidName = jsonObject1.getString("medical_aid_name");
                        String cashReceiptNumber = jsonObject1.getString("cash_receipt_number");
                        String date_payed = jsonObject1.getString("date_payed");

                        list.add(new PaymentOfAccountData(patientIDNumber, IDNumber, surname, firstName, title, postalAddress, telephoneNUmber, email, employer, medicalAidName, cashReceiptNumber, date_payed));
                    }

                    recyclerView.setAdapter(paymentOfAccountAdapter);
                    progressDialog.dismiss();

                }else if(success.equals("0")){

                    progressDialog.dismiss();
                    displayAlertMessage(message);
                }

            } catch (JSONException e) {

                progressDialog.dismiss();
                displayAlertMessage("Internal error\nCannot display any list\nThis could be there are no registered records.");
                e.printStackTrace();
                Log.e("TAG", response);
            }
        }, error -> {

            progressDialog.dismiss();
            displayAlertMessage("Cannot display list.");
            Log.e("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void initialize(){

        swipeRefreshLayout = findViewById(R.id.refresh_payments_list);
        recyclerView = findViewById(R.id.payments_list_recyclerView);
        list = new ArrayList<>();
        paymentOfAccountAdapter = new PaymentOfAccountAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(PaymentsHome.this));

        toolbar = findViewById(R.id.payments_main_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.view_payment_of_account_menu, menu);
        MenuItem searchButton = menu.findItem(R.id.payment_of_account_bar_search);
        SearchView searchView = (SearchView) searchButton.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                paymentOfAccountAdapter.getFilter().filter(newText);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.payment_of_account_create_new_record__menu_item){

            startActivity(new Intent(PaymentsHome.this, PaymentOfAccount.class));
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

    @Override
    public void onRefresh() {

        list.clear();
        loadListData();
        swipeRefreshLayout.setRefreshing(false);

    }

}
