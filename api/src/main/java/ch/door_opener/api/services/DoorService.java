package ch.door_opener.api.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ch.door_opener.api.models.Door;
import ch.door_opener.api.repositories.DoorRepository;
import ch.door_opener.api.messaging.DoorEvent;
import ch.door_opener.api.messaging.DoorEventPublisher;

@Service
public class DoorService {

	private final DoorRepository doorRepository;
	private final DoorEventPublisher doorEventPublisher;

	public DoorService(DoorRepository doorRepository, DoorEventPublisher doorEventPublisher) {
		this.doorRepository = doorRepository;
		this.doorEventPublisher = doorEventPublisher;
	}

	public void setDoor(UUID id, boolean open) {
		doorRepository.save(new Door(id, open));
		doorEventPublisher.publish(new DoorEvent(UUID.randomUUID(), id, open));
	}
}