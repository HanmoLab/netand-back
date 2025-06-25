package org.pro.netandback.domain.issue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IssueListResponse {
    private Long id;
    private LocalDateTime createdAt;
    private String companyName;
    private String productName;
    private String priority;
    private String title;
    private String status;
    private String assigneeName;
    private String issueType;
}
