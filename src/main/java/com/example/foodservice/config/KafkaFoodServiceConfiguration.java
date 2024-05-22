package com.example.foodservice.config;

import com.example.foodservice.message.FoodRequestMessage;
import com.example.foodservice.message.FoodResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaFoodServiceConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, FoodResponseMessage>
    foodResponseMessageProducerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config, new StringSerializer()
                , new JsonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, FoodResponseMessage>
    foodResponseMessageKafkaTemplate(ProducerFactory<String
            , FoodResponseMessage> foodResponseMessageProducerFactory) {
        return new KafkaTemplate<>(foodResponseMessageProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, FoodRequestMessage>
    foodRequestMessageConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer()
                , new JsonDeserializer<>(FoodRequestMessage.class, objectMapper, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FoodRequestMessage>
    foodRequestMessageKafkaListenerContainerFactory(ConsumerFactory<String, FoodRequestMessage>
                                                            foodRequestMessageConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, FoodRequestMessage> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(foodRequestMessageConsumerFactory);
        return factory;
    }
}
