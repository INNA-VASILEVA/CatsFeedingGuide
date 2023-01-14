package com.example.catsfeedingguide.services.impl;

import com.example.catsfeedingguide.model.Ingredient;
import com.example.catsfeedingguide.services.IngredientsService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    private static int counter = 0;

    private final Map<Integer, Ingredient> ingredients = new LinkedHashMap<>();

    @Override
    public int addIngredient(Ingredient ingredient) {
        ingredients.put(++counter, ingredient);
        return counter;
    }

    @Override
    public Ingredient getIngredient(int number) {
        return ingredients.get(number);
    }
}
