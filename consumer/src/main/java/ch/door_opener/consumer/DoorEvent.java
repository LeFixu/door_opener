package ch.door_opener.consumer;

import java.time.Instant;
import java.util.UUID;

public record DoorEvent(UUID eventId, UUID doorId, boolean open, Instant occurredAt) {
}