package com.example.catsfeedingguide.services.impl;

import com.example.catsfeedingguide.model.Ingredient;
import com.example.catsfeedingguide.model.Recipe;
import com.example.catsfeedingguide.services.FilesService;
import com.example.catsfeedingguide.services.RecipesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.catsfeedingguide.CheckData.addNewIngredients;

@Service
public class RecipesServiceImpl implements RecipesService {
    private static int counter = 0;

    private static Map<Integer, Recipe> recipes = new LinkedHashMap<>();

    @Value("${name.of.recipes.data.file}")
    private String recipesDataFileName;

    @Value("${name.of.ingredients.data.file}")
    private String ingredientsDataFileName;

    private final FilesService filesService;

    public RecipesServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
        if (!recipes.isEmpty()) {
            counter = Collections.max(recipes.keySet());
        }
    }

    @Override
    public int addRecipe(Recipe recipe) {
        for (Recipe rcp : recipes.values()) {
            if (rcp.equals(recipe)) {
                return rcp.getId();
            }
        }
        recipe.setId(++counter);
        addNewIngredients(recipe, counter);
        recipes.put(recipe.getId(), recipe);
        saveToFile();
        recipes.put(recipe.getId(), recipe);
        return recipe.getId();
    }

    @Override
    public Recipe getRecipe(int number) {
        return recipes.get(number);
    }

    @Override
    public List<Recipe> getAllRecipes(long page, long numberOfRecipesOnPage) {
        return recipes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .skip((page - 1) * numberOfRecipesOnPage)
                .limit(numberOfRecipesOnPage)
                .map(Map.Entry::getValue)
                .toList();
    }

    @Override
    public List<Recipe> searchByIngredientIds(List<Integer> ingredientIds) {
        return recipes.values().stream()
                .filter(recipe -> new HashSet<>(recipe.getIngredients().stream()
                        .map(Ingredient::getId)
                        .toList())
                        .containsAll(ingredientIds))
                .sorted(Comparator.comparing(Recipe::getId))
                .toList();
    }

    @Override
    public Recipe updateRecipe(int number, Recipe recipe) {
        if (!recipes.containsKey(number)) return null;
        addNewIngredients(recipe, counter);
        recipe.setId(number);
        Recipe result = recipes.put(number, recipe);
        saveToFile();

        return result;
    }

    @Override
    public Recipe deleteRecipe(int number) {
        Recipe result = recipes.remove(number);
        if (result != null) {
            saveToFile();
        }

        return result;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveToJsonFile(json, recipesDataFileName);
            json = new ObjectMapper().writeValueAsString(IngredientsServiceImpl.ingredients);
            filesService.saveToJsonFile(json, ingredientsDataFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromJsonFile(recipesDataFileName);
            if (json != null) {
                recipes = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Integer, Recipe>>() {
                });
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
