package com.richfield.smartpantrymanager.ui.model;

import java.util.List;

/**
 * Model class representing a recipe with ingredient requirements and instructions.
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
}
