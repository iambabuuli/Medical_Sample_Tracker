package com.medicalsampletracker.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Button registerSamplesHome, receiveSamplesHome, paymentsHomeButton, statisticalData;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();

        loginStatusCheck();

        privileges();

        registerSamplesHome.setOnClickListener(this);

        receiveSamplesHome.setOnClickListener(this);

        paymentsHomeButton.setOnClickListener(this);

        statisticalData.setOnClickListener(this);

    }

    public void initialize(){

        toolbar = findViewById(R.id.main_toolbar);
        registerSamplesHome = findViewById(R.id.register_samples_button_home);
        receiveSamplesHome = findViewById(R.id.receive_samples_button_home);
        paymentsHomeButton = findViewById(R.id.payments_home_button);
        statisticalData = findViewById(R.id.statistical_data_button);

        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("LOGIN_PREFERENCES", MODE_PRIVATE);
        toolbar.setSubtitle(sharedPreferences.getString("last_name", "").toUpperCase());

    }

    public void privileges(){

        String accountType = sharedPreferences.getString("account_type","");

        if(accountType.equals("Lab Worker") )

            registerSamplesHome.setEnabled(false);
//            paymentsHomeButton.setEnabled(false);

        if(accountType.equals("Standard") )
            receiveSamplesHome.setEnabled(false);
    }

    public void loginStatusCheck(){

        Intent intent = getIntent();

        String first_name = intent.getStringExtra("first_name");
        String last_name = intent.getStringExtra("last_name");
        String email = intent.getStringExtra("email");
        String phone_number = intent.getStringExtra("phone_number");

        String status = sharedPreferences.getString("status", "");


        //Log out of the account if the Shared Preference key for status is set to Log out.
        if(status.equals("LogOut")){

            Intent intent1 = new Intent(Home.this, LogIn.class);
            startActivity(intent1);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        if(!sharedPreferences.getString("account_type", "").equals("Administrator")){

         menu.findItem(R.id.manage_account_app_main).setVisible(false);

        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.manage_account_app_main)
            startActivity(new Intent(Home.this, ManageAccount.class));

        if(item.getItemId() == R.id.user_account_app_main)
            startActivity(new Intent(this, UserAccount.class));

        if(item.getItemId() == R.id.about_app_main)
            startActivity(new Intent(Home.this, AboutUs.class));

        if(item.getItemId() == R.id.logOut_app_main)
            logOutDialog();

        if(item.getItemId() == R.id.exit_app_main)
            exitAppDialog();

        return super.onOptionsItemSelected(item);
    }

    private void exitAppDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to close app ?");

        builder.setNegativeButton("No", (dialog, which) -> builder.setCancelable(true));

        builder.setPositiveButton("Yes", (dialog, which) -> finish());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void logOutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to Log Out ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.clear();
            editor.putString("status", "LogOut");
            editor.apply();

            Intent intent1 = new Intent(Home.this, LogIn.class);
            startActivity(intent1);
            finish();

        });

        builder.setNegativeButton("No", (dialog, which) -> builder.setCancelable(true));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void aboutAppDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Version.");
        builder.setMessage("Version build 1.0... \nCopyright All rights reserved.");

        builder.setNegativeButton("ok", (dialog, which) -> builder.setCancelable(true));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.register_samples_button_home)
            startActivity(new Intent(Home.this, RegisterSamplesHome.class));

        if(v.getId() == R.id.receive_samples_button_home)
            startActivity(new Intent(Home.this, ReceiveSamplesHome.class));

        if(v.getId() == R.id.payments_home_button)
            startActivity(new Intent(Home.this, PaymentsHome.class));

        if(v.getId() == R.id.statistical_data_button)
            startActivity(new Intent(Home.this, StatisticalData.class));


    }
}
