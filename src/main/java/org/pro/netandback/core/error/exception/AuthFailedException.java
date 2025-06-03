package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class AuthFailedException extends BusinessException {
	public AuthFailedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
