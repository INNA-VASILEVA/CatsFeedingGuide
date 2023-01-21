package com.example.catsfeedingguide.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.catsfeedingguide.CheckData.isNumberNotNullAndPositive;
import static com.example.catsfeedingguide.CheckData.isStringNotNullAndNotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
public class Recipe {
    private static final String DEFAULT_NAME = "<RECIPE_NAME>";
    private static final int DEFAULT_COOKING_TIME = 10;

    public static int counter = 0;

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cookingTime")
    private int cookingTime;
    @JsonProperty("ingredients")
    private List<Ingredient> ingredients;
    @JsonProperty("steps")
    private List<String> steps;

    public Recipe(String name,
                  Integer cookingTime,
                  List<Ingredient> ingredients,
                  List<String> steps) {
        id = ++counter;
        setName(name);
        setCookingTime(cookingTime);
        setIngredients(ingredients);
        setSteps(steps);
    }

    public void setId(Integer id) {
        if (isNumberNotNullAndPositive(id)) {
            this.id = id;
        }
    }

    public void setName(String name) {
        if (isStringNotNullAndNotBlank(name)) {
            this.name = name;
        } else {
            this.name = DEFAULT_NAME;
        }
    }

    public void setCookingTime(Integer cookingTime) {
        if (isNumberNotNullAndPositive(cookingTime)) {
            this.cookingTime = cookingTime;
        } else {
            this.cookingTime = DEFAULT_COOKING_TIME;
        }
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = Objects.requireNonNullElseGet(ingredients, ArrayList::new);
    }

    public void setSteps(List<String> steps) {
        this.steps = Objects.requireNonNullElseGet(steps, ArrayList::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(cookingTime, recipe.cookingTime)
                && Objects.equals(name, recipe.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, cookingTime);
    }
}