package ch.door_opener.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	TopicExchange doorEventsExchange() {
		return new TopicExchange("door.events", true, false);
	}

	@Bean
	Queue doorUpdateLoggerQueue() {
		return QueueBuilder.durable("door-update-logger").build();
	}

	@Bean
	Binding doorUpdateBinding(Queue doorUpdateLoggerQueue, TopicExchange doorEventsExchange) {
		return BindingBuilder.bind(doorUpdateLoggerQueue)
				.to(doorEventsExchange)
				.with("door.updated");
	}

	@Bean
	JacksonJsonMessageConverter rabbitMessageConverter() {
		return new JacksonJsonMessageConverter();
	}
}