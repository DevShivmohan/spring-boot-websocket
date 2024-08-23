package shiv.web.socket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiv.web.socket.producer.KafkaProducer;

@RestController
@RequestMapping("/kafka")
@AllArgsConstructor
public class KafkaRestController {
    private final KafkaProducer kafkaProducer;

    @GetMapping
    public ResponseEntity<?> emitKafkaEvent() throws JsonProcessingException {
        kafkaProducer.sendDummyMessages();
        return ResponseEntity.ok().build();
    }
}
