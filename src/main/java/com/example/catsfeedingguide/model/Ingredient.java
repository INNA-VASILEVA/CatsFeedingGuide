package com.example.catsfeedingguide.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Ingredient {
    private static int counter = 0;

    private Integer id;
    private String name;
    private int amount;
    private String measureUnit;

    public Ingredient(String name,
                      Integer amount,
                      String measureUnit) {
        id = ++counter;
        setName(name);
        setAmount(amount);
        setMeasureUnit(measureUnit);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient ingredient = (Ingredient) o;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(name, ingredient.name)
                .append(amount, ingredient.amount)
                .append(measureUnit, ingredient.measureUnit);

        return eb.isEquals();
    }
    @Override
    public int hashCode() {
        HashCodeBuilder hb = new HashCodeBuilder();
        hb.append(name)
                .append(amount)
                .append(measureUnit);

        return hb.toHashCode();

    }
}