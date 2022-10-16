package com.bezkoder.spring.security.mongodb;

import com.bezkoder.spring.security.mongodb.kafka.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class SpringBootMongodbLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongodbLoginApplication.class, args);
		log.warn("Authorize run! " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")));
	}

	@Bean
	public MessageListener messageListener() {
		return new MessageListener();
	}
}
