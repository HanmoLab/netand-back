package org.pro.netandback.domain.issue.service;

import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.dto.request.IssueUpdateRequest;
import org.pro.netandback.domain.issue.dto.response.IssueCreateResponse;
import org.pro.netandback.domain.issue.dto.response.IssueDetailResponse;
import org.pro.netandback.domain.issue.dto.response.IssueListResponse;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueService {
    IssueCreateResponse createIssue(User currentUser, IssueCreateRequest issueCreateRequest);
    Page<IssueListResponse> getIssueList(Pageable pageable);
    IssueDetailResponse getIssueDetail(User currentUser, Long issueId);
    IssueDetailResponse updateIssue(User currentUser, Long issueId, IssueUpdateRequest request);
    void deleteIssue(User currentUser, Long issueId);
}
