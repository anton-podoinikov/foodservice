package com.example.foodservice.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponseMessage {
    private String token;
    private Long orderId;
    private Boolean completed;

}
