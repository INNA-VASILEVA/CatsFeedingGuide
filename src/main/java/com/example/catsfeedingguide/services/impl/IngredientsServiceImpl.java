package com.example.catsfeedingguide.services.impl;

import com.example.catsfeedingguide.model.Ingredient;
import com.example.catsfeedingguide.services.FilesService;
import com.example.catsfeedingguide.services.IngredientsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    private static Integer counter = 0;

    public static Map<Integer, Ingredient> ingredients = new LinkedHashMap<>();

    @Value("${name.of.ingredients.data.file}")
    private String ingredientsDataFileName;

    private final FilesService filesService;

    public IngredientsServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
        if (!ingredients.isEmpty()) {
            counter = Collections.max(ingredients.keySet());
        }
    }

    @Override
    public int addIngredient(Ingredient ingredient) {
        for (Ingredient ingr : ingredients.values()) {
            if (ingr.equals(ingredient)) {
                return ingr.getId();
            }
        }
        ingredient.setId(++counter);
        ingredients.put(ingredient.getId(), ingredient);
        saveToFile();
        return ingredient.getId();
    }

    @Override
    public Ingredient getIngredient(int number) {
        return ingredients.get(number);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredients.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
    }

    @Override
    public Ingredient updateIngredient(int number, Ingredient ingredient) {
        if (!ingredients.containsKey(number)) {
            return null;
        }
        ingredient.setId(number);
        Ingredient result = ingredients.put(number, ingredient);
        saveToFile();
        return result;

    }

    @Override
    public Ingredient deleteIngredient(int number) {
        Ingredient result = ingredients.remove(number);
        if (result != null) {
            saveToFile();
        }

        return result;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveToJsonFile(json, ingredientsDataFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromJsonFile(ingredientsDataFileName);
            if (json != null) {
                ingredients = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Integer, Ingredient>>() {
                });
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
