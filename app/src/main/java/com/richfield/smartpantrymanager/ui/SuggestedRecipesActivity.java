package com.richfield.smartpantrymanager.ui;

import android.content.Intent;
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

import com.richfield.smartpantrymanager.R;
import com.richfield.smartpantrymanager.ui.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Displays a list of suggested recipes based on pantry inventory.
 */
public class SuggestedRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_suggested_recipes);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ListView listViewRecipes = findViewById(R.id.listViewRecipes);
        List<Recipe> recipes = getSampleRecipes();

        ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this, 0, recipes) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_recipe, parent, false);
                }

                Recipe recipe = getItem(position);
                TextView textViewName = convertView.findViewById(R.id.textViewRecipeName);
                TextView textViewIngredients = convertView.findViewById(R.id.textViewRecipeIngredients);

                if (recipe != null) {
                    textViewName.setText(recipe.getName());
                    String ingList = "Ingredients: " + String.join(", ", recipe.getIngredients());
                    textViewIngredients.setText(ingList);
                }

                return convertView;
            }
        };

        listViewRecipes.setAdapter(adapter);

        listViewRecipes.setOnItemClickListener((parent, view, position, id) -> {
            Recipe selectedRecipe = recipes.get(position);
            Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
            intent.putExtra("recipe_name", selectedRecipe.getName());
            intent.putExtra("recipe_ingredients", String.join(", ", selectedRecipe.getIngredients()));
            intent.putExtra("recipe_instructions", selectedRecipe.getInstructions());
            startActivity(intent);
        });
    }

    private List<Recipe> getSampleRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe(1, "Scrambled Eggs",
                Arrays.asList("Eggs", "Milk", "Butter", "Salt"),
                "1. Whisk eggs, milk, and salt in a bowl.\n2. Melt butter in a skillet over medium heat.\n3. Pour in egg mixture and scramble until set.\n4. Serve warm."));

        recipes.add(new Recipe(2, "Tomato Pasta",
                Arrays.asList("Pasta", "Tomatoes", "Garlic", "Olive Oil"),
                "1. Boil pasta in salted water until al dente.\n2. Heat olive oil and saute minced garlic.\n3. Add diced tomatoes and simmer to form a sauce.\n4. Toss pasta in tomato sauce and serve."));

        recipes.add(new Recipe(3, "Fruit Salad",
                Arrays.asList("Apples", "Bananas", "Strawberries", "Honey"),
                "1. Chop apples, bananas, and strawberries into bite-sized pieces.\n2. Mix all fruit together in a bowl.\n3. Drizzle with honey and toss gently before serving."));

        recipes.add(new Recipe(4, "Grilled Cheese Sandwich",
                Arrays.asList("Bread", "Cheese", "Butter"),
                "1. Butter one side of two bread slices.\n2. Place cheese between non-buttered sides.\n3. Grill on skillet until golden brown on both sides."));

        return recipes;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
