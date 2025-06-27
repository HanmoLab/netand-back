package org.pro.netandback.domain.issue.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.dto.request.IssueUpdateRequest;
import org.pro.netandback.domain.issue.dto.response.IssueCreateResponse;
import org.pro.netandback.domain.issue.dto.response.IssueDetailResponse;
import org.pro.netandback.domain.issue.dto.response.IssueListResponse;
import org.pro.netandback.domain.issue.service.IssueService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@ApiController("/api/v1/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @Operation(summary = "이슈 생성", tags = {"이슈"})
    @PostMapping
    public ResponseEntity<ResponseDto<IssueCreateResponse>> createIssue(@CurrentUser User currentUser, @RequestBody IssueCreateRequest request) {
        IssueCreateResponse response = issueService.createIssue(currentUser, request);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, "이슈 생성 성공", response));
    }

    @Operation(summary = "이슈 목록 조회", tags = {"이슈"})
    @GetMapping
    public ResponseEntity<ResponseDto<Page<IssueListResponse>>> getIssueList(Pageable pageable) {
        Page<IssueListResponse> response = issueService.getIssueList(pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "이슈 목록 조회 성공", response));
    }

    @Operation(summary = "이슈 상세 조회", tags = {"이슈"})
    @GetMapping("/{issueId}")
    public ResponseEntity<ResponseDto<IssueDetailResponse>> getIssueDetail(@CurrentUser User currentUser, @PathVariable Long issueId) {
        IssueDetailResponse response = issueService.getIssueDetail(currentUser, issueId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "이슈 상세 조회 성공", response));
    }

    @Operation(summary = "이슈 수정", tags = {"이슈"})
    @PutMapping("/{issueId}")
    public ResponseEntity<ResponseDto<IssueDetailResponse>> updateIssue(@CurrentUser User currentUser, @PathVariable Long issueId, @RequestBody IssueUpdateRequest request) {
        IssueDetailResponse response = issueService.updateIssue(currentUser, issueId, request);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "이슈 수정 성공", response));
    }

    @Operation(summary = "이슈 삭제", tags = {"이슈"})
    @DeleteMapping("/{issueId}")
    public ResponseEntity<ResponseDto<Void>> deleteIssue(@CurrentUser User currentUser, @PathVariable Long issueId) {
        issueService.deleteIssue(currentUser, issueId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.NO_CONTENT, "이슈 삭제 성공", null));
    }
}
