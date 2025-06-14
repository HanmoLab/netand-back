package org.pro.netandback.domain.product.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class ProductAlreadyExistsException extends BusinessException {
	public ProductAlreadyExistsException(ErrorCode errorCode) {
		super(errorCode);
	}
}
