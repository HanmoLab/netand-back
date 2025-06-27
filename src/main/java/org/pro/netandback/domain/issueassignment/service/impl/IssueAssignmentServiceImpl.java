package org.pro.netandback.domain.issueassignment.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issue.repository.IssueRepository;
import org.pro.netandback.domain.issue.validate.IssueValidate;
import org.pro.netandback.domain.issueassignment.dto.response.IssueAssignmentResponse;
import org.pro.netandback.domain.issueassignment.mapper.IssueAssignmentMapper;
import org.pro.netandback.domain.issueassignment.model.entity.IssueAssignment;
import org.pro.netandback.domain.issueassignment.repository.IssueAssignmentRepository;
import org.pro.netandback.domain.issueassignment.service.IssueAssignmentService;
import org.pro.netandback.domain.issueassignment.validate.IssueAssignmentValidate;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.user.validate.UserValidate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueAssignmentServiceImpl implements IssueAssignmentService {
    private final IssueAssignmentRepository issueAssignmentRepository;
    private final UserValidate userValidate;
    private final IssueValidate issueValidate;
    private final IssueAssignmentValidate issueAssignmentValidate;
    private final IssueAssignmentMapper issueAssignmentMapper;

    @Transactional
    public IssueAssignmentResponse assignAssignee(Long issueId, Long assigneeId, User currentUser) {
        Issue issue = issueValidate.getIssueByIdOrThrow(issueId);
        issueValidate.validateIssueOwner(currentUser, issue);
        User assignee = userValidate.getUserByIdOrThrow(assigneeId);
        IssueAssignment assignment = issueAssignmentMapper.toIssueAssignment(issue, assignee);
        issueAssignmentRepository.save(assignment);
        issue.assignTo(assignee);
        return issueAssignmentMapper.toIssueAssignmentResponse(assignment);
    }

    @Transactional
    public IssueAssignmentResponse updateAssignee(Long issueId, Long newAssigneeId, User currentUser) {
        Issue issue = issueValidate.getIssueByIdOrThrow(issueId);
        issueValidate.validateIssueOwner(currentUser, issue);
        User newAssignee = userValidate.getUserByIdOrThrow(newAssigneeId);
        IssueAssignment assignment = issueAssignmentValidate.getAssignmentByIssueOrThrow(issue);
        assignment.updateAssignee(newAssignee);
        issue.assignTo(newAssignee);
        return new IssueAssignmentResponse(assignment.getId());
    }

    @Transactional
    public void deleteAssignee(Long issueId, User currentUser) {
        Issue issue = issueValidate.getIssueByIdOrThrow(issueId);
        issueValidate.validateIssueOwner(currentUser, issue);
        IssueAssignment assignment = issueAssignmentValidate.getAssignmentByIssueOrThrow(issue);
        issueAssignmentRepository.delete(assignment);
        issue.assignTo(null);
    }
}
