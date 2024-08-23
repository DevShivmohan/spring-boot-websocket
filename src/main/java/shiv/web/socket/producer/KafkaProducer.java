package shiv.web.socket.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendDummyMessages() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, String> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("name", "Shivmohan");
        kafkaTemplate.send("create-order", objectMapper.writeValueAsString(map));
        log.info("Emitted kafka event {}", map);
    }
}
