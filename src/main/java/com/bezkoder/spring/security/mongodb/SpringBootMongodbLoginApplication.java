package com.bezkoder.spring.security.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.MessageListener;

@SpringBootApplication
public class SpringBootMongodbLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongodbLoginApplication.class, args);
	}

//	@Bean
//	public MessageListener messageListener() {
//		return new MessageListener();
//	}
}
