package org.pro.netandback.domain.issuecomment.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issue.validate.IssueValidate;
import org.pro.netandback.domain.issuecomment.dto.request.IssueCommentRequest;
import org.pro.netandback.domain.issuecomment.dto.response.IssueCommentResponse;
import org.pro.netandback.domain.issuecomment.mapper.IssueCommentMapper;
import org.pro.netandback.domain.issuecomment.model.entity.IssueComment;
import org.pro.netandback.domain.issuecomment.repository.IssueCommentRepository;
import org.pro.netandback.domain.issuecomment.service.IssueCommentService;
import org.pro.netandback.domain.issuecomment.validate.IssueCommentValidate;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueCommentServiceImpl implements IssueCommentService {
    private final IssueCommentRepository issueCommentRepository;
    private final IssueValidate issueValidate;
    private final IssueCommentValidate issueCommentValidate;
    private final IssueCommentMapper issueCommentMapper;

    @Transactional
    public IssueCommentResponse createComment(User writer, Long issueId, IssueCommentRequest request) {
        Issue issue = issueValidate.getIssueByIdOrThrow(issueId);
        IssueComment comment = issueCommentMapper.toIssueComment(issue, writer, request);
        return issueCommentMapper.toIssueCommentResponse(issueCommentRepository.save(comment));
    }

    public List<IssueCommentResponse> getCommentsByIssue(Long issueId) {
        List<IssueComment> comments = issueCommentRepository.findByIssueIdOrderByCreatedAtAsc(issueId);
        return issueCommentMapper.toIssueCommentResponseList(comments);
    }

    @Transactional
    public IssueCommentResponse updateComment(User writer, Long issueId, Long commentId, IssueCommentRequest request) {
        issueValidate.getIssueByIdOrThrow(issueId);
        IssueComment comment = issueCommentValidate.getCommentByIdOrThrow(commentId);
        issueCommentValidate.validateCommentInIssue(comment, issueId);
        issueCommentValidate.validateCommentOwner(comment, writer.getId());
        comment.updateContent(request.getContent());
        return issueCommentMapper.toIssueCommentResponse(comment);
    }

    @Transactional
    public void deleteComment(User writer, Long issueId, Long commentId) {
        issueValidate.getIssueByIdOrThrow(issueId);
        IssueComment comment = issueCommentValidate.getCommentByIdOrThrow(commentId);
        issueCommentValidate.validateCommentInIssue(comment, issueId);
        issueCommentValidate.validateCommentOwner(comment, writer.getId());
        issueCommentRepository.delete(comment);
    }

}
