package ch.door_opener.api.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.door_opener.api.services.DoorService;

@RestController
public class DoorController {

	private final DoorService doorService;

	public DoorController(DoorService doorService) {
		this.doorService = doorService;
	}

	@PutMapping("/door")
	public void updateDoor(@RequestBody DoorRequest request) {
		doorService.setDoor(request.id(), request.open());
	}

	public record DoorRequest(UUID id, boolean open) {
	}
}