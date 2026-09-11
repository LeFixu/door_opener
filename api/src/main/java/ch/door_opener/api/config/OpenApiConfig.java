package ch.door_opener.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI doorOpenerOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Door Opener API")
						.version("v1"));
	}
}