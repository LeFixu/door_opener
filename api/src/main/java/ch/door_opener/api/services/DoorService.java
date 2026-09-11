package ch.door_opener.api.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ch.door_opener.api.models.Door;
import ch.door_opener.api.repositories.DoorRepository;

@Service
public class DoorService {

	private final DoorRepository doorRepository;

	public DoorService(DoorRepository doorRepository) {
		this.doorRepository = doorRepository;
	}

	public void setDoor(UUID id, boolean open) {
		doorRepository.save(new Door(id, open));
	}
}