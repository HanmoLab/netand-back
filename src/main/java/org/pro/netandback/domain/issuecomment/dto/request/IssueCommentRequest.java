package org.pro.netandback.domain.issuecomment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueCommentRequest {
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
