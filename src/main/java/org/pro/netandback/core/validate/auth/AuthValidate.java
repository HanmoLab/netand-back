package org.pro.netandback.core.validate.auth;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.EmailAlreadyExistsException;
import org.pro.netandback.core.error.exception.EmailNotVerifiedException;
import org.pro.netandback.core.error.exception.InvalidValueException;
import org.pro.netandback.domain.user.model.type.UserType;
import org.springframework.stereotype.Component;

@Component
public class AuthValidate {

	public void validateDuplicateEmail(boolean exists) {
		if (exists) {
			throw new EmailAlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
		}
	}

	public void validateSignupParam(UserType userType) {
		if (userType == null) {
			throw new InvalidValueException(ErrorCode.INVALID_REQUEST);
		}
	}

}
