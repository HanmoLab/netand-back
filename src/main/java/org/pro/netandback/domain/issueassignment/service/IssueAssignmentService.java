package org.pro.netandback.domain.issueassignment.service;

import org.pro.netandback.domain.issueassignment.dto.response.IssueAssignmentResponse;
import org.pro.netandback.domain.user.model.entity.User;

public interface IssueAssignmentService {
    IssueAssignmentResponse assignAssignee(Long issueId, Long assigneeId, User currentUser);
    IssueAssignmentResponse updateAssignee(Long issueId, Long newAssigneeId, User currentUser);
    void deleteAssignee(Long issueId, User currentUser);
}
