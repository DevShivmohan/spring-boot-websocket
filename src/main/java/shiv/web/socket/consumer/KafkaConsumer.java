package shiv.web.socket.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class KafkaConsumer {
    private int count=0;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 2000, multiplier = 2.0),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "create-order", groupId = "order-transactions")
    public void listenWithRetry(String message, Acknowledgment ack) {
        try {
            log.info("Processing message: {}", message);

            // Processing logic
            if (message.contains("Shiv")) {
                throw new RuntimeException("Simulating a failure");
            }

            ack.acknowledge();  // Acknowledge after successful processing
        } catch (Exception e) {
            log.error("Error encountered, retrying...", e);
            throw e;  // Retry within Spring Retry mechanism
        }
    }


    @KafkaListener(topics = "create-order-dlq", groupId = "order-transactions-dlq")
    public void listenToDlq(String message, Acknowledgment ack) {
        try {
            log.info("Retrying message from DLQ: {}", message);
            // Retry logic for failed messages
            if(message.contains("Shiv")){
                throw new RuntimeException("DLQ runtime error");
            }
            ack.acknowledge();  // Acknowledge after successful processing

        } catch (Exception e) {
            log.error("Failed again, leaving in DLQ: {}", message, e);
            // Optionally handle retries within DLQ as well
        }
    }

}
