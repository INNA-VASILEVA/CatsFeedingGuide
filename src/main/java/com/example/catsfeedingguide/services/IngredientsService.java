package com.example.catsfeedingguide.services;

import com.example.catsfeedingguide.model.Ingredient;

public interface IngredientsService {
    int addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int number);
}