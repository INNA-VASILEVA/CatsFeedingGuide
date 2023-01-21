package com.example.catsfeedingguide.controllers;

import com.example.catsfeedingguide.model.Ingredient;
import com.example.catsfeedingguide.services.IngredientsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")

public class IngredientsController {
    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok("Ingredient ID: " + ingredientsService.addIngredient(ingredient));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) {
        Ingredient result = ingredientsService.getIngredient(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientsService.getAllIngredients();
        if (ingredients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ingredients);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Ingredient oldIngredient = ingredientsService.updateIngredient(id, ingredient);
        if (oldIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ингредиент #" + id + " удален");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable int id) {
        Ingredient deletedIngredient = ingredientsService.deleteIngredient(id);
        if (deletedIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ингредиент #" + id + " удален");
    }
}
