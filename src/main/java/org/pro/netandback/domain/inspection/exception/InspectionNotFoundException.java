package org.pro.netandback.domain.inspection.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class InspectionNotFoundException extends BusinessException {
	public InspectionNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
