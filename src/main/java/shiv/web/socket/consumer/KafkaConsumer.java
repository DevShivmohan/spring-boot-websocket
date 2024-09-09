package shiv.web.socket.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class KafkaConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();

//    @RetryableTopic(
//            attempts = "5",
//            backoff = @Backoff(delay = 10000),
//            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
//    )
//    @KafkaListener(topics = "create-order", groupId = "order-transactions")
    public void listenWithRetry(@Payload String message,
                                @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        try {
            log.info("Processing message from partition {}: {}", partition, message);

            // Processing logic
            if (message.contains("Shiv")) {
                throw new RuntimeException("Simulating a failure");
            }

//            acknowledgment.acknowledge();  // Acknowledge after successful processing
        } catch (Exception e) {
            log.error("Error encountered, retrying...", e);
            throw e;  // Retry within Spring Retry mechanism
        }
    }

    @DltHandler
//    @KafkaListener(topics = "create-order-dlq", groupId = "order-transactions-dlq")
    public void listenToDlq(@Payload String message,@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partitio) {
        try {
            log.info("Processing message from DLQ: {}", message);

            // DLQ Processing logic
            // You can decide how to handle this message, e.g., logging, alerting, or forwarding to another topic
            if (message.contains("Shiv")) {
                log.error("Message still contains issue, won't be processed: {}", message);
                throw new RuntimeException("DLQ runtime error");
            }

//            acknowledgment.acknowledge();  // Acknowledge after successful processing from DLQ
        } catch (Exception e) {
            log.error("Failed again, leaving in DLQ: {}", message, e);
            // Leave the message in DLQ for manual investigation or other handling
        }
    }


//    @RetryableTopic(
//            attempts = "5",
//            backoff = @Backoff(delay = 10000),
//            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
//    )
//    @KafkaListener(topics = "create-order", groupId = "order-transactions")
    public void listenWithRetry1(@Payload String message,
                                @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        try {
            log.info("Processing message1 from partition {}: {}", partition, message);

            // Processing logic
            if (message.contains("Mohan")) {
                throw new RuntimeException("Simulating a failure");
            }

//            acknowledgment.acknowledge();  // Acknowledge after successful processing
        } catch (Exception e) {
            log.error("Error encountered, retrying...", e);
            throw e;  // Retry within Spring Retry mechanism
        }
    }
}
