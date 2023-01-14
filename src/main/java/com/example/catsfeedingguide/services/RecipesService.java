package com.example.catsfeedingguide.services;

import com.example.catsfeedingguide.model.Recipe;

public interface RecipesService {
    int addRecipe(Recipe recipe);

    Recipe getRecipe(int number);
}