package com.appdirect.hackathon2020.iaasconnector.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.appdirect.hackathon2020.iaasconnector.config.RabbitMQProperties;
import com.appdirect.hackathon2020.iaasconnector.model.InvoiceWrapper;

@Component
public class AMQPProducer {
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Autowired
	RabbitMQProperties rabbitMQProperties;

	@Value("${jsa.rabbitmq.exchange}")
	private String exchange;

	@Value("${jsa.rabbitmq.routingkey}")
	private String routingKey;

	public void sendMessage(InvoiceWrapper msg){
		System.out.println("Send msg = " + msg);
		amqpTemplate.convertAndSend(exchange, routingKey, msg);
	}
}
