package org.pro.netandback.domain.user.validate;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;
import org.pro.netandback.domain.user.exception.InvalidPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserValidate {

	private final PasswordEncoder passwordEncoder;
	public void validatePasswordMatch(String rawPassword, String encodedPassword) {
		if (rawPassword == null || !passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new InvalidPasswordException(ErrorCode.AUTHENTICATION_FAILED);
		}
	}
}
