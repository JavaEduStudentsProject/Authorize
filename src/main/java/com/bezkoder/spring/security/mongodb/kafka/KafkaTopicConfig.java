package com.bezkoder.spring.security.mongodb.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic registerUser() {
        return new NewTopic("registerUser", 2, (short) 1);
    }

    @Bean
    public NewTopic parseFileParser() {
        return new NewTopic("parseFileParser", 1, (short) 1);
    }

    @Bean
    public NewTopic sendUser() {
        return new NewTopic("SendUser", 1, (short) 1);
    }
}
