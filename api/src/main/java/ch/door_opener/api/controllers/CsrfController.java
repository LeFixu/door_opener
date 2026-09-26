package ch.door_opener.api.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class CsrfController {

	@GetMapping("/csrf")
	public CsrfToken csrfToken(@Parameter(hidden = true) CsrfToken csrfToken) {
		return csrfToken;
	}
}