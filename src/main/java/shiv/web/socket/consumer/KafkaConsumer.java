package shiv.web.socket.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class KafkaConsumer {
    private int count=0;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "create-order", groupId = "order-transactions",containerFactory = "kafkaListenerContainerFactory")
//    @Retryable(value = {RuntimeException.class}, maxAttempts = 5)
    public void listen(String message, Acknowledgment ack) {
        try {
            count++;
            log.info("counter {} and data {}", count, message);
            if (count > 0) {
//                ack.acknowledge();  // commit offset manually
                throw new RuntimeException("Forcing error");
            }
            ack.acknowledge();
        } catch (RuntimeException e) {
            // retry logic or log error
            throw e;
        }
    }
}
