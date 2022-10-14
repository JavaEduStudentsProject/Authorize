package com.bezkoder.spring.security.mongodb.kafka;

import com.bezkoder.spring.security.mongodb.models.User;
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
public class MessageUserProducer {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User user, String topicName) {
        ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send(topicName,user);

        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Unable to send message = {} dut to: {}", user, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, User> stringDataSendResult) {
                log.info("Message sent successfully with offset = {}", stringDataSendResult.getRecordMetadata().offset());
            }
        });
    }
}
