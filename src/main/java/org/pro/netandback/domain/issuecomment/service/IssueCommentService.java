package org.pro.netandback.domain.issuecomment.service;

import org.pro.netandback.domain.issuecomment.dto.request.IssueCommentRequest;
import org.pro.netandback.domain.issuecomment.dto.response.IssueCommentResponse;
import org.pro.netandback.domain.user.model.entity.User;

import java.util.List;

public interface IssueCommentService {
    IssueCommentResponse createComment(User writer, Long issueId, IssueCommentRequest request);
    List<IssueCommentResponse> getCommentsByIssue(Long issueId);
    IssueCommentResponse updateComment(User writer, Long issueId, Long commentId, IssueCommentRequest request);
    void deleteComment(User writer, Long issueId, Long commentId);
}
