package com.example.catsfeedingguide.services;

import com.example.catsfeedingguide.model.Recipe;

import java.util.List;

public interface RecipesService {
    int addRecipe(Recipe recipe);

    Recipe getRecipe(int number);
    List<Recipe> getAllRecipes(long page, long numberOfRecipesOnPage);
    List<Recipe> searchByIngredientIds(List<Integer> ingredientIds);
    Recipe updateRecipe(int number, Recipe recipe);
    Recipe deleteRecipe(int number);
}