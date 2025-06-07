package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class EmailNotVerifiedException extends BusinessException{
	public EmailNotVerifiedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
