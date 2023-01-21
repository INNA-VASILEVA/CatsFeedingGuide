package com.example.catsfeedingguide.controllers;

import com.example.catsfeedingguide.model.Recipe;
import com.example.catsfeedingguide.services.RecipesService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipesController {
    private static final long NUMBER_OF_RECIPES_ON_PAGE = 10;
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRecipe(@RequestBody Recipe recipe) {
        return ResponseEntity.ok("Recipe ID: " + recipesService.addRecipe(recipe));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe result = recipesService.getRecipe(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(defaultValue = "1") long page) {
        List<Recipe> recipes = recipesService.getAllRecipes(page, NUMBER_OF_RECIPES_ON_PAGE);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }
    @GetMapping(path = "/search")
    public ResponseEntity<List<Recipe>> searchByIngredientIds(@RequestParam(required = false) Integer ingredientId) {
        List<Recipe> recipes = recipesService.searchByIngredientIds(List.of(ingredientId));
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }
    @GetMapping(path = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recipe>> searchByIngredientIds(@RequestBody List<Integer> ingredientIds) {
        List<Recipe> recipes = recipesService.searchByIngredientIds(ingredientIds);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe oldRecipe = recipesService.updateRecipe(id, recipe);
        if (oldRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Рецепт #" + id + " удален");
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int id) {
        Recipe deletedRecipe = recipesService.deleteRecipe(id);
        if (deletedRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Рецепт #" + id + " удален");
    }
}