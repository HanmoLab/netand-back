package org.pro.netandback.domain.issue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class IssueDetailResponse {
    private Long id;
    private String title;
    private String status;
    private String issueType;
    private String companyName;
    private String productName;
    private String assigneeName;
    private String reporterName;
    private String dueDate;
    private int progress;
    private String priority;
    private String description;
}
