package org.pro.netandback.domain.s3.exception;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;

public class ProfileImageException extends BusinessException {
	public ProfileImageException(ErrorCode errorCode) {
		super(errorCode);
	}
}
