/*
 * Smart Pantry Manager
 * Course: 402310140 Mobile_APP_Dev
 */
package com.richfield.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.richfield.smartpantrymanager.R;
import com.richfield.smartpantrymanager.ui.adapter.IngredientAdapter;
import com.richfield.smartpantrymanager.ui.database.PantryDatabaseHelper;
import com.richfield.smartpantrymanager.ui.model.Ingredient;

import java.util.List;

/**
 * Activity for displaying the list of pantry ingredients sorted by urgency:
 * Expired -> Expires Soon -> Fresh (ascending by expiry date).
 */
public class PantryListActivity extends AppCompatActivity {

    private ListView listViewIngredients;
    private TextView textViewEmpty;
    private PantryDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_pantry);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new PantryDatabaseHelper(this);
        listViewIngredients = findViewById(R.id.listViewIngredients);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        listViewIngredients.setEmptyView(textViewEmpty);

        FloatingActionButton fab = findViewById(R.id.fabAddIngredient);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(PantryListActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
    }

    private void loadIngredients() {
        List<Ingredient> ingredientList = dbHelper.getAllIngredients();

        // Sort: Expired (Rank 1) -> Expires Soon (Rank 2) -> Fresh (Rank 3), then by Expiry Date ascending
        ingredientList.sort((i1, i2) -> {
            int rank1 = getPriorityRank(i1);
            int rank2 = getPriorityRank(i2);
            if (rank1 != rank2) {
                return Integer.compare(rank1, rank2);
            }
            String exp1 = i1.getExpiryDate() != null ? i1.getExpiryDate() : "";
            String exp2 = i2.getExpiryDate() != null ? i2.getExpiryDate() : "";
            int dateComp = exp1.compareTo(exp2);
            if (dateComp != 0) {
                return dateComp;
            }
            String name1 = i1.getName() != null ? i1.getName() : "";
            String name2 = i2.getName() != null ? i2.getName() : "";
            return name1.compareToIgnoreCase(name2);
        });

        IngredientAdapter adapter = new IngredientAdapter(this, ingredientList, new IngredientAdapter.OnIngredientActionListener() {
            @Override
            public void onEdit(Ingredient ingredient) {
                Intent intent = new Intent(PantryListActivity.this, AddEditIngredientActivity.class);
                intent.putExtra("ingredient_id", ingredient.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Ingredient ingredient) {
                String confirmMsg = getString(R.string.dialog_delete_confirm, ingredient.getName());
                new AlertDialog.Builder(PantryListActivity.this)
                        .setTitle(R.string.dialog_delete_title)
                        .setMessage(confirmMsg)
                        .setPositiveButton(R.string.btn_delete, (dialog, which) -> {
                            dbHelper.deleteIngredient(ingredient.getId());
                            Toast.makeText(PantryListActivity.this, "Deleted " + ingredient.getName(), Toast.LENGTH_SHORT).show();
                            loadIngredients();
                        })
                        .setNegativeButton(R.string.btn_cancel, null)
                        .show();
            }
        });
        listViewIngredients.setAdapter(adapter);
    }

    private int getPriorityRank(Ingredient ingredient) {
        if (ingredient.isExpired()) {
            return 1; // Expired first
        } else if (ingredient.isNearExpiry()) {
            return 2; // Expires Soon second
        } else {
            return 3; // Fresh third
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
