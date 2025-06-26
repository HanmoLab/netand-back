package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class IssueAssignmentNotFoundException extends NotFoundException {
    public IssueAssignmentNotFoundException() {
        super(ErrorCode.ISSUE_ASSIGNMENT_NOT_FOUND);
    }
}
