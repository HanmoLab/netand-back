package org.pro.netandback.core.auth.jwt;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtSkipPathMatcher {
	private static final List<String> SKIP_PREFIXES = List.of(
		"/api/v1/auth/login",
		"/api/v1/auth/signup",
		"/api/v1/auth/reissue",
		"/api/v1/auth/email",
		"/v3/api-docs",
		"/swagger-ui",
		"/swagger-resources",
		"/webjars"
	);

	public boolean shouldSkip(HttpServletRequest request) {
		String path = request.getRequestURI();
		if (SKIP_PREFIXES.stream().anyMatch(path::startsWith)) {
			return true;
		}
		return false;
	}
}
