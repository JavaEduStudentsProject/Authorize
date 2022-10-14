package com.bezkoder.spring.security.mongodb.kafka;

import com.bezkoder.spring.security.mongodb.models.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import java.util.Collections;

@Slf4j
public class MessageListener {
    @Autowired
    MessageProducer messageProducer;

    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, User> factory;

    @Autowired
    KafkaConsumerConfig config;

     KafkaListener kl;

    private final String topic = "SendUser";

    public MessageListener(){}

    public MessageListener(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }



public User listenerGetUserResponse(String userName) {
        log.info("Get response to a request from Database 'get user'");
        messageProducer.sendMessage(userName, "frontGetUser");
        log.info("Send response to front 'get user' = {}", userName);
        User user = null;
        Consumer<String, User> consumer = (Consumer<String, User>) factory.getConsumerFactory().createConsumer();
        consumer.subscribe(Collections.singleton(topic));

    try {
        while (true) {
            ConsumerRecords<String, User> userRecords = consumer.poll(10000);
            for (ConsumerRecord<String, User> record : userRecords)
            {
                user = record.value();
                return user;
            }
        }
    } finally {
        consumer.close();
    }

    }

}
