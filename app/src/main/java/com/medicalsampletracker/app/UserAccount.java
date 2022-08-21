package com.medicalsampletracker.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAccount extends AppCompatActivity implements View.OnClickListener {

    EditText first_name, last_name, email, phone_number, current_password, new_password , confirm_password;
    Button updateAccountButton, changePhotoButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferenceEditor;
    CircleImageView profile_picture_update;
    Bitmap bitmap;
    Toolbar toolbar;

    String encodedImageStringString;
    String imageNameString;

    private static final int CHANGE_PROFILE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        initialize();

        changePhotoButton.setOnClickListener(this);

        updateAccountButton.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void uploadNewPhoto() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHANGE_PROFILE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHANGE_PROFILE_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null){

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                profile_picture_update.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();

            }

            imageNameString = "IMG_" + System.currentTimeMillis();
            encodedImageStringString = getEncodedImageString(bitmap);
        }
    }

    private String getEncodedImageString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public void initialize(){

        imageNameString = "";

        first_name = findViewById(R.id.update_account_first_name_text);
        last_name = findViewById(R.id.update_account_last_name_text);
        email = findViewById(R.id.update_account_email_text);
        phone_number = findViewById(R.id.update_account_phone_number_text);
        current_password = findViewById(R.id.update_account_current_password_text);
        new_password = findViewById(R.id.update_account_new_password_text);
        confirm_password = findViewById(R.id.update_account_confirm_password_text);

        updateAccountButton = findViewById(R.id.update_account_update_button);
        sharedPreferences = getSharedPreferences("LOGIN_PREFERENCES", MODE_PRIVATE);
        profile_picture_update = findViewById(R.id.my_account_profile_picture_circleImageView);

        changePhotoButton = findViewById(R.id.my_account_change_photo_button);

        sharedPreferenceEditor = sharedPreferences.edit();

        toolbar = findViewById(R.id.my_account_toolbar);
        setSupportActionBar(toolbar);

        //Set the current account information
        first_name.setText(sharedPreferences.getString("first_name", ""));
        last_name.setText(sharedPreferences.getString("last_name", ""));
        email.setText(sharedPreferences.getString("email", ""));
        phone_number.setText(sharedPreferences.getString("phone_number", ""));

        if(sharedPreferences.getString("profile_picture_address", "").isEmpty()){

            profile_picture_update.setImageResource(R.drawable.ic_person_black_100dp);

        } else {

            Picasso.get().load(sharedPreferences.getString("profile_picture_address", "")).into(profile_picture_update);
        }

    }

    public void updateButtonsAction(){

        String first_name_text = first_name.getText().toString();
        String last_name_text = last_name.getText().toString();
        String email_text = email.getText().toString();
        String phone_number_text = phone_number.getText().toString();
        String current_password_text = current_password.getText().toString();
        String new_password_text = new_password.getText().toString();
        String confirm_password_text = confirm_password.getText().toString();
        String imageName = imageNameString;
        String encodedImageString = encodedImageStringString;

        //Check to see if fields are not empty
        if(first_name_text.isEmpty()){

            displayAlertMessage("First name is required.");

        }else if(last_name_text.isEmpty()){

            displayAlertMessage("Last name is required.");

        }else if(email_text.isEmpty()){

            displayAlertMessage("Email is required.");

            //Check to see if the new password matches with the confirmed password

        }else if(phone_number_text.isEmpty()){

            displayAlertMessage("Phone number is required.");

        }else if(imageName.equals("")){

            displayAlertMessage("Choose a new profile picture.");

        }else if (!confirm_password_text.equals(new_password_text)){

            //Check to see if current password was provided in case a user decides to create new password

            if(current_password_text.isEmpty()){

                displayAlertMessage("Current password field cannot be empty.");

            }else{

                displayAlertMessage("New password and Confirmed password do not match.");

            }

        }else if(new_password_text.isEmpty()){

            updateAccountWithOutPassword(first_name_text, last_name_text, email_text, phone_number_text, imageName, encodedImageString);

        }else if (new_password.length() < 6){

            displayAlertMessage("New password must be more than six characters");

        }else{

            //Calls the method that updates the account information
            updateAccountWithPassword(first_name_text, last_name_text, email_text, phone_number_text, current_password_text, new_password_text, imageName, encodedImageString);
        }
    }

    private void updateAccountWithOutPassword(String first_name, String last_name, String email, String phone_number, String imageName, String encodedImageString) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Account...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest stringRequestOne = new StringRequest(Request.Method.POST, References.update_account_without_password, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if(success.equals("1")){

                    //Update the values of the current shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("first_name", first_name);
                    editor.putString("last_name", last_name);
                    editor.putString("email", email);
                    editor.putString("phone_number", phone_number);
                    editor.putString("profile_picture_address", References.address +"profile_pictures/"+ imageName);
                    editor.apply();

                    progressDialog.dismiss();

                    displayAlertMessage(message + "\n\nSome changes will take effect after you re -log in.");

                }else if(success.equals("0")){

                    progressDialog.dismiss();
                    displayAlertMessage(message);
                }

            }catch (JSONException e){

                progressDialog.dismiss();
                displayAlertMessage("Internal system error.");
                Log.e("TAG", response);
            }
        }, error -> {

            progressDialog.dismiss();
            displayAlertMessage("System error.");
            Log.e("TAG", error.getMessage());
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map <String, String> params = new HashMap<>();
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("phone_number", phone_number);
                params.put("image_name", imageName);
                params.put("encoded_image", encodedImageString);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequestOne);
    }

    private void updateAccountWithPassword(String first_name, String last_name, String email, String phone_number, String current_password, String new_password, String imageName, String encodedImageString) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Account.");
        progressDialog.show();
        progressDialog.setCancelable(false);

    StringRequest stringRequest = new StringRequest(Request.Method.POST, References.update_account_with_password, response -> {

        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            String message = jsonObject.getString("message");

            if(success.equals("1")){

                //Update the values of the current shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("first_name", first_name);
                editor.putString("last_name", last_name);
                editor.putString("email", email);
                editor.putString("phone_number", phone_number);
                editor.putString("profile_picture_address", References.address +"profile_pictures/"+ imageName);
                editor.apply();

                progressDialog.dismiss();
                displayAlertMessage(message + "\n\nSome changes will take effect after restarting app.");

            }else if(success.equals("0")){

                progressDialog.dismiss();
                displayAlertMessage(message);
            }

        }catch (JSONException e){

            progressDialog.dismiss();
            displayAlertMessage("Internal system error.");
            Log.e("TAG", response);

        }
    }, error -> {

        progressDialog.dismiss();
        displayAlertMessage("System error.");
        Log.e("TAG", error.getMessage());
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map <String, String> params = new HashMap<>();
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("email", email);
            params.put("phone_number", phone_number);
            params.put("new_password", new_password);
            params.put("current_password", current_password);
            params.put("image_name", imageName);
            params.put("encoded_image", encodedImageString);
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

        if(v.getId() == R.id.update_account_update_button)
            updateButtonsAction();

        if(v.getId() == R.id.my_account_change_photo_button)
            uploadNewPhoto();
    }
}


