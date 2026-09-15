package ch.door_opener.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import ch.door_opener.api.models.Door;
import ch.door_opener.api.messaging.DoorEventPublisher;
import ch.door_opener.api.repositories.DoorRepository;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DoorRepository doorRepository;

	@MockitoBean
	private DoorEventPublisher doorEventPublisher;

	@Test
	void contextLoads() {
	}

	@Test
	void generatesXsrfTokenCookie() throws Exception {
		mockMvc.perform(get("/csrf"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.headerName").value("X-CSRF-TOKEN"))
			.andExpect(jsonPath("$.token").isNotEmpty());
	}

	@Test
	void updatesDoorThroughControllerServiceAndRepository() throws Exception {
		UUID doorId = UUID.randomUUID();

		mockMvc.perform(put("/door")
				.with(user("test-user"))
				.with(csrf())
				.contentType(APPLICATION_JSON)
				.content("{\"id\":\"" + doorId + "\",\"open\":true}"))
			.andExpect(status().isOk());

		Door door = doorRepository.findById(doorId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertTrue(door.isOpen());
		verify(doorEventPublisher).publish(argThat(event ->
			event.doorId().equals(doorId) && event.open()));
	}

}
