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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ganfra.materialspinner.MaterialSpinner;

public class UpdateUserAccount extends AppCompatActivity implements View.OnClickListener {

    EditText first_name, last_name, email, phone_number;
    MaterialSpinner accountType;
    Button updateAccountInformation;
    Toolbar toolbar;
    CircleImageView profile_picture;
    SharedPreferences sharedPreferences;

    String profilePictureAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_account);

        initialize();

        intentData();

        updateAccountInformation.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void updateAccount(){

        try{

            String email_text = email.getText().toString();
            String accountTypeString = accountType.getSelectedItem().toString();

            changeUserPrivileges(email_text,accountTypeString);

        }catch (Exception e){

        Toast.makeText(UpdateUserAccount.this, "Choose the new account type.", Toast.LENGTH_LONG).show();

        }

    }

    public void intentData(){

        Intent intent = getIntent();

        profilePictureAddress = intent.getStringExtra("profile_picture_address");

        intent.getStringExtra("account_type");

        first_name.setText(intent.getStringExtra("first_name"));
        last_name.setText(intent.getStringExtra("last_name"));
        email.setText(intent.getStringExtra("email"));
        phone_number.setText(intent.getStringExtra("phone_number"));

        email.setTextIsSelectable(false);

        if(!profilePictureAddress.isEmpty()){

            Picasso.get().load(profilePictureAddress).into(profile_picture);

        }else{

            profile_picture.setImageResource(R.drawable.ic_person_black_100dp);
        }
    }

    private void changeUserPrivileges(String email, String account_type) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.change_user_privileges, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    Toast.makeText(UpdateUserAccount.this, message, Toast.LENGTH_LONG).show();

                }else if(success.equals("0")){

                    displayAlertMessage(message);

                }

            } catch (JSONException e) {

                displayAlertMessage( "Internal system error.");
                e.printStackTrace();
                Log.d("TAG", response);
            }

        }, error -> displayAlertMessage("System failed.")){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("account_type", account_type);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void initialize(){

        first_name = findViewById(R.id.update_user_first_name_text);
        last_name = findViewById(R.id.update_user_last_name_text);
        email = findViewById(R.id.update_email_text);
        phone_number = findViewById(R.id.update_user_phone_number_text);
        accountType = findViewById(R.id.update_user_account_account_type);
        updateAccountInformation = findViewById(R.id.update_account_information_button);

        toolbar = findViewById(R.id.update_user_account_toolbar);

        profile_picture = findViewById(R.id.update_user_account_circleImageView);

        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("LOGIN_PREFERENCES", MODE_PRIVATE);
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

        if(v.getId() == R.id.update_account_information_button)
            updateAccount();
    }
}
