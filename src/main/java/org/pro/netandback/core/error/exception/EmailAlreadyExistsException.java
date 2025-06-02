package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class EmailAlreadyExistsException extends BusinessException {

	public EmailAlreadyExistsException(ErrorCode errorCode) {
		super(errorCode);
	}

}
