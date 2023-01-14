package com.example.catsfeedingguide.controllers;

import com.example.catsfeedingguide.model.Recipe;
import com.example.catsfeedingguide.services.RecipesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addRecipe(@RequestBody Recipe recipe) {
        return "Object number: " + recipesService.addRecipe(recipe);
    }

    @GetMapping("/get")
    public Recipe getRecipe(@RequestParam int number) {
        return recipesService.getRecipe(number);
    }
}
