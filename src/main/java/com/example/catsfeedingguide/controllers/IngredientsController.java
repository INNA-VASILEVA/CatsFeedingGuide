package com.example.catsfeedingguide.controllers;

import com.example.catsfeedingguide.model.Ingredient;
import com.example.catsfeedingguide.services.IngredientsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredients")

public class IngredientsController {
    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addIngredient(@RequestBody Ingredient ingredient) {
        return "Object number: " + ingredientsService.addIngredient(ingredient);
    }

    @GetMapping("/get")
    public Ingredient getIngredient(@RequestParam int number) {
        return ingredientsService.getIngredient(number);
    }
}
