package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class IssueNotFoundException extends NotFoundException {
    public IssueNotFoundException() {
        super(ErrorCode.ISSUE_NOT_FOUND);
    }
}
