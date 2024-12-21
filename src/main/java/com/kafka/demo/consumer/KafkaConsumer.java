package com.kafka.demo.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.demo.model.KafkaPayload;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class KafkaConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    //    @KafkaListener(
//            topicPartitions = {
//                    @TopicPartition(topic = "thing1", partitionOffsets = {
//                            @PartitionOffset(partition = "0", initialOffset = "0"),
//                            @PartitionOffset(partition = "1", initialOffset = "0"),
//                            @PartitionOffset(partition = "2", initialOffset = "0")
//                    })
//            }
//    )
    @KafkaListener(topics = "thing1", groupId = "group_id")
    public void consumeFromAllPartitions(ConsumerRecord<String, String> record) throws JsonProcessingException {
        final var payload = objectMapper.readValue(record.value(), KafkaPayload.class);
//        if(payload.getName().toLowerCase().contains("exception")){
//            throw new RuntimeException("Force fully raised");
//        }
        log.info("Consumed message from partition {} offset {} and value {}", record.partition(), record.offset(), record.value());
    }
}
