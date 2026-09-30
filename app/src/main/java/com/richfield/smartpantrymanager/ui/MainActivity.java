/*
 * Smart Pantry Manager
 * Course: 402310140 Mobile_APP_Dev
 */
package com.richfield.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.richfield.smartpantrymanager.R;

/**
 * Main Dashboard screen providing quick access to Pantry, Recipes, and Settings.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCardView cardPantry = findViewById(R.id.cardPantry);
        MaterialCardView cardSuggestedRecipes = findViewById(R.id.cardSuggestedRecipes);
        MaterialCardView cardSettings = findViewById(R.id.cardSettings);

        cardPantry.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PantryListActivity.class);
            startActivity(intent);
        });

        cardSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
            startActivity(intent);
        });

        cardSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
