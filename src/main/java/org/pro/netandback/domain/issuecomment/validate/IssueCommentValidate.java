package org.pro.netandback.domain.issuecomment.validate;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.IssueCommentMismatchException;
import org.pro.netandback.core.error.exception.IssueCommentNotFoundException;
import org.pro.netandback.core.error.exception.UnauthorizedException;
import org.pro.netandback.domain.issuecomment.model.entity.IssueComment;
import org.pro.netandback.domain.issuecomment.repository.IssueCommentRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueCommentValidate {
    private final IssueCommentRepository issueCommentRepository;

    public IssueComment getCommentByIdOrThrow(Long commentId) {
        return issueCommentRepository.findById(commentId)
                .orElseThrow(IssueCommentNotFoundException::new);
    }

    public void validateCommentOwner(IssueComment comment, Long userId) {
        if (!comment.getWriter().getId().equals(userId)) {
            throw new UnauthorizedException(ErrorCode.ISSUE_ACCESS_DENIED);
        }
    }

    public void validateCommentInIssue(IssueComment comment, Long issueId) {
        if (!comment.getIssue().getId().equals(issueId)) {
            throw new IssueCommentMismatchException(ErrorCode.ISSUE_COMMENT_MISMATCH);
        }
    }
}
