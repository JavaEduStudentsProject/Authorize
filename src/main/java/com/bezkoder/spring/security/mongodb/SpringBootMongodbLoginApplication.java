package com.bezkoder.spring.security.mongodb;

import com.bezkoder.spring.security.mongodb.kafka.MessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootMongodbLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongodbLoginApplication.class, args);
	}

	@Bean
	public MessageListener messageListener() {
		return new MessageListener();
	}
}
