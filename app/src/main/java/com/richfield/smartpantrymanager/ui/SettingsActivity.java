/*
 * Smart Pantry Manager
 * Course: 402310140 Mobile_APP_Dev
 */
package com.richfield.smartpantrymanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.richfield.smartpantrymanager.R;

/**
 * Settings Screen for user preference toggles and assignment metadata.
 */
public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SmartPantryPrefs";
    private static final String KEY_ALERT_ENABLED = "expiry_alerts_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_settings);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SwitchMaterial switchAlerts = findViewById(R.id.switchExpiryAlerts);

        boolean isAlertsEnabled = prefs.getBoolean(KEY_ALERT_ENABLED, true);
        switchAlerts.setChecked(isAlertsEnabled);

        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_ALERT_ENABLED, isChecked).apply();
            String msg = isChecked ? "Expiring-soon alerts enabled" : "Expiring-soon alerts disabled";
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
