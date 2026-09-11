package ch.door_opener.api.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Door {

	@Id
	private UUID id;

	private boolean open;

	protected Door() {
	}

	public Door(UUID id, boolean open) {
		this.id = id;
		this.open = open;
	}

	public UUID getId() {
		return id;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}