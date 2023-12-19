package org.Tayana.Microservices_RabbitMQ_Project.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
 
@Configuration
public class RabbitMqConfig
{
	
	//giving the name for queue dynamically in .properties file
		@Value("${rabbitmq.queue.json.name}")
		private String queue;
		
		 //giving the name for routing key dynamically in .properties file
		@Value("${rabbitmq.routing.json.key}")
		private String routingKey;
		
		//giving the name for exchange dynamically in .properties file
		@Value("${rabbitmq.exchange.json.name}")
		private String exchange;
		
		
		//spring bean for rabbitMQ queue
		@Bean
		 Queue queue()
		{
			return new Queue(queue);
		}
		
		//spring bean for rabbitMQ
		//this topicExchange checks for the specific Pattern to match to go for the matched pattern for the respected queue
		@Bean
		 TopicExchange exchange()
		{
			return new TopicExchange(exchange);
		}
		
		//these one binds the routingKey with both exchange and queue
		@Bean
		 Binding binding()
		{
			return BindingBuilder
					.bind(queue())
					.to(exchange())
					.with(routingKey);
		}
		
		@Bean
		 MessageConverter converter()
		{
			return new Jackson2JsonMessageConverter();
		}
		
 
		@Bean
		 AmqpTemplate amqpTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory)
		{
			
			RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
			rabbitTemplate.setMessageConverter(converter());
			return rabbitTemplate;
		}
		
	
 
}