package com.richfield.smartpantrymanager.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.richfield.smartpantrymanager.ui.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite Database Helper for managing local pantry ingredient persistence.
 */
public class PantryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_INGREDIENT = "ingredient";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_EXPIRY = "expiryDate";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_INGREDIENT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_QUANTITY + " INTEGER, " +
                    COLUMN_UNIT + " TEXT, " +
                    COLUMN_EXPIRY + " TEXT);";

    public PantryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        onCreate(db);
    }

    // ------------------ CRUD OPERATIONS ------------------

    /**
     * Inserts a new ingredient record into SQLite.
     *
     * @param name       Ingredient name
     * @param quantity   Quantity amount
     * @param unit       Unit description
     * @param expiryDate Expiry date string (YYYY-MM-DD)
     * @return Row ID of the created ingredient, or -1 on error.
     */
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

    /**
     * Retrieves all ingredient records sorted alphabetically by name.
     *
     * @return List of Ingredient objects.
     */
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

    /**
     * Updates an existing ingredient record.
     *
     * @param ingredient Ingredient model containing updated fields.
     * @return Number of rows affected.
     */
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

    /**
     * Deletes an ingredient record by ID.
     *
     * @param id Database ID of the ingredient to remove.
     */
    public void deleteIngredient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENT, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Fetches a single ingredient record by ID.
     *
     * @param id Database ID.
     * @return Ingredient object if found, null otherwise.
     */
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
