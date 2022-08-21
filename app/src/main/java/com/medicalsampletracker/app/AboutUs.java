package com.medicalsampletracker.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutUs extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initialize();

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initialize() {

        toolbar = findViewById(R.id.about_us_toolbar);

        setSupportActionBar(toolbar);
    }


}
