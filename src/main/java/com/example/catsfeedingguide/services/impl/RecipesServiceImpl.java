package com.example.catsfeedingguide.services.impl;

import com.example.catsfeedingguide.model.Recipe;
import com.example.catsfeedingguide.services.RecipesService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RecipesServiceImpl implements RecipesService {

    private static int counter = 0;

    private final Map<Integer, Recipe> recipes = new LinkedHashMap<>();

    @Override
    public int addRecipe(Recipe recipe) {
        recipes.put(++counter, recipe);
        return counter;
    }

    @Override
    public Recipe getRecipe(int number) {
        return recipes.get(number);
    }
}
