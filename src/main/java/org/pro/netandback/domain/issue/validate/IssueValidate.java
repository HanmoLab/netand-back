package org.pro.netandback.domain.issue.validate;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.IssueNotFoundException;
import org.pro.netandback.core.error.exception.UnauthorizedException;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issue.repository.IssueRepository;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IssueValidate {
    private final IssueRepository issueRepository;

    public Issue getIssueByIdOrThrow(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(IssueNotFoundException::new);
    }

    public void validateIssueOwner(User currentUser, Issue issue) {
        if (!issue.getReporter().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException(ErrorCode.ISSUE_ACCESS_DENIED);
        }
    }
}
