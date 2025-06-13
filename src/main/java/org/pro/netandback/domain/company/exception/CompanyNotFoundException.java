package org.pro.netandback.domain.company.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class CompanyNotFoundException extends BusinessException {
	public CompanyNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
