package com.product.affiliation.sender;

import com.product.affiliation.events.ProductPayload;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProductEventSender implements EventSender {

    private final String topicName;
    private final Map<String, String> kafkaMergedConfig;
    private final KafkaProducer<UUID, ProductPayload> producer;


    public ProductEventSender(String topicName, Map<String, String> kafkaConfig) {
        this.topicName = topicName;
        this.kafkaMergedConfig = new LinkedHashMap<>();

        if(kafkaConfig != null && !kafkaConfig.isEmpty()) {
            this.kafkaMergedConfig.putAll(kafkaConfig);
        }

        this.producer = new KafkaProducer<>(kafkaMergedConfig);
    }

    @Override
    public Future<RecordMetadata> send(ProductPayload payload) {
        ProducerRecord<UUID, ProductPayload> kafkaRecord =
                new ProducerRecord<>(this.topicName, payload.getId(), payload);

        return this.producer.send(kafkaRecord);
    }

    @Override
    public void close() throws IOException {
        this.producer.close();
    }
}
