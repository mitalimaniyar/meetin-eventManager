package org.jeavio.meetin.eventManager;

import org.jeavio.meetin.eventManager.dao.EventRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableEurekaClient
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {EventRepository.class})
public class MeetinEventManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetinEventManagerApplication.class, args);
	}

}
