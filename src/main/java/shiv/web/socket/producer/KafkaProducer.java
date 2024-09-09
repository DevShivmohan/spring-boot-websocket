package shiv.web.socket.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendDummyMessages(String value) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, String> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("name", "Shivmohan");
        kafkaTemplate.send("create-order", value);
        log.info("Emitted kafka event {}", value);
    }

    @Scheduled(fixedRate = 1000L)
    public void sendDummyMessages() throws JsonProcessingException {
        final var uuid = UUID.randomUUID().toString();
        log.info("sending message {}", uuid);
        sendDummyMessages(uuid);
        log.info("sent message {}", uuid);
    }
}
