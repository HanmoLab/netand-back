package org.pro.netandback.domain.issue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pro.netandback.domain.issue.model.type.IssueStatus;
import org.pro.netandback.domain.issue.model.type.IssueType;
import org.pro.netandback.domain.issue.model.type.Priority;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueCreateRequest {
    private IssueType issueType;
    private Priority priority;
    private IssueStatus status;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Long companyId;
    private Long reporterId;
    private Long assigneeId;
    private Long productId;
}
