package ch.door_opener.consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class DoorEventLoggerTest {

	@Test
	void acceptsDoorEvents() {
		Logger eventLogger = mock(Logger.class);
		DoorEventLogger logger = new DoorEventLogger(eventLogger);
		UUID eventId = UUID.randomUUID();
		UUID doorId = UUID.randomUUID();
		Instant occurredAt = Instant.now();
		DoorEvent event = new DoorEvent(eventId, doorId, true, occurredAt);

		logger.log(event);

		verify(eventLogger).info("Received door.updated event: eventId={}, doorId={}, open={}, occurredAt={}",
				eventId, doorId, true, occurredAt);
	}
}