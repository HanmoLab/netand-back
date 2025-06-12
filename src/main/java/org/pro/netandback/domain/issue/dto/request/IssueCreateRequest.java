package org.pro.netandback.domain.issue.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pro.netandback.domain.issue.model.type.IssueStatus;
import org.pro.netandback.domain.issue.model.type.IssueType;
import org.pro.netandback.domain.issue.model.type.Priority;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class IssueCreateRequest {
    private IssueType issueType;
    private Priority priority;
    private IssueStatus status;
    private String title;
    private String description;
    private LocalDate dueDate;
    private int progress;
    private Long companyId;
    private Long reporterId;
    private Long assigneeId;
    private Long productId;
}
