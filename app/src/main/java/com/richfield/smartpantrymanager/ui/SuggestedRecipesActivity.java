/*
 * Smart Pantry Manager
 * Course: 402310140 Mobile_APP_Dev
 */
package com.richfield.smartpantrymanager.ui;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.richfield.smartpantrymanager.R;
import com.richfield.smartpantrymanager.ui.database.PantryDatabaseHelper;
import com.richfield.smartpantrymanager.ui.model.Ingredient;
import com.richfield.smartpantrymanager.ui.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enforces strict ingredient matching against user's SQLite pantry items.
 */
public class SuggestedRecipesActivity extends AppCompatActivity {

    private PantryDatabaseHelper dbHelper;
    private TextView textViewPantrySummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_suggested_recipes);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new PantryDatabaseHelper(this);
        textViewPantrySummary = findViewById(R.id.textViewPantrySummary);
        ListView listViewRecipes = findViewById(R.id.listViewRecipes);

        loadMatchingRecipes(listViewRecipes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listViewRecipes = findViewById(R.id.listViewRecipes);
        loadMatchingRecipes(listViewRecipes);
    }

    private void loadMatchingRecipes(ListView listViewRecipes) {
        List<Ingredient> pantryIngredients = dbHelper.getAllIngredients();
        Set<String> pantryNamesLower = new HashSet<>();
        for (Ingredient ing : pantryIngredients) {
            if (ing.getName() != null) {
                pantryNamesLower.add(ing.getName().toLowerCase().trim());
            }
        }

        List<Recipe> dbRecipes = dbHelper.getAllRecipes();
        if (dbRecipes == null || dbRecipes.isEmpty()) {
            dbRecipes = getFallbackCatalog();
        }

        List<Recipe> strictMatches = new ArrayList<>();
        List<Recipe> almostThereMatches = new ArrayList<>();

        for (Recipe r : dbRecipes) {
            List<String> missing = r.getMissingIngredients(pantryNamesLower);
            if (missing.isEmpty() && !pantryIngredients.isEmpty()) {
                strictMatches.add(r);
            } else if (missing.size() == 1 && !pantryIngredients.isEmpty()) {
                almostThereMatches.add(r);
            }
        }

        // Build combined list with header indicators
        List<RecipeDisplayItem> displayList = new ArrayList<>();

        if (pantryIngredients.isEmpty()) {
            textViewPantrySummary.setText("💡 Your pantry is empty! Add ingredients to discover recipes you can cook right now.");
        } else if (strictMatches.isEmpty()) {
            textViewPantrySummary.setText("⚠️ No recipes match your pantry yet — add more ingredients to unlock recipes you can cook right now!");
        } else {
            textViewPantrySummary.setText("✅ Found " + strictMatches.size() + " recipe(s) you can cook right now with NO extra shopping required!");
        }

        // Add strict matches
        for (Recipe r : strictMatches) {
            displayList.add(new RecipeDisplayItem(r, false));
        }

        // Add "Almost There" stretch matches if available
        for (Recipe r : almostThereMatches) {
            displayList.add(new RecipeDisplayItem(r, true));
        }

        ArrayAdapter<RecipeDisplayItem> adapter = new ArrayAdapter<RecipeDisplayItem>(this, 0, displayList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_recipe, parent, false);
                }

                RecipeDisplayItem item = getItem(position);
                TextView textViewName = convertView.findViewById(R.id.textViewRecipeName);
                TextView textViewIngredients = convertView.findViewById(R.id.textViewRecipeIngredients);
                TextView textViewMatchBadge = convertView.findViewById(R.id.textViewMatchBadge);
                TextView textViewMissingIngredients = convertView.findViewById(R.id.textViewMissingIngredients);

                if (item != null) {
                    Recipe recipe = item.recipe;
                    textViewName.setText(recipe.getName());
                    String ingList = "Requires: " + String.join(", ", recipe.getIngredients());
                    textViewIngredients.setText(ingList);

                    List<String> missing = recipe.getMissingIngredients(pantryNamesLower);

                    GradientDrawable badgeBg = new GradientDrawable();
                    badgeBg.setCornerRadius(16f);

                    if (!item.isAlmostThere) {
                        textViewMatchBadge.setText("STRICT MATCH 🍳");
                        textViewMatchBadge.setTextColor(ContextCompat.getColor(getContext(), R.color.status_fresh_text));
                        badgeBg.setColor(ContextCompat.getColor(getContext(), R.color.status_fresh_bg));
                        textViewMissingIngredients.setVisibility(View.GONE);
                    } else {
                        textViewMatchBadge.setText("ALMOST THERE (MISSING 1)");
                        textViewMatchBadge.setTextColor(ContextCompat.getColor(getContext(), R.color.status_warning_text));
                        badgeBg.setColor(ContextCompat.getColor(getContext(), R.color.status_warning_bg));

                        textViewMissingIngredients.setVisibility(View.VISIBLE);
                        textViewMissingIngredients.setText("Missing 1 Item: " + String.join(", ", missing));
                    }

                    textViewMatchBadge.setBackground(badgeBg);
                }

                return convertView;
            }
        };

        listViewRecipes.setAdapter(adapter);

        listViewRecipes.setOnItemClickListener((parent, view, position, id) -> {
            RecipeDisplayItem selectedItem = displayList.get(position);
            Recipe selectedRecipe = selectedItem.recipe;
            Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
            intent.putExtra("recipe_name", selectedRecipe.getName());
            intent.putExtra("recipe_ingredients", String.join(", ", selectedRecipe.getIngredients()));
            intent.putExtra("recipe_instructions", selectedRecipe.getInstructions());
            startActivity(intent);
        });
    }

    private static class RecipeDisplayItem {
        final Recipe recipe;
        final boolean isAlmostThere;

        RecipeDisplayItem(Recipe recipe, boolean isAlmostThere) {
            this.recipe = recipe;
            this.isAlmostThere = isAlmostThere;
        }
    }

    private List<Recipe> getFallbackCatalog() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1, "Scrambled Eggs", Arrays.asList("Eggs", "Milk", "Butter", "Salt"), "Whisk eggs and scramble in buttered pan."));
        recipes.add(new Recipe(2, "Cereal with Milk", Arrays.asList("Cereal", "Milk"), "Pour cereal into bowl and add cold milk."));
        return recipes;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
