package org.pro.netandback.domain.issueassignment.validate;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.error.exception.IssueAssignmentNotFoundException;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issueassignment.model.entity.IssueAssignment;
import org.pro.netandback.domain.issueassignment.repository.IssueAssignmentRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IssueAssignmentValidate {
    private final IssueAssignmentRepository issueAssignmentRepository;

    public IssueAssignment getAssignmentByIssueOrThrow(Issue issue) {
        return issueAssignmentRepository.findByIssue(issue)
                .orElseThrow(IssueAssignmentNotFoundException::new);
    }
}
