package org.pro.netandback.domain.user.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {
	public InvalidPasswordException(ErrorCode errorCode) {
		super(errorCode);
	}
}
