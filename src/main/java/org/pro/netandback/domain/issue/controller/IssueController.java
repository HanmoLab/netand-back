package org.pro.netandback.domain.issue.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.dto.response.IssueCreateResponse;
import org.pro.netandback.domain.issue.service.IssueService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@ApiController("/api/v1/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @Operation(summary = "이슈 생성", tags = {"이슈"})
    @PostMapping("/create")
    public ResponseEntity<ResponseDto<IssueCreateResponse>> createIssue(@CurrentUser User reporter, IssueCreateRequest request) {
        IssueCreateResponse response = issueService.createIssue(reporter, request);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, "이슈 생성 성공", response));
    }
}
