package com.emailtech.hackaton.kafka.rest;

import com.emailtech.hackaton.kafka.dto.UserPayload;
import com.emailtech.hackaton.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer producer;

    @Value("${test.topic}")
    private String topic;

    @PostMapping(value = "/publish", consumes = "application/json")
    public void sendMessageToKafkaTopic(@RequestBody UserPayload message) {
        this.producer.send(topic, message);
    }
}
