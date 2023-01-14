package com.example.catsfeedingguide;

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
}
