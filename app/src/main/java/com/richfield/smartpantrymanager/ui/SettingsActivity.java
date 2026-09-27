package com.richfield.smartpantrymanager.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.richfield.smartpantrymanager.R;

/**
 * Settings & About Activity displaying application details and developer information.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_settings);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
