package com.example.foodservice.service;

import com.example.foodservice.dto.FoodDto;
import com.example.foodservice.model.Food;
import com.example.foodservice.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodDto> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream()
                .map(this::convertToDto)
                .toList();
    }

    private FoodDto convertToDto(Food food) {
        return new FoodDto(food.getId(), food.getName(), food.getPrice());
    }
}
