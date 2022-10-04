package com.bezkoder.spring.security.mongodb.kafka;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageListener {

    MessageProducer messageProducer;

    public MessageListener(){}

    public MessageListener(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }
}
