package com.example.catsfeedingguide.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Getter
@Setter
@ToString
public class Recipe {
    private static final String DEFAULT_NAME = "<RECIPE_NAME>";
    private static final int DEFAULT_COOKING_TIME = 10;

    public static int counter = 0;

    private Integer id;
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(cookingTime, recipe.cookingTime)
                .append(name, recipe.name);
        return eb.isEquals();
    }
    @Override
    public int hashCode() {
        HashCodeBuilder hb = new HashCodeBuilder();
        hb.append(cookingTime)
                .append(name);

        return hb.toHashCode();
    }
}