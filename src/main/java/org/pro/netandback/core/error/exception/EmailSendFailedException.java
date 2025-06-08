package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class EmailSendFailedException extends BusinessException{
	public EmailSendFailedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
