package com.example.foodservice.controller;

import com.example.foodservice.dto.FoodDto;
import com.example.foodservice.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/foods")
    public ResponseEntity<List<FoodDto>> getAllFoods() {
        List<FoodDto> foods = foodService.getAllFoods();
        if(foods.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(foods);
        }
    }
}