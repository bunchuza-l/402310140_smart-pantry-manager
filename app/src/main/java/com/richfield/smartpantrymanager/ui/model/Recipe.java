package com.richfield.smartpantrymanager.ui.model;


import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private List<String> ingredients; // List of ingredient names for simplicity
    private String instructions;

    // Empty constructor
    public Recipe() {}

    // Full constructor
    public Recipe(int id, String name, List<String> ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}