package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
