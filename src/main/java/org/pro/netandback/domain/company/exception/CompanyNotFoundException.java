package org.pro.netandback.domain.company.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {
	public ProductNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
