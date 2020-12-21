package com.appdirect.hackathon2020.iaasconnector.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.appdirect.hackathon2020.iaasconnector.config.RabbitMQProperties;

@Component
public class JobAMQPProducer {
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Autowired
	RabbitMQProperties rabbitMQProperties;

	@Value("job-exchange")
	private String exchange1;

	@Value("job-routingkey")
	private String routingKey1;

	public void sendJobMessage(String msg) {
		System.out.println("Sending Job Message = " + msg);
		amqpTemplate.convertAndSend(exchange1, routingKey1, msg);
	}
}
