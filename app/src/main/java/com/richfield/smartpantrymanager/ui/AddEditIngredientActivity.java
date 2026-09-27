package com.richfield.smartpantrymanager.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.richfield.smartpantrymanager.R;
import com.richfield.smartpantrymanager.ui.database.PantryDatabaseHelper;
import com.richfield.smartpantrymanager.ui.model.Ingredient;

/**
 * Activity for adding a new ingredient or editing an existing one in the pantry database.
 */
public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText editTextName, editTextQuantity, editTextUnit, editTextExpiry;
    private Button buttonSave;
    private TextView textViewFormTitle;
    private PantryDatabaseHelper dbHelper;

    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextUnit = findViewById(R.id.editTextUnit);
        editTextExpiry = findViewById(R.id.editTextExpiry);
        buttonSave = findViewById(R.id.buttonSave);
        textViewFormTitle = findViewById(R.id.textViewFormTitle);

        dbHelper = new PantryDatabaseHelper(this);

        if (getIntent() != null && getIntent().hasExtra("ingredient_id")) {
            ingredientId = getIntent().getIntExtra("ingredient_id", -1);
            if (ingredientId != -1) {
                Ingredient ingredient = dbHelper.getIngredientById(ingredientId);
                if (ingredient != null) {
                    editTextName.setText(ingredient.getName());
                    editTextQuantity.setText(String.valueOf(ingredient.getQuantity()));
                    editTextUnit.setText(ingredient.getUnit());
                    editTextExpiry.setText(ingredient.getExpiryDate());
                    buttonSave.setText(R.string.btn_update_ingredient);
                    if (textViewFormTitle != null) {
                        textViewFormTitle.setText(R.string.title_edit_ingredient);
                    }
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(R.string.title_edit_ingredient);
                    }
                }
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.title_add_ingredient);
            }
        }

        buttonSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = editTextName.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();
        String unit = editTextUnit.getText().toString().trim();
        String expiry = editTextExpiry.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(quantityStr) || TextUtils.isEmpty(unit) || TextUtils.isEmpty(expiry)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantity must be a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ingredientId != -1) {
            Ingredient ingredient = new Ingredient(ingredientId, name, quantity, unit, expiry);
            int rows = dbHelper.updateIngredient(ingredient);
            if (rows > 0) {
                Toast.makeText(this, "Ingredient updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update ingredient", Toast.LENGTH_SHORT).show();
            }
        } else {
            long id = dbHelper.addIngredient(name, quantity, unit, expiry);
            if (id > 0) {
                Toast.makeText(this, "Ingredient added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add ingredient", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
