package org.pro.netandback.domain.issueassignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.domain.issueassignment.dto.request.IssueAssignmentRequest;
import org.pro.netandback.domain.issueassignment.dto.response.IssueAssignmentResponse;
import org.pro.netandback.domain.issueassignment.service.IssueAssignmentService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@ApiController("/api/v1/issues/{issueId}/assignee")
@RequiredArgsConstructor
public class IssueAssignmentController {
    private final IssueAssignmentService issueAssignmentService;

    @Operation(summary = "이슈 담당자 배정", tags = {"이슈 담당자"})
    @PostMapping
    public ResponseEntity<ResponseDto<IssueAssignmentResponse>> assignAssignee(@CurrentUser User currentUser, @PathVariable Long issueId, @RequestBody IssueAssignmentRequest request){
        IssueAssignmentResponse response = issueAssignmentService.assignAssignee(issueId, request.getAssigneeId(), currentUser);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, "담당자 배정 성공", response));
    }

    @Operation(summary = "이슈 담당자 수정", tags = {"이슈 담당자"})
    @PutMapping
    public ResponseEntity<ResponseDto<IssueAssignmentResponse>> updateAssignee(@CurrentUser User currentUser, @PathVariable Long issueId, @RequestBody IssueAssignmentRequest request) {
        IssueAssignmentResponse response = issueAssignmentService.updateAssignee(issueId, request.getAssigneeId(), currentUser);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "담당자 수정 성공", response));
    }

    @Operation(summary = "이슈 담당자 삭제", tags = {"이슈 담당자"})
    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteAssignee(@CurrentUser User currentUser, @PathVariable Long issueId) {
        issueAssignmentService.deleteAssignee(issueId, currentUser);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.NO_CONTENT, "담당자 삭제 성공", null));
    }
}
