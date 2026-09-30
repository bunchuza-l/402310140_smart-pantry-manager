/*
 * Smart Pantry Manager
 * Course: 402310140 Mobile_APP_Dev
 */
package com.richfield.smartpantrymanager.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Model class representing a recipe with ingredient requirements and robust pantry matching logic.
 */
public class Recipe {
    private int id;
    private String name;
    private List<String> ingredients;
    private String instructions;

    /**
     * Default constructor.
     */
    public Recipe() {}

    /**
     * Parameterized constructor for creating a Recipe instance.
     *
     * @param id           Unique recipe ID
     * @param name         Recipe title
     * @param ingredients  List of required ingredient names
     * @param instructions Step-by-step cooking directions
     */
    public Recipe(int id, String name, List<String> ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    /**
     * Calculates matching ingredients from user's pantry.
     *
     * @param pantryNamesLower Set of lower-case pantry ingredient names.
     * @return List of matched ingredient names.
     */
    public List<String> getMatchingIngredients(Set<String> pantryNamesLower) {
        List<String> matched = new ArrayList<>();
        if (ingredients == null) return matched;
        for (String item : ingredients) {
            if (isItemInPantry(item, pantryNamesLower)) {
                matched.add(item);
            }
        }
        return matched;
    }

    /**
     * Calculates missing ingredients not found in user's pantry.
     *
     * @param pantryNamesLower Set of lower-case pantry ingredient names.
     * @return List of missing ingredient names.
     */
    public List<String> getMissingIngredients(Set<String> pantryNamesLower) {
        List<String> missing = new ArrayList<>();
        if (ingredients == null) return missing;
        for (String item : ingredients) {
            if (!isItemInPantry(item, pantryNamesLower)) {
                missing.add(item);
            }
        }
        return missing;
    }

    private boolean isItemInPantry(String recipeItem, Set<String> pantryNamesLower) {
        String reqNorm = normalizeName(recipeItem);

        for (String pItem : pantryNamesLower) {
            String pNorm = normalizeName(pItem);
            if (reqNorm.equals(pNorm) || reqNorm.contains(pNorm) || pNorm.contains(reqNorm)) {
                return true;
            }
        }
        return false;
    }

    private String normalizeName(String raw) {
        if (raw == null) return "";
        String name = raw.toLowerCase().trim();
        if (name.endsWith("es")) {
            return name.substring(0, name.length() - 2);
        } else if (name.endsWith("s") && !name.endsWith("ss")) {
            return name.substring(0, name.length() - 1);
        }
        return name;
    }
}
