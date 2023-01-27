package com.example.catsfeedingguide.controllers;

import com.example.catsfeedingguide.model.Ingredient;
import com.example.catsfeedingguide.services.IngredientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "CRUD-операции для работы с ингредиентами")
public class IngredientsController {
    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @Operation(summary = "Добавление ингредиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно добавлен"
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok("Ingredient ID: " + ingredientsService.addIngredient(ingredient));
    }

    @Operation(summary = "Получение ингредиента по ID")
    @Parameters(value = {
            @Parameter(
                    name = "id",
                    example = "1"
            )}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент с таким ID найден и возвращен в теле ответа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент с таким ID не найден"
            )
    })

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getIngredient(@PathVariable int id) {
        Ingredient result = ingredientsService.getIngredient(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Получение всех добавленных ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список ингредиентов не пуст и возвращен в теле ответа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Ingredient.class
                                            )
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Список ингредиентов пуст"
            )
    })

    @GetMapping
    public ResponseEntity<?> getAllIngredients() {
        List<Ingredient> ingredients = ingredientsService.getAllIngredients();
        if (ingredients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ingredients);
    }
    @Operation(summary = "Обновление ингредиента по ID")
    @Parameters(value =
    @Parameter(
            name = "id",
            example = "1"
    )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент с таким ID найден и обновлен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент с таким ID не найден"
            )
    })

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Ingredient oldIngredient = ingredientsService.updateIngredient(id, ingredient);
        if (oldIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ингредиент #" + id + " обновлен");
    }

    @Operation(summary = "Удаление ингредиента по ID")
    @Parameters(value =
    @Parameter(
            name = "id",
            example = "1"
    )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент с таким ID найден и удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент с таким ID не найден"
            )
    })

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable int id) {
        Ingredient deletedIngredient = ingredientsService.deleteIngredient(id);
        if (deletedIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ингредиент #" + id + " удален");
    }
}
