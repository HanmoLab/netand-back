package org.pro.netandback.domain.issueassignment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issueassignment.dto.response.IssueAssignmentResponse;
import org.pro.netandback.domain.issueassignment.model.entity.IssueAssignment;
import org.pro.netandback.domain.user.model.entity.User;

@Mapper(componentModel = "spring")
public interface IssueAssignmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "issue", source = "issue")
    @Mapping(target = "assignee", source = "assignee")
    IssueAssignment toIssueAssignment(Issue issue, User assignee);

    @Mapping(target = "id", source = "id")
    IssueAssignmentResponse toIssueAssignmentResponse(IssueAssignment issueAssignment);
}
