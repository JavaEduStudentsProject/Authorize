package com.bezkoder.spring.security.mongodb.kafka;

import com.bezkoder.spring.security.mongodb.models.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Properties;

@Slf4j
public class MessageListener {
    @Autowired
    MessageProducer messageProducer;

    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, User> factory;

    private final String clientId = "myApplicationName";
    private final String groupId = "mygroupId";
    private final String endpoints = "localhost:9092";
    private final String topic = "SendUser";
    private final String autoOffsetResetPolicy = "earliest";
    private final String securityProtocol = "SASL_SSL";
    private final String securitySaslMechanism = "SCRAM-SHA-256";
//    private final String keyDeserializer = JsonDeserializer<User>(User.class);
//    private final String valueDeserializer = StringDeserializer.class.getCanonicalName();

    public MessageListener(){}

    public MessageListener(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

//    @KafkaListener(topics = "SendUser", containerFactory = "kafkaListenerContainerFactory")
    public User listenerGetUserResponse(String userName) {
        log.info("Get response to a request from Database 'get user'");
        messageProducer.sendMessage(userName, "frontGetUser");
        log.info("Send response to front 'get user' = {}", userName);
//        factory.getConsumerFactory().createConsumer();
        Consumer<String, User> consumer = (Consumer<String, User>) factory.getConsumerFactory().createConsumer();
        consumer.subscribe(Collections.singletonList(topic));

        ConsumerRecords<String, User> userRecords = consumer.poll(10000);
        System.out.println(userRecords);
        userRecords.records(topic);
        User user = null;
        for(ConsumerRecord<String, User> record : userRecords) {
            user = record.value();
        }
        System.out.println(user);
        return user;
    }

    private Properties getProperties() {
        Properties props = new Properties();
//        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, endpoints);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetPolicy);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, new StringDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, new StringDeserializer());
//        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
//        props.put(SaslConfigs.SASL_MECHANISM, securitySaslMechanism);
        return props;
    }
}
