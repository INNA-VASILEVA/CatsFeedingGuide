package com.example.catsfeedingguide.controllers;

import com.example.catsfeedingguide.model.Recipe;
import com.example.catsfeedingguide.services.RecipesService;
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

import java.util.List;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы с рецептами")
public class RecipesController {
    private static final long NUMBER_OF_RECIPES_ON_PAGE = 10;
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @Operation(
            summary = "Добавление рецепта",
            description = "Новые ингредиенты автоматически добавляются в список ингредиентов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно добавлен"
            )
    })

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRecipe(@RequestBody Recipe recipe) {
        return ResponseEntity.ok("Recipe ID: " + recipesService.addRecipe(recipe));
    }

    @Operation(summary = "Получение рецепта по ID")
    @Parameters(value = {
            @Parameter(
                    name = "id",
                    example = "1"
            )}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт с таким ID найден и возвращен в теле ответа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт с таким ID не найден"
            )
    })

    @GetMapping(path = "/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe result = recipesService.getRecipe(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Получение всех добавленных рецептов",
            description = "Выводится по 10 на страницу"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список рецептов не пуст и возвращен в теле ответа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Recipe.class
                                            )
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Список рецептов пуст"
            )
    })
    @Parameters(value = {
            @Parameter(
                    name = "page",
                    description = "Номер страницы",
                    example = "1"
            )
    })
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(defaultValue = "1") long page) {
        List<Recipe> recipes = recipesService.getAllRecipes(page, NUMBER_OF_RECIPES_ON_PAGE);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }
    @Operation(summary = "Поиск рецептов, содержащих ингредиент с указанным ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты с указанным ингредиентом найдены и возвращены в теле ответа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Recipe.class
                                            )
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Список рецептов с указанным ингредиентом пуст"
            )
    })
    @Parameters(value = {
            @Parameter(
                    name = "ingredientId",
                    description = "ID ингредиента для поиска",
                    example = "1"
            )
    })
    @GetMapping(path = "/search")
    public ResponseEntity<?> searchByIngredientIds(@RequestParam(required = false) Integer ingredientId) {
        List<Recipe> recipes = recipesService.searchByIngredientIds(List.of(ingredientId));
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "Поиск рецептов, содержащих все ингредиенты с указанными ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты с указанными ингредиентами найдены и возвращены в теле ответа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Recipe.class
                                            )
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Список рецептов с указанными ингредиентами пуст"
            )
    })
    @Parameters(value = {
            @Parameter(
                    name = "ingredientIds",
                    description = "Список ID ингредиентов для поиска"
            )
    })

    @GetMapping(path = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recipe>> searchByIngredientIds(@RequestBody List<Integer> ingredientIds) {
        List<Recipe> recipes = recipesService.searchByIngredientIds(ingredientIds);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }
    @Operation(summary = "Обновление рецепта по ID")
    @Parameters(value =
    @Parameter(
            name = "id",
            example = "1"
    )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт с таким ID найден и обновлен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт с таким ID не найден"
            )
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe oldRecipe = recipesService.updateRecipe(id, recipe);
        if (oldRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Рецепт #" + id + " удален");
    }
    @Operation(summary = "Удаление рецепта по ID")
    @Parameters(value =
    @Parameter(
            name = "id",
            example = "1"
    )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт с таким ID найден и удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт с таким ID не найден"
            )
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int id) {
        Recipe deletedRecipe = recipesService.deleteRecipe(id);
        if (deletedRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Рецепт #" + id + " удален");
    }
}