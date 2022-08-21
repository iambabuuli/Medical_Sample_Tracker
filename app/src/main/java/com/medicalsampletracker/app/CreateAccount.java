package com.medicalsampletracker.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ganfra.materialspinner.MaterialSpinner;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {

    EditText first_name_register, last_name_register, email_register, phone_number_register, password_register, confirm_password_register;
    Button register_button, choose_profile_picture;
    MaterialSpinner account_type;
    CircleImageView profile_picture;
    Toolbar toolbar;
    public static Bitmap bitmap;

    String encodedImageString, imageName;

    private static final int CHOOSE_IMAGE_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

       initialize();

       choose_profile_picture.setOnClickListener(this);
       register_button.setOnClickListener(this);

       toolbar.setNavigationOnClickListener(v -> finish());

    }

    private void chooseProfilePicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null){

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                profile_picture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

             imageName = "IMG_" + System.currentTimeMillis();
             encodedImageString = getEncodeImageString(bitmap);

        }
    }


    private String getEncodeImageString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public void initialize(){

        imageName = "";

        first_name_register = findViewById(R.id.first_name_register);
        last_name_register = findViewById(R.id.last_name_register);
        email_register = findViewById(R.id.email_register);
        phone_number_register = findViewById(R.id.phone_number_register);
        password_register = findViewById(R.id.password_register);
        confirm_password_register = findViewById(R.id.confirm_password_register);
        register_button = findViewById(R.id.register_button);
        account_type = findViewById(R.id.account_type_options);
        profile_picture = findViewById(R.id.create_account_profile_picture_circleImageView);
        choose_profile_picture = findViewById(R.id.create_account_choose_photo);

        toolbar = findViewById(R.id.create_account_toolbar);
        setSupportActionBar(toolbar);
    }

    public void validateAndCreate(){

        //Check to make sure that no fields are empty

        if(first_name_register.getText().toString().isEmpty()){

            displayAlertMessage("First name field cannot be empty.");

        }else if(last_name_register.getText().toString().isEmpty()){

            displayAlertMessage("Last name field cannot be empty.");

        }else if(email_register.getText().toString().isEmpty()){

            displayAlertMessage("E-mail field cannot be empty.");

        }else if(phone_number_register.getText().toString().isEmpty()){

            displayAlertMessage("Phone number field cannot be empty.");

        }else if(password_register.getText().toString().isEmpty()){

            displayAlertMessage("Provide a new password.");

        }else if(password_register.getText().length() < 6 ){

            displayAlertMessage("Password cannot be less than six characters.");

        }else if(confirm_password_register.getText().toString().isEmpty()){

            displayAlertMessage("Type your password again to confirm it.");

        } else if (!password_register.getText().toString().equals(confirm_password_register.getText().toString())){

            displayAlertMessage("Confirmed password does not match with the new password.");

        } else if (imageName.equals("")){

            displayAlertMessage("Choose a profile picture.");

        }else {

            try{

                account_type.getSelectedItem().toString();
                AddRegister();

            }catch (Exception e){

                Toast.makeText(CreateAccount.this, "Choose account type.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void displayAlertMessage(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", (dialog, which) -> builder.setCancelable(true));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void AddRegister() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, References.register_url,
                response -> {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");

                        if(success.equals("1")){

                            progressDialog.dismiss();
                            displayAlertMessage(message);

                        }else if (success.equals("0")){

                            progressDialog.dismiss();
                            displayAlertMessage(message);
                        }

                    } catch (JSONException e) {

                        progressDialog.dismiss();
                        displayAlertMessage("Registration failed due to a server error.");
                        Log.e("TAG", response);
                    }

                }, error -> {

                    progressDialog.dismiss();
                    displayAlertMessage(error.getMessage());
                    Log.e("TAG", error.getMessage());

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

               Map<String, String > parameter = new HashMap<>();
               parameter.put("first_name", first_name_register.getText().toString());
               parameter.put("last_name", last_name_register.getText().toString());
               parameter.put("email", email_register.getText().toString());
               parameter.put("phone_number", phone_number_register.getText().toString());
               parameter.put("password", password_register.getText().toString());
               parameter.put("encoded_photo", encodedImageString);
               parameter.put("image_name", imageName);
               parameter.put("account_type", account_type.getSelectedItem().toString());

                return parameter;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.register_button)
            validateAndCreate();

        if(v.getId() == R.id.create_account_choose_photo)
            chooseProfilePicture();
    }
}
