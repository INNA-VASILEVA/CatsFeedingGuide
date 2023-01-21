package com.example.catsfeedingguide;

import com.example.catsfeedingguide.model.Ingredient;
import com.example.catsfeedingguide.model.Recipe;
import com.example.catsfeedingguide.services.impl.IngredientsServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

public class CheckData {
    public static boolean isStringNotNullAndNotBlank(String str) {
        return str != null && !str.isBlank();
    }

    public static <T extends Number> boolean isNumberNotNullAndNotNegative(T num) {
        return num != null && num.doubleValue() >= 0;
    }

    public static <T extends Number> boolean isNumberNotNullAndPositive(T num) {
        return num != null && num.doubleValue() > 0;
    }

    public static boolean isStringRussian(String str) {
        String regex = "^([А-Яа-я0-9\t ]|\\p{Punct})+";
        Pattern pattern = Pattern.compile(regex);
        return str != null && pattern.matcher(str).matches();
    }

    public static void addNewIngredients(Recipe recipe) {
        Collection<Ingredient> ingredients = IngredientsServiceImpl.ingredients.values();
        label:
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            for (Ingredient ingredient : ingredients) {
                if (recipe.getIngredients().get(i).equals(ingredient)) {
                    recipe.getIngredients().add(ingredient);
                    Collections.swap(recipe.getIngredients(), i, recipe.getIngredients().size() - 1);
                    recipe.getIngredients().remove(recipe.getIngredients().size() - 1);
                    continue label;
                }
            }
            IngredientsServiceImpl.ingredients.put(
                    recipe.getIngredients().get(i).getId(),
                    recipe.getIngredients().get(i));
        }
    }
}
