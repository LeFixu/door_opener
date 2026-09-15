package ch.door_opener.api.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitDoorEventPublisher implements DoorEventPublisher {

	private final RabbitTemplate rabbitTemplate;
	private final String exchange;
	private final String routingKey;

	public RabbitDoorEventPublisher(
			RabbitTemplate rabbitTemplate,
			@Value("${app.rabbitmq.exchange}") String exchange,
			@Value("${app.rabbitmq.routing-key}") String routingKey) {
		this.rabbitTemplate = rabbitTemplate;
		this.exchange = exchange;
		this.routingKey = routingKey;
	}

	@Override
	public void publish(DoorEvent event) {
		rabbitTemplate.convertAndSend(exchange, routingKey, event);
	}
}