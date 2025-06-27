package org.pro.netandback.domain.issuecomment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IssueCommentResponse {
    private Long id;
    private String writerName;
    private String content;
    private LocalDateTime createdAt;
}
