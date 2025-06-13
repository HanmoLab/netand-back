package org.pro.netandback.domain.product.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class InspectionNotFoundException extends BusinessException {
	public InspectionNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
