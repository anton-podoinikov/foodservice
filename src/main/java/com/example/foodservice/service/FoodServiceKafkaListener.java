package com.example.foodservice.service;

import com.example.foodservice.message.FoodRequestMessage;
import com.example.foodservice.message.FoodResponseMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceKafkaListener {

    private final KafkaTemplate<String, FoodResponseMessage> kafkaTemplate;

    public FoodServiceKafkaListener(KafkaTemplate<String, FoodResponseMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "food-request", groupId = "food-service")
    public void listen(FoodRequestMessage message) {
        FoodResponseMessage responseMessage = new FoodResponseMessage(
                message.getToken(),
                message.getOrderId(),
                true
        );
        kafkaTemplate.send("food-response", responseMessage);
    }
}
