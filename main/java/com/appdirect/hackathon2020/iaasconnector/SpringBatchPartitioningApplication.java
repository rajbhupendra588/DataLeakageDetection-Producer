package com.appdirect.hackathon2020.iaasconnector;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@EnableBatchProcessing
@ImportResource("classpath:/files/batchjob.xml")
@SpringBootApplication
public class SpringBatchPartitioningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchPartitioningApplication.class, args);
	}
}
