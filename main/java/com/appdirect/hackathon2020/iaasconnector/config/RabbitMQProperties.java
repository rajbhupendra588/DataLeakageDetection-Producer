package com.appdirect.hackathon2020.iaasconnector.config;

import lombok.Data;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
@ToString
public class RabbitMQProperties {
	private String queueName;
	private String exchangeName;
	private String routingKey;
}
