package ch.door_opener.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import ch.door_opener.api.models.Door;
import ch.door_opener.api.repositories.DoorRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DoorRepository doorRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void updatesDoorThroughControllerServiceAndRepository() throws Exception {
		UUID doorId = UUID.randomUUID();

		mockMvc.perform(put("/door")
				.contentType(APPLICATION_JSON)
				.content("{\"id\":\"" + doorId + "\",\"open\":true}"))
			.andExpect(status().isOk());

		Door door = doorRepository.findById(doorId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertTrue(door.isOpen());
	}

}
