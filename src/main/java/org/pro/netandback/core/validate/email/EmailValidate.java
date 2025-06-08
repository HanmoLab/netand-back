package org.pro.netandback.core.validate.email;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.EmailNotVerifiedException;
import org.springframework.stereotype.Component;

@Component
public class EmailValidate {

	public void validateEmailVerified(boolean emailVerified) {
		if (!emailVerified) {
			throw new EmailNotVerifiedException(ErrorCode.EMAIL_NOT_VERIFIED);
		}
	}

}
