package ch.door_opener.api.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@GetMapping("/auth/me")
	public AuthResponse currentUser(Authentication authentication) {
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new AuthResponse(false, null, null);
		}

		String name = authentication.getName();
		String email = null;
		if (authentication.getPrincipal() instanceof OAuth2User user) {
			name = user.getAttribute("name");
			email = user.getAttribute("email");
		}

		return new AuthResponse(true, name, email);
	}

	public record AuthResponse(boolean authenticated, String name, String email) {
	}
}