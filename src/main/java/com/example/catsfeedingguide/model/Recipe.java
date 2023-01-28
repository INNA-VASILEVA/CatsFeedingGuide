package com.example.catsfeedingguide.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Recipe {
    private Integer id;
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> steps;

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