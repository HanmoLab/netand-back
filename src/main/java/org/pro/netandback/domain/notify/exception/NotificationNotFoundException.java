package org.pro.netandback.domain.notify.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class NotificationNotFoundException extends BusinessException {
	public NotificationNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
