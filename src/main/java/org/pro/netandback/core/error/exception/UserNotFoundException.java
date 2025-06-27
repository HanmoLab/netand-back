package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
