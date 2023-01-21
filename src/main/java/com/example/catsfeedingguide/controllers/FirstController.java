package com.example.catsfeedingguide.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping ("*")
    public String helloWorld() {
        return "Приложение запущено";
    }

    @GetMapping ("/info")
    public String info () {
        return "Имя ученика: Васильева Инна<br />" + "Название проекта: Рецепты еды для кошек<br />" + "Дата создания проекта: 30.12.2022<br />" + "Описание: Полезные рецепты еды для кошек с расчетом калорийности<br />";
    }
}
