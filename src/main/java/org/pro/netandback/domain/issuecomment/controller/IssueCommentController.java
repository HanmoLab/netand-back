package org.pro.netandback.domain.issuecomment.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.domain.issuecomment.dto.request.IssueCommentRequest;
import org.pro.netandback.domain.issuecomment.dto.response.IssueCommentResponse;
import org.pro.netandback.domain.issuecomment.service.IssueCommentService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiController("/api/v1/issues/{issueId}/comments")
@RequiredArgsConstructor
public class IssueCommentController {
    private final IssueCommentService issueCommentService;

    @Operation(summary = "댓글 생성", tags = {"이슈 댓글"})
    @PostMapping
    public ResponseEntity<ResponseDto<IssueCommentResponse>> createComment(@CurrentUser User currentUser, @PathVariable Long issueId, @RequestBody @Valid IssueCommentRequest request) {
        IssueCommentResponse response = issueCommentService.createComment(currentUser, issueId, request);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, "댓글 생성 성공", response));
    }

    @Operation(summary = "댓글 목록 조회", tags = {"이슈 댓글"})
    @GetMapping
    public ResponseEntity<ResponseDto<List<IssueCommentResponse>>> getComments(@PathVariable Long issueId) {
        List<IssueCommentResponse> response = issueCommentService.getCommentsByIssue(issueId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "댓글 목록 조회 성공", response));
    }

    @Operation(summary = "댓글 수정", tags = {"이슈 댓글"})
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<IssueCommentResponse>> updateComment(@CurrentUser User currentUser, @PathVariable Long issueId, @PathVariable Long commentId, @RequestBody IssueCommentRequest request) {
        IssueCommentResponse response = issueCommentService.updateComment(currentUser, issueId, commentId, request);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "댓글 수정 성공", response));
    }

    @Operation(summary = "댓글 삭제", tags = {"이슈 댓글"})
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> deleteComment(@CurrentUser User currentUser, @PathVariable Long issueId, @PathVariable Long commentId) {
        issueCommentService.deleteComment(currentUser, issueId, commentId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.NO_CONTENT, "댓글 삭제 성공", null));
    }
}
