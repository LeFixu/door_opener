package ch.door_opener.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.door_opener.api.models.Door;

public interface DoorRepository extends JpaRepository<Door, UUID> {
}