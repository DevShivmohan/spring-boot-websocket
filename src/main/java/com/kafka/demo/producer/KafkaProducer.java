package com.kafka.demo.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.demo.model.KafkaPayload;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
@Log4j2
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedRate = 100L)
//    @PostConstruct
    public void sendKafkaData() throws JsonProcessingException {
        kafkaTemplate.send("thing1", objectMapper.writeValueAsString(KafkaPayload.builder()
                        .name("Exception").dateTime(LocalDateTime.now()
                                .format(DateTimeFormatter.ISO_DATE_TIME)).build()))
                .whenComplete((stringKafkaPayloadSendResult, throwable) -> {
                    if (throwable == null) {
                        log.info("data published ");
                    }
                });
    }
}
