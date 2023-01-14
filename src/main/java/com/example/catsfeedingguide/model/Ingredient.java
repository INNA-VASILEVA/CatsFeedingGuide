package com.example.catsfeedingguide.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import static com.example.catsfeedingguide.CheckData.isNumberNotNullAndPositive;
import static com.example.catsfeedingguide.CheckData.isStringNotNullAndNotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
public class Ingredient {
    private static final String DEFAULT_NAME = "<INGREDIENT_NAME>";
    private static final int DEFAULT_AMOUNT = 1;
    private static final String DEFAULT_MEASURE_UNIT = "г";

    @JsonProperty("name")
    private String name;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("measureUnit")
    private String measureUnit;

    public Ingredient(String name,
                      Integer amount,
                      String measureUnit) {
        setName(name);
        setAmount(amount);
        setMeasureUnit(measureUnit);
    }

    public void setName(String name) {
        if (isStringNotNullAndNotBlank(name)) {
            this.name = name;
        } else {
            this.name = DEFAULT_NAME;
        }
    }

    public void setAmount(Integer amount) {
        if (isNumberNotNullAndPositive(amount)) {
            this.amount = amount;
        } else {
            this.amount = 1;
        }
    }

    public void setMeasureUnit(String measureUnit) {
        if (isStringNotNullAndNotBlank(measureUnit)) {
            this.measureUnit = measureUnit;
        } else {
            this.measureUnit = DEFAULT_MEASURE_UNIT;
        }
    }
}
