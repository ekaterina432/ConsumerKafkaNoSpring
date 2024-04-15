package consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "test-group");



        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topics = new ArrayList<>();
        topics.add("no-spring-topic");
        kafkaConsumer.subscribe(topics);
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(10);
                for (ConsumerRecord<String, String> record : records) {
                    logger.info(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(), record.partition(), record.value()));
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred", e);
        } finally {
            kafkaConsumer.close();
        }
    }
}
