package com.emailtech.hackaton.kafka.consumer;

import com.emailtech.hackaton.kafka.dto.UserPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${test.topic}", containerFactory = "userPayloadKafkaListenerContainerFactory")
    public void receive(UserPayload userPayload) {
        log.info("Received payload: {}", userPayload);
    }
}
