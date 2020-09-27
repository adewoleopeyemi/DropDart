package com.example.dropdart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Profile");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}