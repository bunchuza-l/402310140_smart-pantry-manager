package com.richfield.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.richfield.smartpantrymanager.R;

/**
 * Activity for rendering detailed recipe ingredients and cooking instructions.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_recipe_detail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewIngredients = findViewById(R.id.textViewIngredients);
        TextView textViewInstructions = findViewById(R.id.textViewInstructions);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("recipe_name");
            String ingredients = intent.getStringExtra("recipe_ingredients");
            String instructions = intent.getStringExtra("recipe_instructions");

            if (name != null) {
                textViewTitle.setText(name);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(name);
                }
            }
            if (ingredients != null) textViewIngredients.setText(ingredients);
            if (instructions != null) textViewInstructions.setText(instructions);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
