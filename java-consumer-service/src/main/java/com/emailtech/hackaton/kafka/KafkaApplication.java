package com.emailtech.hackaton.kafka;

import com.emailtech.hackaton.kafka.dto.UserPayload;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KafkaApplication {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    /* producer related beans */
    @Bean
    public ProducerFactory<String, UserPayload> userPayloadProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, UserPayload> userPayloadKafkaTemplate(ProducerFactory<String, UserPayload> userPayloadProducerFactory) {
        return new KafkaTemplate<>(userPayloadProducerFactory);
    }

    /* consumer related beans */
    @Bean
    public ConsumerFactory<String, UserPayload> userPayloadConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "greeting");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(UserPayload.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserPayload> userPayloadKafkaListenerContainerFactory(ConsumerFactory<String, UserPayload> userPayloadConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserPayload> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userPayloadConsumerFactory);
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }
}
