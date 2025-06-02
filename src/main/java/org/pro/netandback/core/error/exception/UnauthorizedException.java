package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class UnauthorizedException extends BusinessException {
	public UnauthorizedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
