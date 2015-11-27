package com.demo.config;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.demo.messaging.MessageSender;

@Profile({ "cloud", "rabbit" })
@EnableRabbit
@Configuration
public class AmqpConfig {

	@Value("${rabbit.exchangeName:metricsExchange}")
	private String rabbitExchangeName = "metricsExchange";
	
	@Value("${rabbit.durable:true}")
	private boolean isRabbitDurable = true;
		
	@Value("${rabbit.autodelete:false}")
	private boolean isRabbitAutoDelete = false;

	
//	@Bean
//	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//		if( connectionFactory == null ) {
//			return null;
//		}
//		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//		rabbitAdmin.setIgnoreDeclarationExceptions(true);
//		return rabbitAdmin;
//	}

	@Bean
	public AbstractExchange testExchange() {
	    return new FanoutExchange(rabbitExchangeName, isRabbitDurable, isRabbitAutoDelete);
	}
	
//	@Bean
//	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//		if( connectionFactory == null ) {
//			return new RabbitTemplate() {
//				@Override
//				public void afterPropertiesSet() {
//				}
//				
//			};
//		}
//		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		return rabbitTemplate;
//	}

	
	@Bean
	public MessageSender amqpMessageSender(RabbitTemplate rabbitTemplate) {
		return s -> { rabbitTemplate.convertAndSend(s); };
	}
	
}
