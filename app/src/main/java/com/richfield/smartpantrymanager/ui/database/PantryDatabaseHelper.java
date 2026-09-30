/*
 * Smart Pantry Manager
 * Course: 402310140 Mobile_APP_Dev
 */
package com.richfield.smartpantrymanager.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.richfield.smartpantrymanager.ui.model.Ingredient;
import com.richfield.smartpantrymanager.ui.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite Database Helper for managing local pantry ingredients and recipe seeding.
 */
public class PantryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

    // Ingredient Table
    public static final String TABLE_INGREDIENT = "ingredient";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_EXPIRY = "expiryDate";

    // Recipe Table
    public static final String TABLE_RECIPE = "recipe";
    public static final String COLUMN_RECIPE_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "name";
    public static final String COLUMN_RECIPE_INGREDIENTS = "ingredients";
    public static final String COLUMN_RECIPE_INSTRUCTIONS = "instructions";

    private static final String TABLE_CREATE_INGREDIENT =
            "CREATE TABLE " + TABLE_INGREDIENT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_QUANTITY + " INTEGER, " +
                    COLUMN_UNIT + " TEXT, " +
                    COLUMN_EXPIRY + " TEXT);";

    private static final String TABLE_CREATE_RECIPE =
            "CREATE TABLE " + TABLE_RECIPE + " (" +
                    COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RECIPE_NAME + " TEXT, " +
                    COLUMN_RECIPE_INGREDIENTS + " TEXT, " +
                    COLUMN_RECIPE_INSTRUCTIONS + " TEXT);";

    public PantryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_INGREDIENT);
        db.execSQL(TABLE_CREATE_RECIPE);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(db);
    }

    // ------------------ RECIPE SEEDING ------------------

    private void seedRecipes(SQLiteDatabase db) {
        insertRecipeSeed(db, "Scrambled Eggs", "Eggs, Milk, Butter, Salt",
                "1. Whisk eggs, milk, and salt in a bowl.\n2. Melt butter in a skillet over medium heat.\n3. Pour in egg mixture and scramble gently until set.\n4. Serve warm with toast.");

        insertRecipeSeed(db, "Cereal with Milk", "Cereal, Milk",
                "1. Pour crunchy breakfast cereal into a clean bowl.\n2. Pour cold fresh milk over the cereal.\n3. Serve immediately and enjoy!");

        insertRecipeSeed(db, "Tomato Pasta", "Pasta, Tomatoes, Garlic, Olive Oil, Salt",
                "1. Boil pasta in salted water until al dente.\n2. Heat olive oil and saute minced garlic.\n3. Add diced tomatoes and simmer to form a rich sauce.\n4. Toss pasta in tomato sauce and serve hot.");

        insertRecipeSeed(db, "Fresh Fruit Salad", "Apples, Bananas, Strawberries, Honey",
                "1. Chop apples, bananas, and strawberries into bite-sized pieces.\n2. Mix all fresh fruit together in a large bowl.\n3. Drizzle with natural honey and toss gently before serving.");

        insertRecipeSeed(db, "Classic Grilled Cheese", "Bread, Cheese, Butter",
                "1. Butter one side of two fresh bread slices.\n2. Place cheese between non-buttered sides.\n3. Grill on skillet over medium heat until golden brown on both sides.");

        insertRecipeSeed(db, "Golden French Toast", "Bread, Eggs, Milk, Butter, Honey",
                "1. Whisk eggs and milk in a shallow dish.\n2. Dip bread slices into mixture until coated.\n3. Fry in buttered pan until golden brown.\n4. Drizzle with honey and serve.");

        insertRecipeSeed(db, "Chicken & Veggie Stir-Fry", "Chicken, Vegetables, Soy Sauce, Rice",
                "1. Slice chicken breast and fresh vegetables.\n2. Heat oil in a wok or pan over high heat.\n3. Stir-fry chicken until cooked through, then toss in vegetables and soy sauce.\n4. Serve hot over steamed rice.");

        insertRecipeSeed(db, "Avocado Toast with Egg", "Bread, Avocado, Eggs, Salt, Pepper",
                "1. Toast slices of bread until golden and crisp.\n2. Mash ripe avocado with a pinch of salt and pepper.\n3. Fry or poach eggs to your preference.\n4. Spread mashed avocado onto toast and top with the egg.");

        insertRecipeSeed(db, "Fluffy Cheese Omelette", "Eggs, Cheese, Butter, Salt",
                "1. Whisk eggs with salt until smooth.\n2. Melt butter in skillet over medium heat and pour egg mixture.\n3. Sprinkle grated cheese when eggs begin to set.\n4. Fold in half and serve warm.");

        insertRecipeSeed(db, "Homestyle Pancakes", "Flour, Eggs, Milk, Sugar, Butter",
                "1. Whisk flour, eggs, milk, and sugar into a smooth batter.\n2. Melt butter on a skillet over medium-high heat.\n3. Pour batter circles and flip when bubbles appear.\n4. Serve warm.");

        insertRecipeSeed(db, "Crispy Garlic Bread", "Bread, Garlic, Butter, Cheese",
                "1. Mix softened butter with minced garlic.\n2. Spread garlic butter generously over bread slices.\n3. Top with grated cheese and bake at 200°C until golden and crispy.");

        insertRecipeSeed(db, "Caprese Salad", "Tomatoes, Cheese, Olive Oil, Salt",
                "1. Slice fresh tomatoes and mozzarella cheese.\n2. Arrange slices alternately on a serving plate.\n3. Drizzle generously with olive oil and sprinkle with sea salt.");

        insertRecipeSeed(db, "Creamy Banana Smoothie", "Bananas, Milk, Honey",
                "1. Peel ripe bananas and place in blender.\n2. Add cold milk and a tablespoon of honey.\n3. Blend until creamy and smooth. Serve chilled.");

        insertRecipeSeed(db, "Egg Fried Rice", "Rice, Eggs, Vegetables, Soy Sauce, Oil",
                "1. Heat oil in a large skillet over high heat.\n2. Scramble eggs and set aside.\n3. Stir-fry cooked rice and vegetables with soy sauce.\n4. Mix in scrambled eggs and serve hot.");

        insertRecipeSeed(db, "Creamy Mashed Potatoes", "Potatoes, Butter, Milk, Salt",
                "1. Peel and boil potatoes in salted water until fork-tender.\n2. Drain water and mash potatoes thoroughly.\n3. Add melted butter and warm milk, stirring until creamy.");

        insertRecipeSeed(db, "Tuna Salad Sandwich", "Bread, Tuna, Mayo, Pepper",
                "1. Drain canned tuna and mix with mayonnaise and black pepper in a bowl.\n2. Spread tuna mixture evenly between two slices of fresh bread.\n3. Slice and serve.");

        insertRecipeSeed(db, "Cheesy Garlic Pasta", "Pasta, Garlic, Butter, Cheese, Salt",
                "1. Boil pasta until al dente.\n2. Melt butter in pan and saute garlic until fragrant.\n3. Toss pasta in garlic butter and melted cheese. Serve warm.");

        insertRecipeSeed(db, "Steamed Veggies with Rice", "Rice, Vegetables, Butter, Salt",
                "1. Steam fresh mixed vegetables until tender.\n2. Cook white or brown rice.\n3. Toss veggies with butter and salt, then serve over warm rice.");
    }

    private void insertRecipeSeed(SQLiteDatabase db, String name, String ingredients, String instructions) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, name);
        values.put(COLUMN_RECIPE_INGREDIENTS, ingredients);
        values.put(COLUMN_RECIPE_INSTRUCTIONS, instructions);
        db.insert(TABLE_RECIPE, null, values);
    }

    /**
     * Reads all pre-seeded recipes from SQLite.
     */
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE, null, null, null, null, null, COLUMN_RECIPE_NAME + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME));
                String rawIng = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INGREDIENTS));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INSTRUCTIONS));

                List<String> ingredients = new ArrayList<>();
                if (rawIng != null) {
                    for (String item : rawIng.split(",")) {
                        ingredients.add(item.trim());
                    }
                }

                recipeList.add(new Recipe(id, name, ingredients, instructions));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return recipeList;
    }

    // ------------------ CRUD OPERATIONS (INGREDIENTS) ------------------

    public long addIngredient(String name, int quantity, String unit, String expiryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_EXPIRY, expiryDate);

        long id = db.insert(TABLE_INGREDIENT, null, values);
        db.close();
        return id;
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INGREDIENT, null, null, null, null, null, COLUMN_NAME + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                ingredient.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT)));
                ingredient.setExpiryDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY)));
                ingredientList.add(ingredient);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return ingredientList;
    }

    public int updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_QUANTITY, ingredient.getQuantity());
        values.put(COLUMN_UNIT, ingredient.getUnit());
        values.put(COLUMN_EXPIRY, ingredient.getExpiryDate());

        int rows = db.update(TABLE_INGREDIENT, values, COLUMN_ID + " = ?", new String[]{String.valueOf(ingredient.getId())});
        db.close();
        return rows;
    }

    public void deleteIngredient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENT, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Ingredient getIngredientById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INGREDIENT, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        Ingredient ingredient = null;
        if (cursor != null && cursor.moveToFirst()) {
            ingredient = new Ingredient();
            ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            ingredient.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
            ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT)));
            ingredient.setExpiryDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY)));
            cursor.close();
        }
        db.close();
        return ingredient;
    }
}
