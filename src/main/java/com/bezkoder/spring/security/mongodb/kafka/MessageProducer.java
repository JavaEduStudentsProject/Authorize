package com.bezkoder.spring.security.mongodb.kafka;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@NoArgsConstructor
@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String product, String topicName) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName,product);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Unable to send message = {} dut to: {}", product, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringDataSendResult) {
                log.info("Message sent successfully with offset = {}", stringDataSendResult.getRecordMetadata().offset());
            }
        });
    }
}
