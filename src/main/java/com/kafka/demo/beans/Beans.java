package com.kafka.demo.beans;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("thing2", 3, (short) 1);
    }
}
