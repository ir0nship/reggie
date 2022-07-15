package com.ironship.reggie.controller;

import com.ironship.reggie.service.DishService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
public class DishFlavorController {
    private final DishService dishService;

    public DishFlavorController(DishService dishService) {
        this.dishService = dishService;
    }
}

