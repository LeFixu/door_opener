package ch.door_opener.api.messaging;

public interface DoorEventPublisher {

	void publish(DoorEvent event);
}