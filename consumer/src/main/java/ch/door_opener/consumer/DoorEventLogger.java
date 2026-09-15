package ch.door_opener.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DoorEventLogger {

	private static final Logger defaultLogger = LoggerFactory.getLogger(DoorEventLogger.class);
	private final Logger logger;

	public DoorEventLogger() {
		this(defaultLogger);
	}

	DoorEventLogger(Logger logger) {
		this.logger = logger;
	}

	@PostConstruct
	void announceListening() {
		logger.info("RabbitMQ consumer is listening on queue door-update-logger");
	}

	@RabbitListener(queues = "${app.rabbitmq.queue}")
	public void log(DoorEvent event) {
		logger.info("Received door.updated event: eventId={}, doorId={}, open={}, occurredAt={}",
				event.eventId(), event.doorId(), event.open(), event.occurredAt());
	}
}