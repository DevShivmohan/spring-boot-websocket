package com.kafka.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaPayload {
    private final String id = UUID.randomUUID().toString();

    private String name;

    private String dateTime;
}
