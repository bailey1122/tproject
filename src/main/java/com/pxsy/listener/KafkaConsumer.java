package com.pxsy.listener;

import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class);

    // consumes the first line from a file and puts into output to the command line
    @KafkaListener(topics = "Kafka_top", groupId = "group_id")
    public void consume(String message) {
        LOGGER.info("Consuming a message by Kafka");
        System.out.println("Consumed message: " + message);
    }
}
