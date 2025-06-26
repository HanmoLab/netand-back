package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class IssueCommentNotFoundException extends NotFoundException {
    public IssueCommentNotFoundException() {
        super(ErrorCode.ISSUE_COMMENT_NOT_FOUND);
    }
}
