package ch.door_opener.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI doorOpenerOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Door Opener API")
						.version("v1"))
				.components(new Components().addSecuritySchemes(
					"csrfToken",
					new SecurityScheme()
						.type(SecurityScheme.Type.APIKEY)
						.in(SecurityScheme.In.HEADER)
						.name("X-XSRF-TOKEN")
						.description("CSRF token returned by /csrf endpoint")));
	}
}