package ch.door_opener.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			ObjectProvider<ClientRegistrationRepository> clientRegistrations,
			@Value("${app.oauth2.success-url:/}") String successUrl) throws Exception {
		CookieCsrfTokenRepository csrfRepository =
            CookieCsrfTokenRepository.withHttpOnlyFalse();

		http
			.csrf(csrf -> csrf
				.csrfTokenRepository(csrfRepository)
				.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/", "/auth/me", "/xsrf", "/error", "/login/**", "/oauth2/**").permitAll()
				.anyRequest().authenticated())
			.logout(logout -> logout.logoutSuccessUrl("/"));

		if (clientRegistrations.getIfAvailable() != null) {
			http.oauth2Login(oauth2 -> oauth2.defaultSuccessUrl(successUrl, true));
		}

		return http.build();
	}
}