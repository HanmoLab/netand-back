package org.pro.netandback.domain.notify.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class NotificationAccessDeniedException extends BusinessException {
	public NotificationAccessDeniedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
