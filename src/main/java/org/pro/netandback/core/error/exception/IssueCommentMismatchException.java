package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class IssueCommentMismatchException extends BusinessException{
    public IssueCommentMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
