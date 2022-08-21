package com.medicalsampletracker.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

public class ManageAccount extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ArrayList<UserAccountInformation> list;
    UserAccountsListAdapter userAccountsListAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        initialize();

        tryLoadingAccounts();

        swipeRefreshLayout.setOnRefreshListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void initialize(){

        toolbar = findViewById(R.id.manage_user_toolbar);
        list = new ArrayList<>();
        userAccountsListAdapter = new UserAccountsListAdapter(list);
        recyclerView = findViewById(R.id.user_accounts_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh_accounts_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(ManageAccount.this));
        setSupportActionBar(toolbar);

    }

    public void getUserAccounts(){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.display_user_accounts_list, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    JSONArray jsonArray = jsonObject.getJSONArray("users");

                    for (int i = 0; i < jsonArray.length() ; i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String first_name = jsonObject1.getString("first_name");
                        String last_name = jsonObject1.getString("last_name");
                        String email = jsonObject1.getString("email");
                        String phone_number = jsonObject1.getString("phone_number");
                        String accountType = jsonObject1.getString("account_type");
                        String profile_picture_path = jsonObject1.getString("profile_picture_path");
                        String profile_picture_address = References.address + profile_picture_path;

                        list.add(new UserAccountInformation(first_name, last_name, email, phone_number, accountType, profile_picture_path, profile_picture_address));
                    }

                    recyclerView.setAdapter(userAccountsListAdapter);
                    progressDialog.dismiss();

                }else if(success.equals("0")){

                    progressDialog.setCancelable(false);
                    Toast.makeText(ManageAccount.this, message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {

                progressDialog.dismiss();
                displayAlertMessage("Internal server error.");
                Log.e("TAG",response);
            }

        }, error -> {

            progressDialog.dismiss();
            displayAlertMessage("Cannot display accounts.");
            Log.e("TAG", error.getMessage());
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.manage_users_account_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.manage_account_search_menu_item);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                userAccountsListAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.manage_account_create_account_menu_item)
            startActivity(new Intent(getApplicationContext(), CreateAccount.class));


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
        getUserAccounts();
        swipeRefreshLayout.setRefreshing(false);

    }

    public void tryLoadingAccounts(){

        try{

            getUserAccounts();

        }catch (Exception e){

            tryAgainAlertDialog();

        }
    }

    public void tryAgainAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Failed to load the user accounts. Do you want to try again ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Retry", (dialog, which) -> tryLoadingAccounts());

        builder.setNegativeButton("Cancel", (dialog, which) -> finish());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
