package org.pro.netandback.domain.issue.validate;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.error.exception.IssueNotFoundException;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issue.repository.IssueRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IssueValidate {
    private final IssueRepository issueRepository;

    public Issue validateIssueExists(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(IssueNotFoundException::new);
    }
}
