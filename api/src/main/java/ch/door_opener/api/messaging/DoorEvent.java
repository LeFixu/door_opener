package ch.door_opener.api.messaging;

import java.time.Instant;
import java.util.UUID;

public record DoorEvent(UUID eventId, UUID doorId, boolean open, Instant occurredAt) {

	public DoorEvent(UUID eventId, UUID doorId, boolean open) {
		this(eventId, doorId, open, Instant.now());
	}
}