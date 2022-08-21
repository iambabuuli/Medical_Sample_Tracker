package com.medicalsampletracker.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    EditText email_login, password_login;
    Button login_button;
    SharedPreferences sharedPreferences;
    CheckBox login_checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initialize();

        checkLogInStatus();

        login_button.setOnClickListener(this);

    }

    public void initialize(){

        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        login_button = findViewById(R.id.login_button);
        login_checkbox = findViewById(R.id.login_checkbox);

        sharedPreferences = getSharedPreferences("LOGIN_PREFERENCES", MODE_PRIVATE);
    }

    public void checkLogInStatus(){

        String status = sharedPreferences.getString("status", "");
        Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

        if(status.equals("Keep LogIn")){

            Intent intent1 = new Intent(LogIn.this, Home.class);

            intent1.putExtra("first_name", sharedPreferences.getString("first_name", ""));
            intent1.putExtra("last_name", sharedPreferences.getString("last_name", ""));
            intent1.putExtra("email", sharedPreferences.getString("email", ""));
            intent1.putExtra("phone_number", sharedPreferences.getString("phone_number", ""));

            startActivity(intent1);

            finish();
        }
    }

    public void validateAndLogIn(){

        String email = email_login.getText().toString().trim();
        String password = password_login.getText().toString().trim();

        if(email.isEmpty()) {

            displayAlertMessage("Provide your registered E-mail.");

        }else if(password.isEmpty()){

            displayAlertMessage("Provide your password.");

        }else{
            //Call the Log In Method
            Login(email, password);
        }

    }

    public void Login(String email, String password) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.");
        progressDialog.show();
        progressDialog.setCancelable(false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, References.login_url,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String first_name = object.getString("first_name");
                                    String last_name = object.getString("last_name");
                                    String login_email = object.getString("email");
                                    String phone_number = object.getString("phone_number");
                                    String accountType = object.getString("account_type");
                                    String profile_picture_path = object.getString("profile_picture_path");
                                    String profile_picture_address = References.address + profile_picture_path;

                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    if(login_checkbox.isChecked()){

                                        editor.putString("status", "Keep LogIn");
                                        editor.putString("first_name", first_name);
                                        editor.putString("last_name", last_name);
                                        editor.putString("email", login_email);
                                        editor.putString("phone_number", phone_number);
                                        editor.putString("account_type", accountType);
                                        editor.putString("profile_picture_path", profile_picture_path);
                                        editor.putString("profile_picture_address", profile_picture_address);
                                        editor.apply();

                                        progressDialog.dismiss();

                                        Intent intent1 = new Intent(LogIn.this, Home.class);
                                        intent1.putExtra("first_name", first_name);
                                        intent1.putExtra("last_name", last_name);
                                        intent1.putExtra("email", login_email);
                                        intent1.putExtra("phone_number", phone_number);
                                        intent1.putExtra("account_type", accountType);
                                        startActivity(intent1);
                                        finish();

                                    }else{

                                        editor.putString("status", "Keep LogIn");
                                        editor.putString("first_name", first_name);
                                        editor.putString("last_name", last_name);
                                        editor.putString("email", login_email);
                                        editor.putString("phone_number", phone_number);
                                        editor.putString("account_type", accountType);
                                        editor.putString("profile_picture_path", profile_picture_path);
                                        editor.putString("profile_picture_address", profile_picture_address);
                                        editor.apply();

                                        progressDialog.dismiss();

                                        Intent intent1 = new Intent(LogIn.this, Home.class);
                                        intent1.putExtra("first_name", first_name);
                                        intent1.putExtra("last_name", last_name);
                                        intent1.putExtra("email", login_email);
                                        intent1.putExtra("phone_number", phone_number);
                                        intent1.putExtra("account_type", accountType);
                                        startActivity(intent1);
                                        finish();
                                    }
                                }
                            }
                            else if (success.equals("0")) {

                                progressDialog.dismiss();
                                displayAlertMessage(message);
                            }

                        } catch (JSONException e) {

                            progressDialog.dismiss();
                            displayAlertMessage("Internal Server error is preventing Log In. " + e.getMessage() + "\n"+ response);
                            Log.d("TAG", response);
                        }

                    }, error -> {

                    progressDialog.dismiss();
                    displayAlertMessage("Failed to Log In due to a technical problem.");
                    error.printStackTrace();
//                    Log.d("TAG", error.getMessage());
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
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

        if(v.getId() == R.id.login_button)
            validateAndLogIn();
    }
}
