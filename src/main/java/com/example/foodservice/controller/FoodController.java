package com.example.foodservice.controller;

import com.example.foodservice.dto.FoodDto;
import com.example.foodservice.service.FoodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodController {

    private FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/foods")
    public List<FoodDto> getAllFoods() {
        return foodService.getAllFoods();
    }
}